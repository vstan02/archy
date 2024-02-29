(ns archy.script
  (:gen-class)
  (:require [archy.shell :as shell]
            [archy.code :as code]))

(defn ^:private package-list [cfg type]
  (->> (type (:packages cfg))
       (vals)
       (apply concat)))

(defn ^:private custom-package [pkg]
  (shell/custom-install
   (:name pkg) (:src pkg) (apply code/lines (:install pkg))))

(defn ^:private yay-setup [cfg]
  (when (:yay cfg)
    (shell/makepkg-install "yay" "https://aur.archlinux.org/yay.git")))

(defn ^:private simple-packages [cfg type cmd]
  (let [pkgs (->> (package-list cfg type)
                  (map #(if (map? %) [(:name %) (:postinstall %)] [%])))]
    (code/sections
     (->> pkgs
          (map first)
          (map str)
          (apply cmd))
     (->> pkgs
          (map second)
          (filter some?)
          (map #(apply code/lines %))
          (apply code/sections)))))

(defn ^:private pacman-packages [cfg]
  (simple-packages cfg :pacman shell/pacman-install))

(defn ^:private yay-packages [cfg]
  (when (:yay cfg)
    (simple-packages cfg :yay shell/yay-install)))

(defn ^:private custom-packages [cfg]
  (->> (package-list cfg :custom)
       (map custom-package)
       (apply code/sections)))

(defn ^:private packages [cfg]
  (->> [pacman-packages yay-setup yay-packages custom-packages]
       (map #(% cfg))
       (filter some?)
       (apply code/sections)))

(defn ^:private pc-sync [cfg]
  (when (:pacman-sync cfg)
    (shell/pacman-sync)))

(defn ^:private clean [cfg]
  (when (:pacman-clean cfg)
    (shell/pacman-clean)))

(defn ^:private modules [cfg]
  (->> (:modules cfg)
       (map str)
       (shell/add-modules)))

(defn ^:private services [cfg]
  (->> (:services cfg)
       (map str)
       (shell/add-services)))

(defn make [cfg]
  (->> [pc-sync packages clean modules services]
       (map #(% cfg))
       (filter some?)
       (apply code/sections)))

(ns archy.shell
  (:gen-class)
  (:require [archy.code :as code]))

(def ^:private cmds
  {:add-modules
   (code/lines "sudo sed -i \"s/MODULES=(/MODULES=(%1$s /g\" /etc/mkinitcpio.conf"
               "sudo mkinitcpio -P")
   :pacman-sync
   (code/lines "sudo pacman -Sy --noconfirm")
   :pacman-clean
   (code/lines "sudo pacman -Rs $(pacman -Qdtq) --noconfirm"
               "sudo pacman -Sc --noconfirm")
   :makepkg
   (code/lines "makepkg -sri")
   :add-service
   (code/lines "sudo systemctl enable %1$s.service")
   :pkg-custom-install
   (code/lines "cd ~/Downloads"
               "git clone '%2$s' '%1$s'"
               "cd '%1$s'"
               "%3$s"
               "cd .."
               "sudo rm -R '%1$s'"
               "cd ~")
   :pkg-pacman-install
   (code/lines "sudo pacman -S %1$s --noconfirm")
   :pkg-yay-install
   (code/lines "yay -S %1$s --noconfirm")})

(defn pacman-sync []
  (:pacman-sync cmds))

(defn pacman-clean []
  (:pacman-clean cmds))

(defn add-modules [modules]
  (->> modules
       (apply code/words)
       (format (:add-modules cmds))))

(defn add-service [service]
  (format (:add-service cmds) service))

(defn add-services [services]
  (->> services
       (map add-service)
       (apply code/lines)))

(defn pacman-install [& names]
  (->> names
       (apply code/words)
       (format (:pkg-pacman-install cmds))))

(defn yay-install [& names]
  (->> names
       (apply code/words)
       (format (:pkg-yay-install cmds))))

(defn custom-install [name, repo-url, install-cmds]
  (format (:pkg-custom-install cmds)
          name repo-url install-cmds))

(defn makepkg-install [name repo-url]
  (custom-install name repo-url (:makepkg cmds)))

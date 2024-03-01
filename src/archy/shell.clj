(ns archy.shell
  (:gen-class)
  (:require [archy.code :as code]))

(defn pacman-sync []
  (code/lines "sudo pacman -Sy --noconfirm"))

(defn pacman-clean []
  (code/lines "sudo pacman -Rs $(pacman -Qdtq) --noconfirm"
               "sudo pacman -Sc --noconfirm"))

(defn add-modules [modules]
  (->> modules
       (apply code/words)
       (format 
        (code/lines "sudo sed -i \"s/MODULES=(/MODULES=(%1$s /g\" /etc/mkinitcpio.conf"
                    "sudo mkinitcpio -P"))))

(defn add-service [service]
  (format 
   (code/lines "sudo systemctl enable %1$s.service") 
   service))

(defn add-services [services]
  (->> services
       (map add-service)
       (apply code/lines)))

(defn pacman-install [& names]
  (->> names
       (apply code/words)
       (format (code/lines "sudo pacman -S %1$s --noconfirm"))))

(defn yay-install [& names]
  (->> names
       (apply code/words)
       (format (code/lines "yay -S %1$s --noconfirm"))))

(defn custom-install [name, repo-url, install-cmds]
  (format 
   (code/lines "cd ~/Downloads"
               "git clone '%2$s' '%1$s'"
               "cd '%1$s'"
               "%3$s"
               "cd .."
               "sudo rm -R '%1$s'"
               "cd ~")
   name repo-url install-cmds))

(defn makepkg-install [name repo-url]
  (custom-install name repo-url 
   (code/lines "makepkg -sri")))

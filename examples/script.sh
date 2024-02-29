sudo pacman -Sy --noconfirm

sudo pacman -S linux-lts-headers intel-ucode git clang curl wget zsh neovim xorg xorg-server gnome-shell gdm gnome-terminal gnome-control-center gnome-tweaks gnome-shell-extensions gnome-system-monitor eog celluloid gnome-backgrounds power-profiles-daemon gnome-calculator gnome-text-editor nautilus xdg-user-dirs pipewire pipewire-pulse pipewire-audio wireplumber bluez bluez-utils a52dec faac faad2 flac jasper lame libdca libdv libmad libmpeg2 libtheora libvorbis libxv wavpack x264 xvidcore ttf-caladea ttf-carlito ttf-dejavu ttf-liberation noto-fonts ttf-opensans otf-overpass ttf-roboto tex-gyre-fonts ttf-ubuntu-font-family mesa lib32-mesa mesa-utils xf86-video-nouveau xf86-video-intel vulkan-intel lib32-vulkan-intel switcheroo-control breeze papirus-icon-theme vivaldi vivaldi-ffmpeg-codecs libnotify --noconfirm

sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh) --unattended --keep-zshrc"
cp ./assets/zshrc ~/.zshrc
chsh -s $(which zsh)

mkdir -p ~/.config/nvim
cp ./assets/vimrc ~/.config/nvim/init.vim

xdg-user-dirs-update

cd ~/Downloads
git clone 'https://aur.archlinux.org/yay.git' 'yay'
cd 'yay'
makepkg -sri
cd ..
sudo rm -R 'yay'
cd ~

yay -S gdm-settings gnome-browser-connector --noconfirm



cd ~/Downloads
git clone 'https://github.com/vinceliuice/Graphite-gtk-theme.git' 'Graphite-gtk-theme'
cd 'Graphite-gtk-theme'
./install.sh -t blue -c dark --tweaks nord darker rimless normal
./install.sh -l -t blue -c dark --tweaks nord darker rimless normal
sudo ./install.sh -t blue -c dark --tweaks nord darker rimless normal
sudo ./install.sh -l -t blue -c dark --tweaks nord darker rimless normal
sudo ./install.sh -g -t blue -c dark --tweaks nord darker rimless normal
cd ..
sudo rm -R 'Graphite-gtk-theme'
cd ~

sudo pacman -Rs $(pacman -Qdtq) --noconfirm
sudo pacman -Sc --noconfirm

sudo sed -i "s/MODULES=(/MODULES=(i915 nouveau /g" /etc/mkinitcpio.conf
sudo mkinitcpio -P

sudo systemctl enable gdm.service
sudo systemctl enable bluetooth.service
sudo systemctl enable switcheroo-control.service
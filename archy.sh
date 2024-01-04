sudo pacman -Sy linux-lts-headers intel-ucode git clang curl wget zsh neovim xorg xorg-server xdg-user-dirs gnome-shell gdm gnome-terminal gnome-control-center gnome-tweaks gnome-shell-extensions gnome-system-monitor eog celluloid gnome-text-editor nautilus vivaldi vivaldi-ffmpeg-codecs libnotify breeze papirus-icon-theme gnome-backgrounds power-profiles-daemon gnome-calculator ttf-caladea ttf-carlito ttf-dejavu ttf-liberation noto-fonts ttf-opensans otf-overpass ttf-roboto tex-gyre-fonts ttf-ubuntu-font-family a52dec faac faad2 flac jasper lame libdca libdv libmad libmpeg2 libtheora libvorbis libxv wavpack x264 xvidcore pipewire pipewire-pulse pipewire-audio wireplumber bluez bluez-utils --noconfirm

xdg-user-dirs-update

cd ~/Downloads
git clone https://aur.archlinux.org/yay.git
cd yay
makepkg -sri
cd ..
sudo rm -R yay
cd ~
yay -S gdm-settings

cd ~/Downloads
git clone https://github.com/vinceliuice/Graphite-gtk-theme.git
cd Graphite-gtk-theme
./install.sh -t blue -c dark --tweaks nord darker rimless normal
./install.sh -l -t blue -c dark --tweaks nord darker rimless normal
sudo ./install.sh -t blue -c dark --tweaks nord darker rimless normal
sudo ./install.sh -l -t blue -c dark --tweaks nord darker rimless normal
sudo ./install.sh -g -t blue -c dark --tweaks nord darker rimless normal
cd ..
sudo rm -R Graphite-gtk-theme
cd ~

sudo systemctl enable gdm.service
sudo systemctl enable bluetooth.service

mkdir ~/.config/nvim
cp ./assets/vimrc ~/.config/nvim/init.vim
sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
cp ./assets/zshrc ~/.zshrc

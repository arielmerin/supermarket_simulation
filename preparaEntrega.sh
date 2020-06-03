#!/bin/bash
read -p "Nombre del comprimido: " title
rsync -Rr --exclude='*.sh' . proyecto/

rm -rf proyecto/.git/
rm -rf proyecto/.idea/
rm -rf proyecto/out/
rm -rf proyecto/*.md
rm -rf proyecto/.gitignore
rm -rf proyecto/*.iml
rm -rf proyecto/LICENSE

find proyecto/src/ -name '*.class' -exec shred -n 10 -uz --remove {} \;
find proyecto/ -name '*.aux' -exec shred -n 10 -uz --remove {} \;
find proyecto/ -name '*.log' -exec shred -n 10 -uz --remove {} \;
find proyecto/ -name '*.synctex.gz' -exec shred -n 10 -uz --remove {} \;

tar -czf "$title".tar.gz --exclude='*.tar.gz'  proyecto/

rm -fr proyecto/
echo "$title.tar.gz" >> .gitignore
echo -e "\nACABASTE ZORRITA ;) \nSe creó el archivo $title.tar.gz\n"


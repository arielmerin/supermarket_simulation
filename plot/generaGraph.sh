#!/usr/bin/gnuplot -persist
set terminal png size 500,500
set output 'prinip.png'
set title "Eficiencia por número de cajas rápidas" font "14" textcolor rgbcolor "royalblue"
set xlabel "Número de cajas rápidas"
set xlabel "Tiempo promedio de espera"
set pointsize 1
plot  "datos.p" using 2:1 with linespoints

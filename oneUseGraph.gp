#!/usr/local/bin/gnuplot --persist
set terminal png size 1600,1200
set output ARG1
set title "Eficiencia por número de cajas rápidas" font "Times, 14" textcolor rgbcolor "royalblue"
set xlabel "Número de cajas rápidas"
set ylabel "Tiempo promedio de espera"
#set xrange [0:15]
#set xtic (1,2,3,4,5,6,7,8,9,10,11,12,13,14)
set pointsize 1
plot  "plot/datosJuntos.dat" using 2:1 with circles lc rgb "red" fs transparent solid 0.5 noborder ti "Tiempo de espera ",
#!/usr/bin/gnuplot -persist
set terminal png size 1600,1200
set output 'plot/histGraph.png'
set style data histograms
set style fill solid
set style histogram cluster
plot 'plot/datos.dat' using 3:xtic(2) ti "Clientes rapidos" linecolor rgb "blue", '' u 4:xtic(2) ti "Clientes normales" linecolor rgb "orange"

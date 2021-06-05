#!/usr/bin/gnuplot 

# don't print legend
set nokey

set title "METHOD on FUNCTION finished in ITERATIONS iterations"

FUNCTION

set isosamples 228
set xrange [MIN_X:MAX_X]
set yrange [MIN_Y:MAX_Y]
set zrange [MIN_Z:MAX_Z]

set pm3d map

splot f(x, y), \
    DATA w l linecolor rgb "red" lw 2, \
    DATA with points lc rgb "#20FD6A02" pointtype 7 pointsize 1

pause mouse close

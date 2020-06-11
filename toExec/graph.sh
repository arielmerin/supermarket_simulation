#!/bin/bash

gnuplot -c histGraph.gp $1
gnuplot -c mainGraph.gp $2
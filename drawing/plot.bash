#!/bin/bash

# e - stop execution after the first failure
# x - print the executed commands

if [[ $# -le 2 ]]
then
    echo "usage: DATAFILE FUNCTION METHOD [MIN_X MAX_X MIN_Y MAX_Y MIN_Z MAX_Z]"
    exit 1
fi

set -x

# create separate file for plotting
gp_num=0
while test -e $gp_num.gp; do
  ((gp_num++))
done

fname=$gp_num.gp

cp plot-template.gp $fname

# $1 - name of the data file (where points are)
sed -i "s/DATA/'$1'/g" $fname

# $2 - the function definition
# should look like this: "f(x,y)=..."
sed -i "s/FUNCTION/$2/g" $fname

# $3 - the method name (russian is OK)
sed -i "s/METHOD/$3/g" $fname

# $4 - MIN_X
sed -i "s/MIN_X/${4:--250}/g" $fname

# $5 - MAX_X
sed -i "s/MAX_X/${5:-250}/g" $fname

# $6 - MIN_Y
sed -i "s/MIN_Y/${6:--250}/g" $fname

# $7 - MAX_Y
sed -i "s/MAX_Y/${7:-250}/g" $fname

# $8 - MIN_Z
sed -i "s/MIN_Z/${8:--100}/g" $fname

# $9 - MAX_Z
sed -i "s/MAX_Z/${9:-1000000}/g" $fname 

# ITERATIONS - the number of iterations
iters=$(wc -l $1 | awk '{ print $1 }')
((iters-=2))
sed -i "s/ITERATIONS/$iters/g" $fname

# OUTPUT - the name of the output image file
sed -i "s/OUTPUT/${1%.*}.png/g" $fname

cat -n $fname

./$fname

#!/usr/bin/env bash

echo "Creating PDF"

unameOut="$(uname -s)"
case "${unameOut}" in
    Linux*)     machine=Linux;;
    Darwin*)    machine=Mac;;
    CYGWIN*)    machine=Cygwin;;
    MINGW*)     machine=MinGw;;
    *)          machine="UNKNOWN:${unameOut}"
esac

##echo ${machine}

##if [ "${machine}" == "Mac" ]; then
    echo "creating PDF..."
    alias chrome="/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome"
    chrome --headless --print-to-pdf="RESUME.pdf" Resumes.html
##elif [ "${machine}" == "Linux" ]; then
# Do something under GNU/Linux platform
##elif [ "${machine}" == "Cygwin" ]; then
##    echo "creating PDF..."
##    wkhtmltopdf C:\TollProject\Resumes.html C:\TollProject\Resumes.pdf
##elif [ "${machine}" == "MinGw" ]; then
##    echo "creating PDF..."
##    wkhtmltopdf C:\TollProject\Resumes.html C:\TollProject\Resumes.pdf
##fi

echo "Creating PDF complete"
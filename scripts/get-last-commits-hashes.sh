#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Usage: $0 <number> <path>" >&2
    exit 1
fi

git log -n "$1" --pretty=format:"%H" > "$2"_tmp
paste -sd, "$2"_tmp > "$2"
rm "$2"_tmp
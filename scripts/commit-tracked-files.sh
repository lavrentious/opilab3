#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <classes_file_path>" >&2
  exit 1
fi

# add newline if no newline
if [ -n "$(tail -c1 "$1")" ]; then
  echo >> "$1"
fi


changed_files=$(git status --porcelain | awk '{print $2}')

if [ -z "$changed_files" ]; then
  echo "working directory clean. exiting"
  exit 0
fi


commit_needed=false
while read class; do
  echo -n "checking class $class... "
  for file in $changed_files; do
    # convert file path to java class name
    java_class=$(echo "$file" | sed 's|^app/src/main/java/||' | sed 's|/|.|g' | sed 's|.java$||')

    if [ "$java_class" = "$class" ]; then
      echo -n "change detected"
      commit_needed=true
      git add $file
    fi
  done
  echo
done < "$1"

echo "========"


if $commit_needed; then
  echo "at least one of the watched classes has changed. committing..."
  # git commit -m "auto-commit: changes in tracked classes"
else
  echo "no changes detected in watched classes"
fi
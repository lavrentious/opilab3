#!/bin/bash

if [ -z "$1" ] || [ -z "$2" ]; then
    echo "Usage: $0 <max_attempts> <diff_file_path>" >&2
    exit 1
fi


max_attempts=$1
attempt=1
prev_commit=$(git rev-parse HEAD)
while [ $attempt -lt $max_attempts ]; do
  commit=$(git rev-parse HEAD)
  echo "attempt $((attempt+1)): trying commit $commit"

  if ant build-war; then
    echo "build success on attempt $attempt (commit=$commit)"
    git diff $prev_commit $commit > $2
    exit 0
  else
    echo "build failed commit $commit. going back to previous commit..."
    git checkout HEAD~1 || {
      echo "no more commits. it's over"
      exit 1
    }
  fi
  attempt=$((attempt+1))
done

echo "max attempts ($max_attempts) reached, build failed"
exit 1
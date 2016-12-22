#!/usr/bin/env bash

set -o errexit
set -o pipefail
set -o nounset
#set -x

# Set magic variables for current file & dir
__dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
__file="${__dir}/$(basename "${BASH_SOURCE[0]}")"
__base="$(basename ${__file} .sh)"
__root="$(cd "$(dirname "${__dir}")" && pwd)"

echo $__dir

HR="--------------------------------------------------------------------------------\n"

# Use colors, but only if connected to a terminal, and that terminal
# supports them.
if which tput >/dev/null 2>&1; then
  ncolors=$(tput colors)
fi
if [ -t 1 ] && [ -n "$ncolors" ] && [ "$ncolors" -ge 8 ]; then
  R="$(tput setaf 1)"
  G="$(tput setaf 2)"
  B="$(tput setaf 4)"
  Y="$(tput setaf 3)"
  BD="$(tput bold)"
  N="$(tput sgr0)"
else
  R=""
  G=""
  B=""
  Y=""
  BD=""
  N=""
fi

# Ask a Question, https://gist.github.com/davejamesmiller/1965569
function ask() {
  while true; do
    if [ "${2:-}" = "Y" ]; then
      prompt="Y/n"
      default=Y
    elif [ "${2:-}" = "N" ]; then
      prompt="y/N"
      default=N
    else
      prompt="y/n"
      default=
    fi

    # Ask the question (not using "read -p" as it uses stderr not stdout)
    echo "$1 [$prompt] "

    # Read the answer (use /dev/tty in case stdin is redirected from somewhere else)
    read REPLY </dev/tty

    # Default?
    if [ -z "$REPLY" ]; then
      REPLY=$default
    fi

    # Check if the reply is valid
    case "$REPLY" in
      Y*|y*) return 0 ;;
      N*|n*) return 1 ;;
    esac
  done
}

function replace_version() {
  sed -i '' -e "s|$2|$3|g" "$__dir/$1"
  env git status --porcelain 2>/dev/null| grep "$(basename "$1")" || {
    echo "${R}Error patching version info in file: $__dir/$1${N}" && exit 1
  }
  echo "Updated versions in file: $1"
}

function main() {
  read -p "${B}Enter the new version number: ${N}" VERSION_NEW
  replace_version "README.md" "{{LP_SEG_VERSION}}" $VERSION_NEW
  replace_version "gradle.properties" "{{LP_SEG_VERSION}}" $VERSION_NEW
}

main

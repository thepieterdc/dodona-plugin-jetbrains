#!/usr/bin/env python3

import glob
import sys

entrypoint = sys.argv[1]

missing = set()
for filename in glob.iglob("{}/**/*.java".format(entrypoint), recursive=True):
    with open(filename, 'r') as file:
        contents = file.read()
        if "Pieter De Clercq" not in contents or "Tobiah Lissens" not in contents:
            missing.add(filename)

if missing:
    for file in missing:
        print(file)
    exit(len(missing))

#!/usr/bin/env python3

import json
import sys

name = sys.argv[1]
infile = sys.argv[2]
outfile = sys.argv[3]

with open(infile) as log:
	copyrightlog = log.readlines()

files = ''
for file in copyrightlog:
    files += "- {}\n".format(file)

out = {"body": "{} has forgotten to include copyright headers in the following files:```{}```".format(name, files)}

with open(outfile, 'w') as outh:
	json.dump(out, outh)

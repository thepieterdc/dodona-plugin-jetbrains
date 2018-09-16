#!/usr/bin/env python3

import json
import sys

name = sys.argv[1]
infile = sys.argv[2]
outfile = sys.argv[3]

with open(infile) as log:
	buildlog = log.read()

out = {"body": "{} screwed things up once more.\n**Build log:**\n`{}`".format(name, buildlog)}

with open(outfile, 'w') as outh:
	json.dump(out, outh)

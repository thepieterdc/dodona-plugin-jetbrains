#!/bin/sh

sed -i "s/SNAPSHOT'$/SNAPSHOT-$(date +%s)'/g" build.gradle
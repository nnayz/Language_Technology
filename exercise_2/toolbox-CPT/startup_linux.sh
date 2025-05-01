#!/bin/bash
# java -Xmx2000M -Djava.ext.dirs=.:./lib de.uni_leipzig.asv.toolbox.toolboxGui.Toolbox unset G_DEBUG

java -Xmx2000M -classpath "bin:./lib/*" de.uni_leipzig.asv.toolbox.toolboxGui.Toolbox unset G_DEBUG



#!/bin/bash

cd ImperatorToCk2
mvn package
cp target/irtockii.jar ../Release/ImperatorToCK2
cd ..
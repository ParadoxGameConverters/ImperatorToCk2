#!/bin/bash

cd ImperatorToCk2
mvn package
cp target/ImperatorToCK2.jar ../Release/ImperatorToCK2
cd ..
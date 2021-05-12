#!/bin/bash
while getopts t:d: flag;
do
    case "${flag}" in
        t) DATE="${OPTARG}";;
        d) DRIVER="${OPTARG}";;
        *) echo "Invalid option";;
    esac
done

echo "Testing latest OpenLiberty Docker image"

sed -i "\#<artifactId>liberty-maven-plugin</artifactId>#a<configuration><install><runtimeUrl>https://public.dhe.ibm.com/ibmdl/export/pub/software/openliberty/runtime/nightly/"$DATE"/"$DRIVER"</runtimeUrl></install></configuration>" system/pom.xml query/pom.xml inventory/pom.xml
cat system/pom.xml query/pom.xml inventory/pom.xml

sed -i "s;FROM openliberty/open-liberty:full-java11-openj9-ubi;FROM openliberty/daily:latest;g" system/Dockerfile query/Dockerfile inventory/Dockerfile
cat system/Dockerfile query/Dockerfile inventory/Dockerfile

docker pull "openliberty/daily:latest"

sudo ../scripts/testApp.sh

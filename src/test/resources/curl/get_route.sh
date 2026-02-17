#!/bin/bash

curl -X 'GET' \
          'http://localhost:8080/pathfinder/routing/AUT/GEO' \
          -H 'accept: */*' -w  " ResponseTime=%{time_starttransfer} statusCode=%{http_code}\n"

curl -X 'GET' \
          'http://localhost:8080/pathfinder/routing/AUT/USA' \
          -H 'accept: */*' -w  " ResponseTime=%{time_starttransfer} statusCode=%{http_code}\n"

curl -X 'GET' \
          'http://localhost:8080/pathfinder/routing/AUT/TEST' \
          -H 'accept: */*' -w  " ResponseTime=%{time_starttransfer} statusCode=%{http_code}\n"

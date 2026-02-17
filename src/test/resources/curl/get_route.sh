#!/bin/bash

curl -X 'GET' \
          'http://localhost:8181/pathfinder/routing/RUS/GEO' \
          -H 'accept: */*' -w  " ResponseTime=%{time_starttransfer} statusCode=%{http_code}\n"

curl -X 'GET' \
          'http://localhost:8181/pathfinder/routing/RUS/USA' \
          -H 'accept: */*' -w  " ResponseTime=%{time_starttransfer} statusCode=%{http_code}\n"

curl -X 'GET' \
          'http://localhost:8181/pathfinder/routing/RUS/TEST' \
          -H 'accept: */*' -w  " ResponseTime=%{time_starttransfer} statusCode=%{http_code}\n"

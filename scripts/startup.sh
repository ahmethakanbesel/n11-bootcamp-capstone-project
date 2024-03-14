#!/bin/bash

echo "1111"

# wait for Solr to start
until $(curl --output /dev/null --silent --head --fail http://solr:8983/solr/restaurants/admin/ping); do
    printf '.'
    sleep 5
done

# add field to 'restaurants' schema
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":{
     "name":"location",
     "type":"location",
     "stored":true
   }
}' http://solr:8983/solr/restaurants/schema

echo "2222"
#!/bin/bash

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

echo "Set up Solr schema"

until $(curl --output /dev/null --silent --head --fail http://host.docker.internal:8082/actuator/health); do
    printf '.'
    sleep 5
done

# add example restaurant data
curl -X 'POST' \
  'http://host.docker.internal:8082/api/v1/restaurants' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Ocakbaşı Restaurant",
  "type": "TURKISH",
  "latitude": 39.925533,
  "longitude": 32.866287
}'

curl -X 'POST' \
  'http://host.docker.internal:8082/api/v1/restaurants' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Köşebaşı Kebap",
  "type": "TURKISH",
  "latitude": 39.925544,
  "longitude": 32.866278
}'

curl -X 'POST' \
  'http://host.docker.internal:8082/api/v1/restaurants' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "Hamdi Burger",
  "type": "FAST_FOOD",
  "latitude": 39.925500,
  "longitude": 32.866315
}'

until $(curl --output /dev/null --silent --head --fail http://host.docker.internal:8081/actuator/health); do
    printf '.'
    sleep 5
done

echo "Added example restaurant data"

curl -X 'POST' \
  'http://host.docker.internal:8081/api/v1/users' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "name": "John",
  "surname": "Doe",
  "username": "john_doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "birthDate": "1990-01-01",
  "latitude": 39.925593,
  "longitude": 32.866237
}'

echo "Added example user data"

curl -X 'POST' \
  'http://host.docker.internal:8081/api/v1/reviews' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": 100,
  "restaurantId": "restaurant-1",
  "score": "FOUR",
  "comment": "Great restaurant!"
}'

echo "Added example review data"
#!/usr/bin/env bash

BASE_URL="http://localhost:8080/users"
EMAIL="user@github.com"
USER_ID=1

echo "Create user $EMAIL:"
curl -X POST -H 'Content-Type: application/json' -d "{\"email\": \"$EMAIL\", \"user_id\": $USER_ID}" $BASE_URL
echo
echo "Create user $EMAIL again and get duplication error:"
curl -X POST -H 'Content-Type: application/json' -d "{\"email\": \"$EMAIL\", \"user_id\": $USER_ID}" $BASE_URL
echo

echo "Get user $EMAIL:"
curl -X GET -H 'Content-Type: application/json' $BASE_URL/$EMAIL
echo
echo "Get non-existent user:"
curl -X GET -H 'Content-Type: application/json' $BASE_URL/no-such-user@email.local
echo

echo "Delete user $EMAIL:"
curl -X DELETE -H 'Content-Type: application/json' $BASE_URL/$EMAIL
echo
echo "Delete non-existent user:"
curl -X DELETE -H 'Content-Type: application/json' $BASE_URL/$EMAIL
echo

#!/bin/bash

# Make a GET request to http://localhost:8080 using curl
echo "Gaming platform fetches token from GameProvider AuthServer"
tokenForGamePlatform=$(curl -s http://localhost:8080/tokens?scope=supergame1)

# Check if curl command was successful
if [ $? -eq 0 ]; then
    # Display the tokenForGamePlatform
    echo "Response from http://localhost:8080?scope=supergame1:"
    echo "$tokenForGamePlatform"
else
    echo "Error: Failed to retrieve tokenForGamePlatform from http://localhost:8080"
    exit 1
fi

echo ""
echo "Redirected user using this token to exchange it session token from GameProvider"

# Make a POST request to http://localhost:9001/validate-platform-tokens with the token as Bearer token
playerSessionTokenRaw=$(curl -sS --fail -X POST -d '{"userId":"Alex"}' -H "Content-Type: application/json" -H "Authorization: Bearer $tokenForGamePlatform" http://localhost:9001/validate-platform-tokens)

# Check if the POST request was successful
if [ $? -eq 0 ]; then
    # Display the response
    echo "Response from http://localhost:9001/validate-platform-tokens -d '{"userId":"Alex"} :"
    echo "$playerSessionTokenRaw"
    # Extract token from JSON
    playerSessionToken=$(echo "$playerSessionTokenRaw" | grep -o '"token":"[^"]*' | cut -d '"' -f 4)
#    echo "$playerSessionToken"
else
    echo "Error: Failed to make POST request to http://localhost:9001/validate-platform-tokens"
fi
echo "User supplies its session token to actual game endpoint"
# Make a POST request to http://localhost:9001/validate-players with the playerSessionToken as Bearer token
response=$(curl -sS --fail -X POST -H "Authorization: Bearer $playerSessionToken" http://localhost:9001/validate-players)

# Check if the POST request to validate-players was successful
if [ $? -ne 0 ]; then
    echo "Error: Failed to make POST request to http://localhost:9001/validate-players"
    exit 1
fi

echo "Response from http://localhost:9001/validate-players:"
echo "$response"
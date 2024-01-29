This is a GameProvider part.
Endpoint that validates token for GamePlatform
/validate-platofrm-tokens is a bearer token auth protected resource.
token is the one received from GamePlatform (it must have authorized before)

This service lack functionality to obtain session tokens
for internal game-progress user tracking, but it is pretty much the same as depicted for GamePlatform

NOTE: for the sake of brevity logging/exception handling/proper configuration was omitted.
For such kind of POC is a good practice to build docker-compose image with the Postman-collection request examples.
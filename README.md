# JWT Auth service.

Simple configuration for an authentication service. Endpoints setup to allow registration, login and for other services to validate user JWTs.

## Endpoints

### /api/v1/auth/register
example HTTP request to register a new user
```
curl -H "Content-Type: application/json" -X POST localhost:8081/api/v1/auth/register -d '{"fistName":"steve","lastName":"j", "email":"steve@example.com", "password":"MyStrongPassword123"}'
```


### /api/v1/auth/authenticate
example HTTP request to authenticate an existing user
```
curl -H "Content-Type: application/json" -X POST localhost:8081/api/v1/auth/authenticate -d '{"email":"steve@example.com", "password":"MyStrongPassword123"}'
```



`/api/v1/auth/validate`

example HTTP request to validate a request that requires authentication from another service
```
curl -H "Content-Type: application/json" -X POST localhost:8081/api/v1/auth/validate -d '{"accessKey":"<access_key>"}'
```

## To-dos

- reasonable password length/strength rules
- add password change functionality
- add user details modification options
- add further optional user details - DOB/phone number etc
- profile picture url or a simple avatar text-encoded profile image?
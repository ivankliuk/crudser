CRUDser
=======

A set of REST endpoints for integration with ReqRes.in using
[http4s](https://http4s.org/), [doobie](https://tpolecat.github.io/doobie/) and
[PostgreSQL](https://www.postgresql.org/).

Endpoint list
-------------

1. Endpoint that allows to get a user data from ReqRes.in
(example: https://reqres.in/api/users/12) and persist user's `id`, `first_name`,
 `last_name` to the local database. An extra parameter `email` is used as a
 unique key for the `user` table.

**POST /users**

```json
{
  "email": <string>,
  "user_id": <int>
}

```
where
* `email` - unique key for user identification in the local database
* `user_id` - user identifier in ReqRes.in

2. Endpoint that allows to get a user data from the database.

**GET /users/{email}**
```json
{
  "email": <string>,
  "id": <int>,
  "first_name": <string>,
  "last_name": <string>
}
```

3. Endpoint that allows to delete a user data from the local database.

**DELETE /users/{email}**

4. All errors should have following format:

```json
{ "message": <string> }
```

Running
-------
To run the application:
```bash
sbt run
```
It will be listening on port 8080 by default. All settings are located in
`src/main/resources/application.conf`. Don't forget to configure access to a
PostgreSQL instance.

E2E testing
-----------
To run end-to-end tests execute `e2e-tests.sh` script.
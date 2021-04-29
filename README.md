# rocket-science

## Item routes

POST: `http://localhost:8080/item/`
```json
{
    "name": "Milk",
    "description": "best milk ever",
    "price": 200
}
```

Create an item

GET`http://localhost:8080/items`

Retrieve all values inside the map

GET `http://localhost:8080/item/:id`

Retrieve a value by key

PUT `http://localhost:8080/item/:id`
```json
{
"name": "Milk",
"description": "best milk ever",
"price": 55
}
```

Update an item

## Purchase routes

POST: `http://localhost:8080/purchase`
```json
{
  "ids": ["839d4c4d-ed63-4dfe-bab7-4b75b2627101","8a921f16-5202-4394-a6fc-6fac5733eeea"]
}
```

Purchase items

## Dockerization

to build an local docker image `sbt clean docker:publishLocal`

### Docker command

to run the docker container
`docker run -p 8080:8080 <docker image id/docker name>`

eg. `docker run -p 8080:8080 24e0cfa1e5f7`
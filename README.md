# ATTORNATUS GESSICA API

## --- UNDER DEVELOPMENT ---

API to access PERSON and ADDRESS data.

Using this API it is possible to create a person with name, date of birth and a list of addresses (optional).
Each address must have a street, post-code, city, number and might contain a flag for whether it is a main address or
not.

Below there's an example on how to create a Person with addresses.
#### Path: /attornatus/v1/person
```json
{
  "name": "Gessica Goulart",
  "dateOfBirth": "01/11/1992",
  "addresses": [
    {
      "street": "Rua Qualquer",
      "postCode": "TW16-6RD",
      "city": "London",
      "number": "101A",
      "isMainAddress": true
    },
    {
      "street": "Rua qualquer 2",
      "postCode": "45860-000",
      "city": "Porto",
      "number": "30",
      "isMainAddress": false
    }
  ]
}
```

It is also possible to create an address to an already created person. See request body example below:
#### Path: /attornatus/v1/address
```json
{
  "personId": "e1cbc004-368e-4d15-8e33-b232fa42b12c",
  "street": "Três ",
  "postCode": "SSS",
  "city": "Brighton",
  "number": "500",
  "isMainAddress": true
}
```

## The main address flag

This is a unique main address flag, which means that the last created/updated address for that person with the flag set
to true, will be the only main address. Every other address for that person will have the main address flag set to
false.
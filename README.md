# MinyUrl

**MinyUrl** is a modern scalable url shortner service written in java,taking advantage of recent edge technologies such as docker and redis caching   

## Key Features

* Open Source
* Powered with Docker Scalability feature
* Redis Powered Caching  
* LoadBalance with nginx
* analytics for the generated short link
* provided Api to use in Microservice architecture


### Stack

* Java8
* Spring Boot
* Spring Data MongoDB
* JUnit
* Mockito
* maven
* Docker
* Redis
* MongoDB

### Setup and Run

You need to have Java 8 jdk installed on you system

1. Clone this repository
2. To package jar file and create the app image execute the following command in **MinyUrlService** directory: 
    ```
    mvn clean package docker:build
    ```
3. To run the system: 
    
    ```
    docker-compose up -d 
    ```
    
4. To shortify a url:

    ```
    curl -v -H "Content-Type: application/json" -X POST -d '{"longUrl":"www.google.com"}' http://localhost:8080/api/v1/shortify
    ``` 
5. To access your shorted url: (***KEY*** is the shorted code generated in the 3rd step)
    ```
    curl -v -X GET http://localhost:8080/api/v1/KEY
    ```
6. To access analytics for a url: (***KEY*** is the shorted code generated in the 3rd step)

    ```
    curl -v  -H "Content-Type: application/json" -X GET http://localhost:8080/api/v1/stat/KEY
    ```

## Running the tests

To run the tests :    `mvn test`

### API

**Get shortened URLs list:**
```
GET /api/v1/KEY
```
where ***KEY*** Is shorted link 


**Shortify a link:**

```
POST /api/v1/shortify
```
**Request Scheme:**
```
{"longUrl":"www.google.com"}
```
**Response Scheme:**

```
{
	"success": true,
	"message": "YQTH",
	"code": 0
}
```

**Get Statistics:**

```
GET /api/v1/stat/KEY
```

where ***KEY*** Is shorted link 

***Response Scheme:***
    
```
{
    "success": true,
    "message": "analytics",
    "code": 0,
    "lastAccessDate": "2018-05-12",
    "dailyAverage": 3.0,
    "max": 3,
    "min": 3,
    "totalPerYear": 3,
    "perMonth": {
        "June": 0,
        "October": 0,
        "December": 0,
        "May": 3,
        "September": 0,
        "March": 0,
        "July": 0,
        "January": 0,
        "February": 0,
        "April": 0,
        "August": 0,
        "November": 0
    },
    "byBrowsers": {
        "ie": 0,
        "fireFox": 0,
        "chrome": 2,
        "opera": 0,
        "safari": 0,
        "others": 1
    },
    "byOs": {
        "windows": 2,
        "macOs": 0,
        "linux": 0,
        "android": 0,
        "ios": 0,
        "others": 1
    }
}
```

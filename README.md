# bartender-system
# Bartender System API
This Spring Boot application simulates a Bartender Service API where customers can place drink orders and view all placed orders. 

## Project Features

- Accepts POST requests with customer number and drink type (BEER|DRINK).
- Responds with a 200 status code when the ordered drink will be served.
- Responds with a 429 status code when the order cannot be accepted at the moment.
- Keeps track of served drinks and customers, and exposes an endpoint which lists them.
- The barman can prepare at once 2 beers (drinks of BEER type) or 1 drink (DRINK type).
- Preparing one drink takes a configurable amount of time regardless of drink type.
- Drink request gets the response as soon as the barman starts to prepare a drink. It doesn't get delayed for the time of the drink preparation.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

### Building the Project

1. Clone the repository:
    ```
    git clone https://github.com/steffanie07/bartender-system.git
    ```
2. Navigate to the project directory:
    ```
    cd bartender-system
    ```
3. Build the project using Gradle:
    ```
   ./gradlew build
    ```

### Running the Application

After building the project, you can run the application using the command:
```
./gradlew bootRun
```

## API Endpoints

### Place an Order

- URL: `/orders`
- Method: `POST`
- Body:
    ```json
    {
        "customerNumber": 1,
        "drinkType": "BEER"
    }
    ```

### Get All Orders

- URL: `api/orders`
- Method: `GET`

## Configuration

The time it takes to prepare a drink can be configured in the `application.properties` file. The default time is 5 seconds. 

```
drink.preparation.time=5
```

Change this property to adjust the preparation time. 

## Logging 

All requests are audited using the application log.

## Limitations

This application runs in-memory and on a single node. There is no persistent storage.



## License

This project is open source and available under the [MIT License](LICENSE).

# Spring Boot RabbitMQ Messaging

This is a Spring Boot RabbitMQ example to demonstrate how we can marshall and convert the RabbitMQ message string to Java domain object based on the binding key using MessageConverter

<img width="800" alt="Screenshot 2020-07-22 at 20 29 41" src="https://user-images.githubusercontent.com/6831336/88214471-8f359d80-cc5a-11ea-84a9-74e69fa7d5c2.png">

## Running the app

### 1- Start the docker compose file
```
docker-compose up
```
### 2- Run the app

```
./gradlew bootRun
```

### 3- Access and Check RabbitMQ

Once the application starts successfully, open browser and execute the below url

http://localhost:15672/

Check if you are able to see the exhange declaraions like as shown below

<img width="600" alt="Screenshot 2020-07-22 at 20 18 40" src="https://user-images.githubusercontent.com/6831336/88215582-2b13d900-cc5c-11ea-9ae4-3797982d6444.png">

Check if you are able to see the queue declaraions like as shown below

<img width="600" alt="Screenshot 2020-07-22 at 20 38 30" src="https://user-images.githubusercontent.com/6831336/88215080-6bbf2280-cc5b-11ea-84f2-4dbed46b857e.png">


### 4- Access Swagger UI

Open browser and execute the below url

http://localhost:8080/swagger-ui.html

You can see the swagger ui like as shown below

<img width="600" alt="Screenshot 2020-07-25 at 15 17 39" src="https://user-images.githubusercontent.com/6831336/88457892-2a608a00-ce8a-11ea-8331-cd22cc3b29da.png">

## License

See [LICENSE](LICENSE) file.


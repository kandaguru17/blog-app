## Blog App Api
This is an API project built using Spring-boot (java 8) which uses the `https://jsonplaceholder.typicode.com/` as its data-source

### Dependencies
This project uses:

- spring-boot starter web
- spring-boot-starter-test

### Available Endpoints
- **GET** http://localhost:8080/api/posts - Retrieves all the posts
- **GET** http://localhost:8080/api/posts?limit=10&offset=0 - Retrieves 10 posts on page 1 
- **GET** http://localhost:8080/api/posts?keyword=est - Retrieves all the posts containing the mentioned keyword in the title and body of the post
- **GET** http://localhost:8080/api/posts?limit=10&offset=0&keyword=est - Retrieves all the posts containing the mentioned keyword in the title and body of the post and considers the paging for the retrieved results
- **GET** http://localhost:8080/api/posts/{postid}/comments - Retrieves all the comments for the mentioned post with id postId
 

### Run
To run the application
1. clone the repository `https://github.com/kandaguru17/blog-app-api.git`
2. move to the directory where pom.xml is located
3. run `./mvnw clean install && ./mvnw spring-boot:run` on the terminal/cmd, the command will run the tests, creates a build and starts the application.

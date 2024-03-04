# Order System
Use Optimistic Lock prevent product overselling.

Order create process
![progress.jpg](img%2Fprogress.jpg)

# Timeline
3/1 
1. start project and setup env
2. design the db schema ver.1
3. service -> spring boot 3
4. ORM -> mybatis
5. DB -> mySQL + redis
6. cache -> spring cache + redis


3/2 
1. check mybatis not support spring boot 3 
2. change to use jdbc
3. create user api
4. create order api
5. create product api
6. create statistics api
7. add cache on user

3/3 
1. fix calculation bug
2. add role for user
3. add liquibase
4. add docker-compose
5. add new user for query
6. adjustment table constraint
7. fix user bug
8. add general advice

3/4
1. add login api and return jwt token
2. add customer advice
3. fix page sql

# TODO
1. add security for api user
2. optimize create order api
3. add message queue for order creating
4. repository interface abstraction
5. add unit test

# Swagger
http://localhost:8080/swagger-ui/index.html#/

# Develop Environment
JDK 17 +

# Tech Stack
Spring boot 3
Spring Cache
jdbc
validation
redis
mySQL
liquibase

# Run Application
1. Clone the project
2. open the project file
3. `$cd database-compose` && run `docker-compose up -d`
4. mvn clean install -U
5. `mvn spring-boot: run`

# Run Test
To be added

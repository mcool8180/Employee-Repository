kill -9 $(lsof -t -i:8081 -sTCP:LISTEN)

mvn spring-boot:run &

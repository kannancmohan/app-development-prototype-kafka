# kafka-consumer

## Build and Run

    ## Prerequisite
    * maven 
    * java 17
    * Install Docker for running kafka instance localy. check the docker-compose.yml file

    ## Build
    * mvn clean install

    ## Run
    * mvn spring-boot:run

## spring actuator

    http://localhost:8882/actuator
    spring clould bindings: http://localhost:8882/actuator/bindings
    spring cloud integrationgraph: http://localhost:8882/actuator/integrationgraph
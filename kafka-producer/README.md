# kafka-producer

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

    http://localhost:8883/actuator
    spring clould bindings: http://localhost:8883/actuator/bindings
    spring cloud integrationgraph: http://localhost:8883/actuator/integrationgraph
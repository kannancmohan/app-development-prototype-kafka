# kafka-processor
    sample processor that consumes a kafka topic process it and sents the result to another topic

## Build and Run

    ## Prerequisite
    * maven 
    * java 17
    * Install Docker for running kafka instance localy. check the docker-compose.yml file

    ## Build
    * To Build project : mvn clean install
    * To skip integration test : mvn clean install -Dskip.integration.test=true
    * To format code : mvn git-code-format:format-code

    ## Run
    * start the kafka broker
    * mvn spring-boot:run

    ## Testing 

## spring actuator

    http://localhost:8884/actuator
    spring clould bindings: http://localhost:8884/actuator/bindings
    spring cloud integrationgraph: http://localhost:8884/actuator/integrationgraph
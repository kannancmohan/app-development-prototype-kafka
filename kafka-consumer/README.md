# kafka-consumer
    sample kafka consumer using spring-cloud-stream-binder-kafka

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
    * mvn spring-boot:run

## spring actuator

    http://localhost:8882/actuator
    spring clould bindings: http://localhost:8882/actuator/bindings
    spring cloud integrationgraph: http://localhost:8882/actuator/integrationgraph
# app-development-prototype-kafka  
    Kafka consumer and producer implementations

## project initial setup
    Add supressions.xml for maven-checkstyle-plugin
    Add .mvn/jvm.config to fix issue with git-code-format-maven-plugin . check https://github.com/Cosium/git-code-format-maven-plugin
    Add lombok.config for lombok
    Add .gitignore

## Project IDE initial setup
    Add google-java-format plugin to intellij
    Add google code style for intellij from https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
    [Optional] Add spotbugs plugin https://plugins.jetbrains.com/plugin/14014-spotbugs
    [Optional] Add sonarlint plugin https://plugins.jetbrains.com/plugin/7973-sonarlint
    [Optional] Add GitToolBox plugin https://plugins.jetbrains.com/plugin/7499-gittoolbox

## Build and Run
    ## Prerequisite
    * maven 
    * java 17
    * Install Docker for running kafka instance localy. check the docker-compose.yml file

    ## Build
    * mvn clean install

    ## Run
    check module specific README file

## spring actuator
    http://localhost:8882/actuator

## Testing kafka consumer and producer with kafka cli
    * download kafka binary from https://kafka.apache.org/downloads
    * extract the downloaded binary and exceute the commands from kafka-binary/bin folder
    * if you are connecting to a secure kafka instance add a configuration file(kafka.properties) with the connect details
    eg: This is an example configuration file for connecting to kafka server with jaas authentication
    ```
    #bootstarp.servers=<kafka-server-host>:<9092>
    ssl.endpoint.identification.algorithm=https
    security.protocol=SASL_SSL
    sasl.mechanism=PLAIN
    sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="<USER-NAME>" password="<PASSWORD>";
    ```
    ##To list all topics in a kafka server
    kafka-topics.sh --bootstrap-server <kafka-server-host>:<9092> --command-config kafka.properties --list --exclude-internal
    
    ##To view a topic configuration
    kafka-topics.sh --bootstrap-server <kafka-server-host>:<9092> --command-config kafka_dev.properties --describe --topic <topic-name>

    ##To send data to a topic
    kafka-console-producer.sh --bootstrap-server <kafka-server-host>:<9092> --producer.config kafka_dev.properties --topic <topic-name> 
    
    ##To consume data from a topic (from the begining)
    kafka-console-consumer.sh --bootstrap-server <kafka-server-host>:<9092> --consumer.config kafka_dev.properties --topic <topic-name> --from-beginning --group=<consumer-group-name> 

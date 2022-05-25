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
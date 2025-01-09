$WORKING_DIR = Get-Location
$CURRENT_DIR = (Get-Item -Path $WORKING_DIR).Name
$NETWORK_NAME = "mqtt-network"
$DOCKER_IMAGE = "openjdk:17"

$CONTAINER_RUNTIME_NAME = "$CURRENT_DIR-openjdk-17"

docker run -it --rm `
    --name $CONTAINER_RUNTIME_NAME `
    -v "$WORKING_DIR/target/rest-api-0.0.1-SNAPSHOT.jar:/app/rest-api-0.0.1-SNAPSHOT.jar" `
    --net $NETWORK_NAME `
    -p 8085:8080 `
    -e MYSQL_ROOT_PASSWORD='root' `
    -e MYSQL_USERNAME='root' `
    $DOCKER_IMAGE java -jar /app/rest-api-0.0.1-SNAPSHOT.jar

# Helios Station

This project can be used to publish `xml` messages from a fictional `station` referenced by a provided `city`.

## How to use it

#### Building

First you need to build the project so that you end up with the application artifact.

`docker run -it --rm -v "$(pwd)":/app -w /app maven:3.6.3-jdk-14 mvn clean package`

#### Running it

You can run as many "weather stations" as you like at the same time.  
You can start a weather station with the following command:

`docker run -it --rm --network="host" -v "$(pwd)/target":/app -w /app openjdk:14.0.2-jdk-slim java -jar helios-station.jar --city Copenhagen --sorted`

##### Arguments

* `--city=<CITY>` is the name of the city where the weather station is.
* `--sorted` is a flag to indicate if the messages should have timestamps in chronological order.
If not provided, messages will not be sent in chronological order.


##### Dependencies

While the application will run just fine without a Kafka topic, it will not actually do what it was intended to.  
By default, the application will expect to find a Kafka server at address `localhost:9092`.  
The server address is configurable by means of environment variable `KAFKA_BOOTSTRAP_SERVERS`. 

Don't worry if you don't have the Kafka server ready when you start the application.
It will pick one up if you start it later, but old messages that were supposed to be sent will have been lost.

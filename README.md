# BookingGo Techincal Test Submission

## Prerequisites
Java 1.7+ and Maven is required. Project developed in Java 1.8.

## Setup
```
$ git clone https://github.com/sivachokkapu/bookinggo_technical_test.git
$ cd bookinggo_technical_test
$ mvn clean install
$ mvn install dependency:copy-dependencies
```

## Part 1

### Console application to print the search results for Dave's Taxis

`$ java -jar out/artifacts/bookinggo_tech_test_jar/bookinggo_tech_test.jar 1 51.470020,-0.454295 3.410632,-2.157533`

#### Output

### Console application to print the search results for Dave's Taxis filtered by number of passengers

`$ java -jar out/artifacts/bookinggo_tech_test_jar/bookinggo_tech_test.jar 2 51.470020,-0.454295 3.410632,-2.157533 5`

#### Output

### Console application to print cheapest suppliers (can be filtered by passengers)

`$ java -jar out/artifacts/bookinggo_tech_test_jar/bookinggo_tech_test.jar 3 51.470020,-0.454295 3.410632,-2.157533 5`

#### Output

## Part 2

`java -jar target/demo-0.0.1-SNAPSHOT.jar`

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

Parameters: 
- [0] - output option {1,2,3}
- [1] - pickup {lat,long}
- [2] - dropoff {lat,long}
- [3] - number of passengers {n}

### Console application to print the search results for Dave's Taxis

```
$ java -jar out/artifacts/bookinggo_tech_test_jar/bookinggo_tech_test.jar 1 51.470020,-0.454295 3.410632,-2.157533
```

#### Output:
```
STANDARD - 15149
EXECUTIVE - 336932
LUXURY - 904332
PEOPLE_CARRIER - 826826
```

### Console application to print the search results for Dave's Taxis filtered by number of passengers

```
$ java -jar out/artifacts/bookinggo_tech_test_jar/bookinggo_tech_test.jar 2 51.470020,-0.454295 3.410632,-2.157533 5
```

#### Output:

```
PEOPLE_CARRIER - 681655
LUXURY_PEOPLE_CARRIER - 449482
MINIBUS - 241250
```

### Console application to print cheapest suppliers (can be filtered by passengers)

```
$ java -jar out/artifacts/bookinggo_tech_test_jar/bookinggo_tech_test.jar 3 51.470020,-0.454295 3.410632,-2.157533 5
```

#### Output:

```
PEOPLE_CARRIER - Dave's Taxis - 300845
MINIBUS - Jeff's Taxis - 48463
LUXURY_PEOPLE_CARRIER - Jeff's Taxis - 299065
```

## Part 2

Run with command:

```
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### Get Dave's Taxis available cars through REST api

```
http://localhost:8080/rideways/daves-taxis?pickup=51.470020,-0.454295&dropoff=51.470020,-0.454295&noOfPassengers=1
http://localhost:8080/rideways/daves-taxis - uses default data
```
Values for dropoff, pickup and noOfPassengers can be changed.

#### Output:

```
[{"carType":"EXECUTIVE","price":317187,"supplier":"Dave's Taxis"},{"carType":"LUXURY","price":382330,"supplier":"Dave's Taxis"},{"carType":"PEOPLE_CARRIER","price":981795,"supplier":"Dave's Taxis"},{"carType":"LUXURY_PEOPLE_CARRIER","price":673448,"supplier":"Dave's Taxis"}]
```

### Get cheapest suppliers through REST api

```
http://localhost:8080/rideways/cheapest-cars?pickup=51.470020,-0.454295&dropoff=51.470020,-0.454295&noOfPassengers=1
http://localhost:8080/rideways/cheapest-cars - uses default data
```
Values for dropoff, pickup and noOfPassengers can be changed.

#### Output:
```
[{"carType":"MINIBUS","price":39243,"supplier":"Jeff's Taxis"},{"carType":"PEOPLE_CARRIER","price":202705,"supplier":"Eric's Taxis"},{"carType":"EXECUTIVE","price":196925,"supplier":"Eric's Taxis"},{"carType":"LUXURY","price":398083,"supplier":"Dave's Taxis"},{"carType":"LUXURY_PEOPLE_CARRIER","price":548705,"supplier":"Jeff's Taxis"},{"carType":"STANDARD","price":176217,"supplier":"Jeff's Taxis"}]
```



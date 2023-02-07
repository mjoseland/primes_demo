## Requirements
* Java 19 JDK
* JAVA_HOME and PATH environment variables set to JDK 19

## How to Build/Run
```
./mvnw clean verify
java -jar .\target\primes_demo-0.0.1-SNAPSHOT.jar
```

## Usage Examples

Calculate all primes <= 100 with Sieve of Eratosthenes:
```
>e 100
```

Calculate all primes <= 1 trillion with Sieve of Atkin:
```
>a 1000000000
```

Exit the program:
```
>exit
```

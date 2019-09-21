# Project - Log Events Processor

Log event processor is the utility to process log file in predefined (JSON) format. Events in log file is processed and events taking more than 4ms are flag with alert and saved in DB.


### Prerequisites
Below softwares are required to build and execute Log Events Processor project

```
Java version 1.8 or higher
```

```
Gradle 4.12 or higher
```

```
GIT to checkout project
```

### Checkout Project

Checkout project from git repository using below commond

```
Go to directory where you want to download source code
```

And execute command
```
git clone https://github.com/shaileshshindegit/events-processor.git
```

## Build the application

```
Go to source code location.
```
For example if you have checkout code to location C:\java\sourceCode then change director to C:\java\sourceCode

```
cd events-processor
```

```
gradlew clean build
```


## Execute application

```
First build the project. Please follow above steps to build application
```

```
gradlew runApplication --args="<log_file_location>"
```

For example
```
gradlew runApplication --args="C:\\temp\logfile.txt"
```


## Running the tests

```
Go to source code location.
```

```
cd events-processor
```

Use below command to run tests

```
gradlew clean test -info
```



## Author
Shailesh Shinde



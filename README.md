# CSV File Reader Service

## Installation
1. Install Java 8
2. Install maven 3 or superior
3. Clone it
4. On project root folder, run
~~~~
clean package install spring-boot:run
~~~~

## Usage
1. By default, the application will look for /tmp/csv/sessions.csv file to query. There is an example file on test/resources folder that can be used
2. File path can be changed on application.properties file
3. To use the search endpoint, hit
~~~~
wget http://localhost:8080/cities?from=2010-08-04&to=2016-08-04&city=Berlin
~~~~
and exchange the parameters as you wish
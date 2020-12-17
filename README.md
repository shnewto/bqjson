[![GitHub Actions build](https://github.com/shnewto/bqjson/workflows/build/badge.svg?branch=main)](https://github.com/shnewto/bqjson/actions?query=workflow%3ACI)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.shnewto/bqjson.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.shnewto%22%20AND%20a:%22bqjson%22)

# bqjson
Serialize/Deserialize BigQuery TableResults (and TableResult adjacent types) to/from JSON.

# Example
```java
package com.github.shnewto.bqjson.SerDe;
/* ... */

class MyBigQueryService {
    void myBigQueryWork() {
        /* Get a table result from BigQuery */
        TableResult originalTableResult = bigQuery
                .create(JobInfo.newBuilder(queryConfig)
                .setJobId(jobId).build())
                .waitFor()
                .getQueryResults();
        
        /* Serialize TableResult to JSON */
        String jsonString = SerDe.toJson(originalTableResult);
        /* Deserialize the JSON back into a TableResult */
        TableResult reconstitutedTableResult = SerDe.fromJson(jsonString, TableResult.class);
        
        /* ... */
    }
}
```

# Why?

This package came about because I wanted to write tests for parts of applications that use 
BQ TableResults, but didn't want to have to actually interact with BQ to run them. I'd hoped
that writing TableResults to JSON files (that I could read in and use on the next test runs) would be trivial.
When it wasn't, I wrote this helper library so it could be.


# Notes

Please raise issues or make PRs if you have a question or spot a bug :heart::heart:

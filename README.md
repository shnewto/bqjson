![](https://github.com/shnewto/bqjson/workflows/CI/badge.svg?branch=main)

# bqjson
Serialize/Deserialize BigQuery TableResults (and TableResult adjacent types) to/from JSON.

# Example
```java
import bqjson.SerDe;
/* imports ... */   

class MyBigQueryService {
    void workToDo() {
        /* Get a table result from BigQuery */
        TableResult originalTableResult = bigQuery
                .create(JobInfo.newBuilder(queryConfig)
                .setJobId(jobId).build())
                .waitFor()
                .getQueryResults();
        
        /* Serialize TableResult to JSON */
        String jsonString = SerDe.toJson(tableResult);
        /* Deserialize the JSON back into a TableResult */
        TableResult reconstitutedTableResult = SerDe.fromJson(jsonString, TableResult.class);
    }
}
```

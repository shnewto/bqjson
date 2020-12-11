package bqjson.service;

import com.google.cloud.bigquery.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestQueryService {
    BigQuery bigQuery;

    public TestQueryService() {
        bigQuery = BigQueryOptions.getDefaultInstance().getService();
    }


    public TableResult runQuery(String query) throws InterruptedException {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        queryJob = queryJob.waitFor();

        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        return queryJob.getQueryResults();
    }

    public Map<String, String> tableResultToMap(TableResult tableResult) {
        Map<String, String> result = new HashMap<>();
        for (FieldValueList row : tableResult.iterateAll()) {
            if (row.size() == 2) {
                result.put(row.get(0).getStringValue(), row.get(1).getStringValue());
            }
        }
        return result;
    }


}

package bqjson.util;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import bqjson.service.TestQueryService;
import bqjson.transform.Decode;
import bqjson.transform.Encode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

    public static void saveQuery(TableResult tableResult, String path) throws IOException {
        Files.write(Paths.get(path), Encode.toJsonBytes(tableResult));
    }

    public static TableResult loadQuery(String path) throws IOException {
        return Decode.fromJson(new String(Files.readAllBytes(Paths.get(path))), TableResult.class);
    }

    public static void generateTestData() throws InterruptedException, IOException {
        String testQuery;
        TestQueryService testQueryService;
        String tableResultFile;
        String schemaFile;
        String fieldListFile;
        TableResult tableResult;
        Schema schema;
        FieldList fieldList;

        String dataset = System.getenv("BQTR_TEST_DATASET");
        if (dataset == null) {
            System.out.println("'BQTR_TEST_DATASET' wasn't present in environment");
        }

        String tablename = System.getenv("BQTR_TEST_TABLENAME");
        if (dataset == null) {
            System.out.println("'BQTR_TEST_TABLENAME' wasn't present in environment");
        }

        testQuery = String.format("" +
                "SELECT repository_name, repository_owner FROM %s.%s " +
                "WHERE repository_open_issues > 10 AND repository_watchers > 10 " +
                "GROUP BY repository_name, repository_owner;", dataset, tablename);

        testQueryService = new TestQueryService();

        ClassLoader classLoader = TestUtils.class.getClassLoader();
        String tableResult_s = "src/test/resources/tableResult_s.json";
        String schema_s = "src/test/resources/schema_s.json";
        String fieldListFile_s = "src/test/resources/fieldListFile_s.json";

        tableResult = testQueryService.runQuery(testQuery);
        schema = tableResult.getSchema();
        fieldList = schema.getFields();

        Files.write(Paths.get(tableResult_s), Encode.toJsonBytes(tableResult));
        Files.write(Paths.get(schema_s), Encode.toJsonBytes(schema));
        Files.write(Paths.get(fieldListFile_s), Encode.toJsonBytes(fieldList));
    }

}

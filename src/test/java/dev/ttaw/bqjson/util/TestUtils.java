package dev.ttaw.bqjson.util;

import dev.ttaw.bqjson.SerDe;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import dev.ttaw.bqjson.service.TestQueryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {
    static String testQuery = "SELECT * FROM %s.%s LIMIT 1000;";
    static TestQueryService testQueryService;
    public static List<TableResult> tableResults = new ArrayList<>();
    public static List<Schema> schemas = new ArrayList<>();
    public static List<FieldList> fieldLists = new ArrayList<>();

    public static void saveQuery(TableResult tableResult, String path) throws IOException {
        Files.write(Paths.get(path), SerDe.toJsonBytes(tableResult));
    }

    public static TableResult loadQuery(String path) throws IOException {
        return SerDe.fromJson(new String(Files.readAllBytes(Paths.get(path))), TableResult.class);
    }

    private static void writeTableResultWithSetup(String dataset, String table) throws InterruptedException, IOException {
        TableResult tableResult = testQueryService.runQuery(String.format(testQuery, dataset, table));
        tableResults.add(tableResult);
        Schema schema = tableResult.getSchema();
        schemas.add(schema);
        FieldList fieldList = schema.getFields();
        fieldLists.add(fieldList);

        String tableResult_f = String.format("src/test/resources/tableResult_%s.json", table);
        String schema_f = String.format("src/test/resources/schema_%s.json", table);
        String fieldListFile_f = String.format("src/test/resources/fieldList_%s.json", table);

        Files.write(Paths.get(tableResult_f), SerDe.toJsonBytes(tableResult));
        Files.write(Paths.get(schema_f), SerDe.toJsonBytes(schema));
        Files.write(Paths.get(fieldListFile_f), SerDe.toJsonBytes(fieldList));
    }

    public static void generateTestData() throws InterruptedException, IOException {
        // Need environment set for generating new test data:
        // export BQTR_TEST_DATASET="<datasetname>"
        // export BQTR_TEST_TABLENAMES="<comma separated list of tablanames>"

        String dataset = System.getenv("BQTR_TEST_DATASET");
        if (dataset == null) {
            System.out.println("'BQTR_TEST_DATASET' wasn't present in environment");
        }

        String tables = System.getenv("BQTR_TEST_TABLENAMES");
        if (tables == null) {
            System.out.println("'BQTR_TEST_TABLENAME' wasn't present in environment");
        }

        String[] tablesList = tables.split(",");

        testQueryService = new TestQueryService();

        for( String table : tablesList) {
            writeTableResultWithSetup(dataset, table);
        }
    }

}

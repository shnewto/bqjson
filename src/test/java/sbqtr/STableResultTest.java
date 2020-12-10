package sbqtr;

import sbqtr.service.TestQueryService;
import com.google.cloud.bigquery.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import sbqtr.transform.Deserializer;
import sbqtr.transform.Serializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class STableResultTest {
    private final Gson gson = new Gson();
    private String testQuery;
    private TestQueryService testQueryService;
    private String tr_001;
    private String tr_002;

    @BeforeAll
    void setUp() {
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

        ClassLoader classLoader = getClass().getClassLoader();
        tr_001 = new File(classLoader.getResource("tr_001.json").getFile()).getAbsolutePath();
        tr_002 = new File(classLoader.getResource("tr_002.json").getFile()).getAbsolutePath();
    }

    @AfterAll
    void tearDown() {
    }


    @Test
    void runAndCompareLiveAndSerialized() throws IOException, InterruptedException {
        TableResult tableResult = testQueryService.runQuery(testQuery);
        saveQuery(tableResult, tr_002);
        TableResult deserialized = loadQuery(tr_002);
        TableResult actual = testQueryService.runQuery(testQuery);

        assertThat(deserialized).isExactlyInstanceOf(TableResult.class);
    }

    @Test
    void runAndSerializeQuery() throws InterruptedException, IOException {
        TableResult tableResult = testQueryService.runQuery(testQuery);
        saveQuery(tableResult, tr_001);
    }

    @Test
    void runDeserializedQuery() throws IOException {
        TableResult tableResult = loadQuery(tr_001);
        Map<String, String> result = testQueryService.tableResultToMap(tableResult);

        assertThat(result.size()).isEqualTo(4936);
        assertThat(result.get("ember.js")).isEqualTo("emberjs");
        assertThat(result.get("request")).isEqualTo("mikeal");
        assertThat(result.get("droid-fu")).isEqualTo("kaeppler");
        assertThat(result.get("v2ex")).isEqualTo("livid");
        assertThat(result.get("arcemu")).isEqualTo("arcemu");
        assertThat(result.get("fancyBox")).isEqualTo("fancyapps");
    }

    @Test
    void runQuery() throws InterruptedException {
        TableResult tableResult = testQueryService.runQuery(testQuery);
        Map<String, String> result = testQueryService.tableResultToMap(tableResult);

        assertThat(result.size()).isEqualTo(4936);
        assertThat(result.get("ember.js")).isEqualTo("emberjs");
        assertThat(result.get("request")).isEqualTo("mikeal");
        assertThat(result.get("droid-fu")).isEqualTo("kaeppler");
        assertThat(result.get("v2ex")).isEqualTo("livid");
        assertThat(result.get("arcemu")).isEqualTo("arcemu");
        assertThat(result.get("fancyBox")).isEqualTo("fancyapps");
    }

    void saveQuery(TableResult tableResult, String fpath) throws IOException {
        Files.write(Paths.get(fpath), Serializer.toJsonBytes(tableResult));
    }

    TableResult loadQuery(String fpath) throws IOException {
        return Deserializer.fromJson(new String(Files.readAllBytes(Paths.get(fpath))), TableResult.class);
    }
}


package dev.ttaw.bqjson.context;

import dev.ttaw.bqjson.SerDe;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestContext {
    private static TestContext integrationTestContextInstance = null;
    public List<String> tableResultJsonList = new ArrayList<>();
    public List<String> schemaJsonList = new ArrayList<>();
    public List<String> fieldListJsonList = new ArrayList<>();
    public List<TableResult> tableResults = new ArrayList<>();
    public List<Schema> schemas = new ArrayList<>();
    public List<FieldList> fieldLists = new ArrayList<>();

    private TestContext() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        File resDir = new File(Paths.get("src","test", "resources").toString());

        File[] foundFiles;

        foundFiles = resDir.listFiles((_dir, name) -> name.startsWith("tableResult_"));

        Arrays.stream(foundFiles).forEach(f -> {
            try {
                String json = new String(Files.readAllBytes(Paths.get(f.toString())));
                tableResultJsonList.add(json);
                tableResults.add(SerDe.fromJson(json, TableResult.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        foundFiles = resDir.listFiles((_dir, name) -> name.startsWith("schema_"));

        Arrays.stream(foundFiles).forEach(f -> {
            try {
                String json = new String(Files.readAllBytes(Paths.get(f.toString())));
                schemaJsonList.add(json);
                schemas.add(SerDe.fromJson(json, Schema.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        foundFiles = resDir.listFiles((_dir, name) -> name.startsWith("fieldList_"));

        Arrays.stream(foundFiles).forEach(f -> {
            try {
                String json = new String(Files.readAllBytes(Paths.get(f.toString())));
                fieldListJsonList.add(json);
                fieldLists.add(SerDe.fromJson(json, FieldList.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static TestContext getInstance() throws InterruptedException, IOException {
        // To ensure only one instance is created
        if (integrationTestContextInstance == null) {
            integrationTestContextInstance = new TestContext();
        }
        return integrationTestContextInstance;
    }
}

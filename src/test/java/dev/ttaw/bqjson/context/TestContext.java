package dev.ttaw.bqjson.context;

import dev.ttaw.bqjson.SerDe;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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


        List<String> tableResultFiles = Arrays.asList(
                "tableResult_fda_food.json",
                "tableResult_github_timeline.json",
                "tableResult_world_port_index.json");

        tableResultFiles.forEach(f -> {
            String json = null;
            try {
                json = new String(Files.readAllBytes(Paths.get(classLoader.getResource(f).toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            tableResultJsonList.add(json);
            tableResults.add(SerDe.fromJson(json, TableResult.class));
        });

        List<String> schemaFiles = Arrays.asList(
                "schema_fda_food.json",
                "schema_github_timeline.json",
                "schema_world_port_index.json");

        schemaFiles.forEach(f -> {
            String json = null;
            try {
                json = new String(Files.readAllBytes(Paths.get(classLoader.getResource(f).toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            schemaJsonList.add(json);
            schemas.add(SerDe.fromJson(json, Schema.class));
        });

        List<String> fieldListFiles = Arrays.asList(
                "fieldList_fda_food.json",
                "fieldList_github_timeline.json",
                "fieldList_world_port_index.json");

        fieldListFiles.forEach(f -> {
            String json = null;
            try {
                json = new String(Files.readAllBytes(Paths.get(classLoader.getResource(f).toURI())));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            fieldListJsonList.add(json);
            fieldLists.add(SerDe.fromJson(json, FieldList.class));
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

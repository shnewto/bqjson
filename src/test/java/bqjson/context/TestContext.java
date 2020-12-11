package bqjson.context;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import bqjson.transform.Decode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestContext {
    private static TestContext integrationTestContextInstance =null;
    public String tableResultFile;
    public String schemaFile;
    public String fieldListFile;
    public TableResult tableResult;
    public Schema schema;
    public FieldList fieldList;

    private TestContext() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        tableResultFile = new File(classLoader.getResource("tableResult_s.json").getFile()).getAbsolutePath();
        schemaFile = new File(classLoader.getResource("schema_s.json").getFile()).getAbsolutePath();
        fieldListFile = new File(classLoader.getResource("fieldListFile_s.json").getFile()).getAbsolutePath();

        tableResult = Decode.fromJson(new String(Files.readAllBytes(Paths.get(tableResultFile))), TableResult.class);
        schema = Decode.fromJson(new String(Files.readAllBytes(Paths.get(schemaFile))), Schema.class);
        fieldList = Decode.fromJson(new String(Files.readAllBytes(Paths.get(fieldListFile))), FieldList.class);
    }

    public static TestContext getInstance() throws InterruptedException, IOException {
        // To ensure only one instance is created
        if (integrationTestContextInstance == null)
        {
            integrationTestContextInstance = new TestContext();
        }
        return integrationTestContextInstance;
    }
}

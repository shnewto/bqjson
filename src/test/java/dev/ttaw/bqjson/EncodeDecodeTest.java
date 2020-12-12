package dev.ttaw.bqjson;

import dev.ttaw.bqjson.SerDe;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import dev.ttaw.bqjson.context.TestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EncodeDecodeTest {
    TestContext context;
    @BeforeAll
    void setUp() throws InterruptedException, IOException {
        context = TestContext.getInstance();
    }

    @Test
    void testTableResultToJsonAndBack() {
        context.tableResults.forEach(t -> {
            String tableResultJson = SerDe.toJson(t);
            TableResult tableResult = SerDe.fromJson(tableResultJson, TableResult.class);
            assertThat(tableResult).isEqualTo(t);
        });
    }

    @Test
    void testTableResultToJsonBytesAndBack() {
        context.tableResults.forEach(t -> {
            byte[] tableResultBytes = SerDe.toJsonBytes(t);
            TableResult tableResult = SerDe.fromJson(tableResultBytes, TableResult.class);
            assertThat(tableResult).isEqualTo(t);
        });
    }

    @Test
    void testSchemaToJsonAndBack() {
        context.schemas.forEach(s -> {
            String schemaJson = SerDe.toJson(s);
            Schema schema = SerDe.fromJson(schemaJson, Schema.class);
            assertThat(schema).isEqualTo(s);
        });
    }

    @Test
    void testSchemaToJsonBytesAndBack() {
        context.schemas.forEach(s -> {
            byte[] schemaBytes = SerDe.toJsonBytes(s);
            Schema schema = SerDe.fromJson(schemaBytes, Schema.class);
            assertThat(schema).isEqualTo(s);
        });
    }

    @Test
    void testFieldListToJsonAndBack() {
        context.fieldLists.forEach(f ->{
            String fieldListJson = SerDe.toJson(f);
            FieldList fieldList = SerDe.fromJson(fieldListJson, FieldList.class);
            assertThat(fieldList).isEqualTo(f);
        });
    }

    @Test
    void testFieldListToJsonBytesAndBack() {
        context.fieldLists.forEach(f -> {
            byte[] fieldListBytes = SerDe.toJsonBytes(f);
            FieldList fieldList = SerDe.fromJson(fieldListBytes, FieldList.class);
            assertThat(fieldList).isEqualTo(f);
        });
    }
}
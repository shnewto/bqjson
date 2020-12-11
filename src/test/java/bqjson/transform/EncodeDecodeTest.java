package bqjson.transform;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import bqjson.context.TestContext;

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
        String tableResultJson = Encode.toJson(context.tableResult);
        TableResult tableResult = Decode.fromJson(tableResultJson, TableResult.class);
        assertThat(tableResult).isEqualTo(context.tableResult);
    }

    @Test
    void testTableResultToJsonBytesAndBack() {
        byte[] tableResultBytes = Encode.toJsonBytes(context.tableResult);
        TableResult tableResult = Decode.fromJson(tableResultBytes, TableResult.class);
        assertThat(tableResult).isEqualTo(context.tableResult);
    }

    @Test
    void testSchemaToJsonAndBack() {
        String schemaJson = Encode.toJson(context.schema);
        Schema schema = Decode.fromJson(schemaJson, Schema.class);
        assertThat(schema).isEqualTo(context.schema);
    }

    @Test
    void testSchemaToJsonBytesAndBack() {
        byte[] schemaBytes = Encode.toJsonBytes(context.schema);
        Schema schema = Decode.fromJson(schemaBytes, Schema.class);
        assertThat(schema).isEqualTo(context.schema);
    }

    @Test
    void testFieldListToJsonAndBack() {
        String fieldListJson = Encode.toJson(context.fieldList);
        FieldList fieldList = Decode.fromJson(fieldListJson, FieldList.class);
        assertThat(fieldList).isEqualTo(context.fieldList);
    }

    @Test
    void testFieldListToJsonBytesAndBack() {
        byte[] fieldListBytes = Encode.toJsonBytes(context.fieldList);
        FieldList fieldList = Decode.fromJson(fieldListBytes, FieldList.class);
        assertThat(fieldList).isEqualTo(context.fieldList);
    }
}
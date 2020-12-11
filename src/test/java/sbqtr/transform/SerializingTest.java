package sbqtr.transform;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import sbqtr.context.TestContext;
import sbqtr.util.TestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SerializingTest {
    TestContext context;
    @BeforeAll
    void setUp() throws InterruptedException, IOException {
        context = TestContext.getInstance();
    }

    @Test
    void testTableResultToJsonAndBack() {
        String tableResultJson = Serializer.toJson(context.tableResult);
        TableResult tableResult = Deserializer.fromJson(tableResultJson, TableResult.class);
        assertThat(tableResult).isEqualTo(context.tableResult);
    }

    @Test
    void testTableResultToJsonBytesAndBack() {
        byte[] tableResultBytes = Serializer.toJsonBytes(context.tableResult);
        TableResult tableResult = Deserializer.fromJsonBytes(tableResultBytes, TableResult.class);
        assertThat(tableResult).isEqualTo(context.tableResult);
    }

    @Test
    void testSchemaToJsonAndBack() {
        String schemaJson = Serializer.toJson(context.schema);
        Schema schema = Deserializer.fromJson(schemaJson, Schema.class);
        assertThat(schema).isEqualTo(context.schema);
    }

    @Test
    void testSchemaToJsonBytesAndBack() {
        byte[] schemaBytes = Serializer.toJsonBytes(context.schema);
        Schema schema = Deserializer.fromJsonBytes(schemaBytes, Schema.class);
        assertThat(schema).isEqualTo(context.schema);
    }

    @Test
    void testFieldListToJsonAndBack() {
        String fieldListJson = Serializer.toJson(context.fieldList);
        FieldList fieldList = Deserializer.fromJson(fieldListJson, FieldList.class);
        assertThat(fieldList).isEqualTo(context.fieldList);
    }

    @Test
    void testFieldListToJsonBytesAndBack() {
        byte[] fieldListBytes = Serializer.toJsonBytes(context.fieldList);
        FieldList fieldList = Deserializer.fromJsonBytes(fieldListBytes, FieldList.class);
        assertThat(fieldList).isEqualTo(context.fieldList);
    }
}
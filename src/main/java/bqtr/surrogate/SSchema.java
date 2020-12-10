package bqtr.surrogate;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;

public class SSchema {
    public Schema toSchema() {
        return Schema.of(fields.toFieldList());
    }
    public FieldList getFields() {
        return fields.toFieldList();
    }

    final SFieldList fields;
    SSchema(Schema schema) {
        this.fields = new SFieldList(schema.getFields());
    }
}

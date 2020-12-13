package com.github.shnewto.bqjson.surrogate;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;

/**
 * Surrogate type to help Serializing/Deserializing BigQuery Schema.
 */
public class SSchema {
    public Schema toSchema() {
        return Schema.of(fields.toFieldList());
    }

    public FieldList getFields() {
        return fields.toFieldList();
    }

    final SFieldList fields;

    public SSchema(Schema schema) {
        this.fields = new SFieldList(schema.getFields());
    }
}

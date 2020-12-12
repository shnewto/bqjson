package dev.ttaw.bqjson.surrogate;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.FieldList;

import java.util.List;

/**
 * Surrogate type to help Serializing/Deserializing BigQuery FieldList.
 */
public class SFieldList {
    final List<Field> fields;

    public SFieldList(List<Field> fields) {
        this.fields = fields;
    }

    public FieldList toFieldList() {
        return com.google.cloud.bigquery.FieldList.of(fields);
    }
}

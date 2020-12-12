package dev.ttaw.bqjson.surrogate;


import com.google.api.gax.paging.Page;
import com.google.cloud.PageImpl;
import com.google.cloud.bigquery.*;

import java.util.ArrayList;
import java.util.List;

/** Surrogate type to help Serializing/Deserializing BigQuery TableResult. */
public class STableResult {
    final SSchema schema;
    final long totalRows;
    List<FieldValueList> fieldValueLists;

    public STableResult(TableResult tableResult) {
        schema = new SSchema(tableResult.getSchema());
        totalRows = tableResult.getTotalRows();
        fieldValueLists = new ArrayList<>();
        pageNoSchema(tableResult);
    }

    private void sanitizeFieldValueLists() {
        List<FieldValueList> sanitized = new ArrayList<>();
        for (List<FieldValue> values: fieldValueLists) {
            sanitized.add(FieldValueList.of(values, schema.getFields()));
        }
        fieldValueLists = sanitized;
    }

    private void pageNoSchema(TableResult tableResult) {
        tableResult.iterateAll().iterator().forEachRemaining(fieldValueLists::add);
    }

    private Page<FieldValueList> pageNoSchema() {
        return new PageImpl<>(
                (PageImpl.NextPageFetcher<FieldValueList>) () -> null,
                null,
                fieldValueLists);
    }

    public TableResult toTableResult() {
        sanitizeFieldValueLists();
        return new TableResult(schema.toSchema(), totalRows, pageNoSchema());
    }
}
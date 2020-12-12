package dev.ttaw.bqjson;

import dev.ttaw.bqjson.surrogate.SFieldList;
import dev.ttaw.bqjson.surrogate.SSchema;
import dev.ttaw.bqjson.surrogate.STableResult;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/** Serialize and Deserialize BigQuery TableResults (and TableResult adjacent types). */
public class SerDe {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SerDe.class);

    public static String toJson(TableResult tableResult) {
        Gson gson = new Gson();
        return gson.toJson(new STableResult(tableResult));
    }

    public static byte[] toJsonBytes(TableResult tableResult) {
        Gson gson = new Gson();
        return gson.toJson(new STableResult(tableResult)).getBytes();
    }

    public static String toJson(Schema schema) {
        Gson gson = new Gson();
        return gson.toJson(new SSchema(schema));
    }

    public static byte[] toJsonBytes(Schema schema) {
        Gson gson = new Gson();
        return gson.toJson(new SSchema(schema)).getBytes();
    }

    public static String toJson(FieldList fieldList) {
        Gson gson = new Gson();
        return gson.toJson(new SFieldList(fieldList));
    }

    public static byte[] toJsonBytes(FieldList fieldList) {
        Gson gson = new Gson();
        return gson.toJson(new SFieldList(fieldList)).getBytes();
    }

    public static <T> T fromJson(String object, Class<T> classOfT) throws ClassCastException {
        try {

            if (classOfT == TableResult.class) {
                return classOfT.cast(tableResultFromJson(object));
            }

            if (classOfT == Schema.class) {
                return classOfT.cast(schemaFromJson(object));
            }

            if (classOfT == FieldList.class) {
                return classOfT.cast(fieldListFromJson(object));
            }
        } catch (Exception e) {
            log.error(e.toString());
        }

        return null;
    }

    public static <T> T fromJson(byte[] bytes, Class<T> classOfT) {
        return fromJson(new String(bytes, StandardCharsets.UTF_8), classOfT);
    }

    private static TableResult tableResultFromJson(String tableResultJson) {
        Gson gson = new Gson();
        return gson.fromJson(tableResultJson, STableResult.class).toTableResult();
    }

    private static Schema schemaFromJson(String schemaJson) {
        Gson gson = new Gson();
        return gson.fromJson(schemaJson, SSchema.class).toSchema();
    }

    private static FieldList fieldListFromJson(String fieldListJson) {
        Gson gson = new Gson();
        return gson.fromJson(fieldListJson, SFieldList.class).toFieldList();
    }
}

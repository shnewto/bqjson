package bqjson.transform;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;
import bqjson.surrogate.SFieldList;
import bqjson.surrogate.SSchema;
import bqjson.surrogate.STableResult;

import java.nio.charset.StandardCharsets;

public class Decode {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Decode.class);

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

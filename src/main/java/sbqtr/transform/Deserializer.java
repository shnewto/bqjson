package sbqtr.transform;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;
import sbqtr.SFieldList;
import sbqtr.SSchema;
import sbqtr.STableResult;

import java.nio.charset.StandardCharsets;

public class Deserializer {
    public static <T> T fromJson(String object, Class<T> classOfT) {
        if (classOfT == Schema.class){
            return (T) schemaFromJson(object);
        }

        if (classOfT == FieldList.class){
            return (T) fieldListFromJson(object);
        }

        if (classOfT == TableResult.class){
            return (T) tableResultFromJson(object);
        }

        return null;
    }

    public static <T> T fromJsonBytes(byte[] bytes, Class<T> classOfT) {
        String object = new String(bytes, StandardCharsets.UTF_8);
        if (classOfT == Schema.class){
            return (T) schemaFromJson(object);
        }

        if (classOfT == FieldList.class){
            return (T) fieldListFromJson(object);
        }

        if (classOfT == TableResult.class){
            return (T) tableResultFromJson(object);
        }

        return null;
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

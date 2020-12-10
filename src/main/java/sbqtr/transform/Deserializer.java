package sbqtr.transform;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;
import sbqtr.SFieldList;
import sbqtr.SSchema;
import sbqtr.STableResult;

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

    public static TableResult tableResultFromJson(String tableResultJson) {
        Gson gson = new Gson();
        return gson.fromJson(tableResultJson, STableResult.class).toTableResult();
    }

    public static Schema schemaFromJson(String schemaJson) {
        Gson gson = new Gson();
        return gson.fromJson(schemaJson, SSchema.class).toSchema();
    }

    public static FieldList fieldListFromJson(String fieldListJson) {
        Gson gson = new Gson();
        return gson.fromJson(fieldListJson, SFieldList.class).toFieldList();
    }
}

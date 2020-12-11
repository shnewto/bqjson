package bqjson.transform;

import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.Schema;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.Gson;
import bqjson.surrogate.SFieldList;
import bqjson.surrogate.SSchema;
import bqjson.surrogate.STableResult;

public class Encode {
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
}

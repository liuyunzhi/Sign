package com.cdut.sign.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Json {

    private JSONObject jsonObject;

    public Json(String json) throws JSONException {
        this.jsonObject = new JSONObject(json);
    }

    public String get(String key) throws JSONException {
        return jsonObject.getString(key);
    }
}

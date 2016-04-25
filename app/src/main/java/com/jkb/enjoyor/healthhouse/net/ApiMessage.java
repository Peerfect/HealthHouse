package com.jkb.enjoyor.healthhouse.net;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class ApiMessage {

    public int Code = 1001;
    public String Msg = "";
    public String Data = "";

    public ApiMessage() {
        Code = 1001;
        Msg = "Success";
        Data = "";
    }

    public static ApiMessage FromJson(String jsonText) {
        if (jsonText.isEmpty())
            return null;

        ApiMessage newMsg = new ApiMessage();

        try {
            // create json analyst
            JSONTokener analyst = new JSONTokener(jsonText);

            // fetch message
            JSONObject message = new JSONObject(jsonText);
            newMsg.Code = message.getInt("Errcode");
            newMsg.Msg = message.getString("Msg");
            newMsg.Data = message.getString("Data");

        } catch (JSONException ex) {
            // handle exception
            newMsg.Code = -1;
            newMsg.Msg = ex.getMessage();
        }

        return newMsg;
    }

    public static ApiMessage Create(int code, String msg, String data) {
        ApiMessage api = new ApiMessage();

        api.Code = code;
        api.Msg = msg;
        api.Data = data;

        return api;
    }

    public void Set(int code, String msg, String data) {
        this.Code = code;
        this.Msg = msg;
        this.Data = data;
    }
}

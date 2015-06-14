package io.skyrom.taxmachina.utils;

import org.json.JSONObject;

/**
 *
 * @author petros
 * @param <T>
 */
public class ResponseMessage<T extends Object> {

    private T data;
    private int code;
    private String message;
    private String locale;
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        json.put("locale", locale);
        json.put("data", data);
        return json;
    }

    public T getData() {
        return data;
    }

    public void setData( T data ) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode( int code ) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale( String locale ) {
        this.locale = locale;
    }

}

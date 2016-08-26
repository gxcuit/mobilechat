package com.example.webmobilegroupchat;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Utils {
	 
    private Context context;
    private SharedPreferences sharedPref;
 
    private static final String KEY_SHARED_PREF = "ANDROID_WEB_CHAT";
    private static final int KEY_MODE_PRIVATE = 0;
    private static final String KEY_SESSION_ID = "sessionId",
            FLAG_MESSAGE = "message";
 
    public Utils(Context context) {
        this.context = context;
        sharedPref = this.context.getSharedPreferences(KEY_SHARED_PREF,
                KEY_MODE_PRIVATE);
    }
 
    public void storeSessionId(String sessionId) {
        Editor editor = sharedPref.edit();
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.commit();
    }
 
    public String getSessionId() {
        return sharedPref.getString(KEY_SESSION_ID, null);
    }
 
    public String getSendMessageJSON(String message) {
        String json = null;
 
        try {
            JSONObject jObj = new JSONObject();
            jObj.put("flag", FLAG_MESSAGE);
            jObj.put("sessionId", getSessionId());
            jObj.put("message", message);
 
            json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
 
        return json;
    }
    
    public String getSendOneJson(String message,String toId){
    	JSONObject json = new JSONObject();
    	try {
			json.put("flag", "toOne");
			json.put("sessionId", getSessionId());
			json.put("message", message);
			json.put("toId", toId);
			return json.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "";
    	
    	
    }
 
}
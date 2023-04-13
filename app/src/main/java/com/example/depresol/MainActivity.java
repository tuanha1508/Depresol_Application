package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Fragment {


    RecyclerView recyclerView;
    EditText editText;
    ImageView imageView , back , analysis;
    ArrayList<Chatsmodal> chatsmodalArrayList;
    List<String> message_user = new ArrayList<String>();
    ChatAdapter chatAdapter;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private final String BOT_WAITING = "waiting";
    private Boolean is_done_send_data = true;
    private PrintWriter output;
    private BufferedReader input;
    Thread Thread1 = null;


    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        Thread1 = new Thread(new Thread1());
        Thread1.start();
        try{
            super.onCreate(savedInstanceState);
        }catch (Exception ex){
            Log.e("error" , ex.toString());
        }
        View view = inflater.inflate(R.layout.activity_chat , container , false);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_recycler);
        editText = (EditText) view.findViewById(R.id.edt_msg);
        imageView = (ImageView) view.findViewById(R.id.send_btn);
        analysis = (ImageView) view.findViewById(R.id.send_analysic);
        back = view.findViewById(R.id.ic_back);
        chatsmodalArrayList = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatsmodalArrayList,getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(chatAdapter);
        //run_python();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this.getContext(),MainActivity_Menu.class);
                startActivity(intent);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(),"Please enter your message",Toast.LENGTH_SHORT).show();
                    return;
                }
                String mess = editText.getText().toString();
                message_user.add(mess);
                addToChat(mess , USER_KEY);
                editText.setText("");
                getResponse(mess);
            }
        });
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Thread3("SENT DATA" , true)).start();
            }
        });
        return view;
    }

    private void getResponse(String message) {


        chatgpt chatgpt = new chatgpt();
        String bot_rep = chatgpt.run(message);

        addResponse(bot_rep);

    }
    private List<String> process_respone(String message){
        List<String> data = new ArrayList<String>();
        int last = message.indexOf('.') , begin = 0;
        while(last!=-1){
            String tmp = message.substring(begin , last);
            data.add(tmp);
            begin = last;
            last = message.indexOf('.' , last+1);
        }
        return data;
    }
    void addToChat(String message, String sendBy){
        getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        if(sendBy == "user"){
                            chatsmodalArrayList.add(new Chatsmodal(message,sendBy));
                            chatsmodalArrayList.add(new Chatsmodal(message,BOT_WAITING));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(chatsmodalArrayList.size() - 1);
                        } else {

                            chatsmodalArrayList.add(new Chatsmodal(message,sendBy));
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.smoothScrollToPosition(chatsmodalArrayList.size() - 1);
                        }
                    }catch (Exception e){
                        e.toString();
                    }
                }
            });
    }
    void addResponse(String response){
        chatsmodalArrayList.remove(chatsmodalArrayList.size()-1);
        addToChat(response, BOT_KEY);
    }
    class Thread1 implements Runnable {
        public void run() {
            Socket socket;
            try {
                socket = new Socket("192.168.44.2", 9990);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                output = new PrintWriter(outputStreamWriter, true);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                new Thread(new Thread2()).start();
                Log.i("Connect" , "Done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class Thread2 implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    final String json = input.readLine();
                    if (json != null) {
                        Gson gson = new Gson();
                        Log.i("Result" , json);
                        Intent intent = new Intent(MainActivity.this.getContext(), Result_analysis.class);
                        intent.putExtra("data", json);
                        startActivity(intent);
                    } else {
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class Thread3 implements Runnable {
        private String message;
        private boolean sent_list;
        Thread3(String message , boolean sent_list) {
            this.message = message;
            this.sent_list = sent_list;
        }
        @Override
        public void run() {
            if(sent_list){
                Gson gson = new Gson();
                String jsonString = gson.toJson(message_user);
                output.print(jsonString);
                output.flush();
            }
        }
    }
}
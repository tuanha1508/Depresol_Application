package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Fragment {


    RecyclerView recyclerView;
    EditText editText;
    ImageView imageView , back;
    ArrayList<Chatsmodal> chatsmodalArrayList;
    ChatAdapter chatAdapter;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private final String BOT_WAITING = "waiting";

    Python py;
    PyObject pyboj , obj;

    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
        run_python();
        try{
            super.onCreate(savedInstanceState);
        }catch (Exception ex){
            Log.e("error" , ex.toString());
        }
        View view = inflater.inflate(R.layout.activity_chat , container , false);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_recycler);
        editText = (EditText) view.findViewById(R.id.edt_msg);
        imageView = (ImageView) view.findViewById(R.id.send_btn);
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
                chatsmodalArrayList.add(new Chatsmodal(editText.getText().toString(),USER_KEY));
                chatAdapter.notifyDataSetChanged();

                getResponse(editText.getText().toString());
            }
        });
        return view;
    }
    void run_python(){
    }
    private void getResponse(String message) {
        editText.setText("");
        chatgpt chatgpt = new chatgpt();
        chatsmodalArrayList.add(new Chatsmodal(message,BOT_WAITING));
        chatAdapter.notifyDataSetChanged();
        String bot_rep = chatgpt.run(message);
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        chatsmodalArrayList.remove(chatsmodalArrayList.size() - 1);
        chatAdapter.notifyDataSetChanged();

        chatsmodalArrayList.add(new Chatsmodal(bot_rep, BOT_KEY));
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
        chatAdapter.notifyDataSetChanged();

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
}
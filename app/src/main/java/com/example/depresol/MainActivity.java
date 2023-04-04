package com.example.depresol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.ArrayList;

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
                getResponse(editText.getText().toString());
                editText.setText("");
            }
        });
        return view;
    }
    void run_python(){
        if(!Python.isStarted()) Python.start(new AndroidPlatform(MainActivity.this.getContext()));
        py = Python.getInstance();
        pyboj = py.getModule("chatgpt");
    }
    private void getResponse(String message) {
        chatsmodalArrayList.add(new Chatsmodal(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        obj = pyboj.callAttr("main",message);
        chatsmodalArrayList.add(new Chatsmodal(obj.toString(),BOT_KEY));
        chatAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
        chatAdapter.notifyDataSetChanged();
    }
}
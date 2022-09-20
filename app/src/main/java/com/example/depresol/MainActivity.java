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
    public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState) {
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
    private void getResponse(String message) {
        chatsmodalArrayList.add(new Chatsmodal(message,USER_KEY));
        chatAdapter.notifyDataSetChanged();
        String url = "http://api.brainshop.ai/get?bid=166699&key=7BNL3cP7oGQUB9gJ&uid=[uid]&msg="+message;
        //String url = "http://api.brainshop.ai/get?bid=166699&key=7BNL3cP7oGQUB9gJ&uid=[uid]&msg" + message;
        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitApi retroFitApi = retrofit.create(RetroFitApi.class);
        Call<MsgModal> call = retroFitApi.getMessage(url);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()) {
                    MsgModal msgModal = response.body();
                    chatsmodalArrayList.add(new Chatsmodal(msgModal.getCnt(),BOT_KEY));
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatsmodalArrayList.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsmodalArrayList.add(new Chatsmodal("Bot chưa được thiết lập để trả lời cho câu trên",BOT_KEY));
                chatAdapter.notifyDataSetChanged();
            }
        });
    }
}
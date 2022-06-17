package com.example.depresol;

import static android.content.ContentValues.TAG;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.depresol.databinding.ActivityMainMenuBarBinding;
import com.example.depresol.BottomNavigationBehavior;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;



public class MainActivity_Menu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ActivityMainMenuBarBinding binding;
    FirebaseFirestore db;
    private static ArrayList<String> mArrayList = new ArrayList<>();;
    NavHostFragment navHostFragment;
    private BottomNavigationView bottomNavigationView;
    String name_user , url_avatar;
    TextView loi_chao , phat_ngon;
    int time_now;
    ImageView avatar;
    ArrayList<String> cau_noi_of_day = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainMenuBarBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.appBarMain.toolbar);
        setupNavigation();

        db = FirebaseFirestore.getInstance(); // firebase

        loi_chao = findViewById(R.id.loi_chao);
        avatar = findViewById(R.id.avatar_main_menu);
        phat_ngon = findViewById(R.id.phat_ngon);

        Calendar calendar = Calendar.getInstance();
        time_now = calendar.get(Calendar.HOUR_OF_DAY);


        name_user = getIntent().getStringExtra("name_user");
        url_avatar = getIntent().getStringExtra("url_avatar");
        String tmp  , gio;
        gio = Integer.toString(time_now);
        if(4 <= time_now && 10 >= time_now){
            tmp = "Chào buổi sáng nhaa ";
        } else if(11 <= time_now && 12 >= time_now){
            tmp = "Cậu nhớ nghỉ trưa nhaa ";
        } else if(13 <= time_now && 17 >= time_now){
            tmp = "Buổi chiều tốt lành nhaa ";
        } else {
            tmp = "Buổi tốt tốt lành ^.^ ";
        }
        if(name_user != null){
            loi_chao.setText(tmp + name_user );
        } else {
            loi_chao.setText(tmp + "Cậu");
        }

        if(url_avatar != null ){
            Picasso.get().load(url_avatar).into(avatar);
        }
    }

    private void setupNavigation() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.appBarMain.bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (navHostFragment != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        }
    }
    void xuli(String s){
        boolean ok = false;
        String tmp = "";
        for(int i = 0; i < s.length();i++){
            tmp = "";
            while ( s.charAt(i) != '=' && i+1<s.length() ){
                i++;
                ok = true;
            }
            i++;
            if(i>=s.length()) return;
            if(ok){
                while(  ( (s.charAt(i) < '0' && s.charAt(i) < '9') || (s.charAt(i) > '0' && s.charAt(i) > '9') ) && i +1 < s.length()){
                    tmp = tmp + s.charAt(i);
                    i++;
                }
                cau_noi_of_day.add(tmp);
                tmp ="";
                i--;
            }
            ok = false;
        }
    }
    public void get_cau_noi(){
        DocumentReference docRef = db.collection("Cau_noi").document("Cau_noi_cua_ngay");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //phat_ngon.setText(document.getData().toString());
                        xuli(document.getData().toString());
                        Log.d("D2 " , document.getData().toString());
                        for(String i:cau_noi_of_day){
                            Log.d("DD " , i);
                        }
                        phat_ngon.setText(cau_noi_of_day.get(0));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

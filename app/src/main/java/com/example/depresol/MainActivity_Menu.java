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
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
        get_cau_noi();
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
    public void get_cau_noi(){
        db.collection("Cau_noi")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("AAAA", document.getId() + " => " + document.getString("name"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

package com.example.dogapp.AdminViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dogapp.MainActivity;
import com.example.dogapp.R;
import com.example.dogapp.UserViews.Home;
import com.example.dogapp.session.SessionManager;

public class admin_landing_page extends AppCompatActivity {

    private ImageView iconHome, iconProf;
    private TextView addButton;
    private dog_list dogListFragment;
    private pending_dog pendingDogFragment;
    private add_update addOrUpdateFragment;
    private ImageView iconMenu;
    private TextView logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing_page);

        // initialize--
        iconHome = findViewById(R.id.iconHome);
        iconProf = findViewById(R.id.iconProf);
        addButton = findViewById(R.id.addBtn);
        iconMenu = findViewById(R.id.iconMenu);
        logoutBtn = findViewById(R.id.logoutBtn);

        dogListFragment = new dog_list();
        pendingDogFragment = new pending_dog();
        addOrUpdateFragment = new add_update();
        SessionManager sm = new SessionManager(this);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, dogListFragment)
                .commit();

        iconHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.VISIBLE);
                iconHome.setImageResource(R.drawable.icon_home_filled);
                iconProf.setImageResource(R.drawable.icon_profile);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, dogListFragment)
                        .commit();
            }
        });

        iconProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.GONE);
                iconProf.setImageResource(R.drawable.icon_profile_filled);
                iconHome.setImageResource(R.drawable.icon_home_outline);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, pendingDogFragment)
                        .commit();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.GONE);
                iconHome.setImageResource(R.drawable.icon_home_outline);
                iconProf.setImageResource(R.drawable.icon_profile);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new add_update())
                        .commit();

            }
        });


        iconMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutBtn.setVisibility(View.VISIBLE);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sm.clearSession();
                Toast.makeText(admin_landing_page.this, "Logged out", Toast.LENGTH_SHORT).show();
                // redirect to login page
                Intent intent = new Intent(admin_landing_page.this, MainActivity.class);
                finishActivity(0);
                startActivity(intent);

                logoutBtn.setVisibility(View.GONE);
            }
        });

    }
}
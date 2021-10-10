package com.example.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.schedule.Professor.ProfessorsFragment;
import com.example.schedule.Schedule.ScheduleFragment;
import com.example.schedule.Session.SessionFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String r="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Расписание");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        Intent intent = getIntent();
        hideItem(intent.getStringExtra("rights"));
        r = intent.getStringExtra("group");


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            ScheduleFragment scheduleFragment = new ScheduleFragment();
            Bundle bundle = new Bundle();
            bundle.putString("group",r);
            scheduleFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                   scheduleFragment).commit();
            navigationView.setCheckedItem(R.id.nav_schedule);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putString("group",r);
        switch (item.getItemId()){
            case R.id.nav_schedule:
                ScheduleFragment scheduleFragment = new ScheduleFragment();
                scheduleFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        scheduleFragment).commit();
                break;
            case R.id.nav_session:
                SessionFragment sessionFragment = new SessionFragment();
                sessionFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        sessionFragment).commit();
                break;
            case R.id.nav_professor:
                ProfessorsFragment professorsFragment = new ProfessorsFragment();
                professorsFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        professorsFragment).commit();
                break;
            case R.id.nav_change:
                ChangeFragment changeFragment = new ChangeFragment();
                changeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        changeFragment).commit();
                break;
            case R.id.nav_add:
                AddFragment addFragment = new AddFragment();
                addFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        addFragment).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //Скрыть item
    public void hideItem(String rights){
        if(!rights.equals("admin")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_add).setVisible(false);
            menu.findItem(R.id.nav_change).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
package com.example.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Рассписание");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ScheduleFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_schedule);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ScheduleFragment()).commit();
                break;
            case R.id.nav_session:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SessionFragment()).commit();
                break;
            case R.id.nav_professor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfessorsFragment()).commit();
                break;
            case R.id.nav_change:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChangeFragment()).commit();
                break;
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }


}
package com.example.poryectochat.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.poryectochat.R;
import com.example.poryectochat.fragments.chat;
import com.example.poryectochat.fragments.filter;
import com.example.poryectochat.fragments.homefragment;
import com.example.poryectochat.fragments.profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //instanciamos el botonnavigationy le asignamos el metodo que se realizara cuando se seleccione una opcion
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new homefragment());
    }
    ///metodo para pasar de pantallas
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.item_home:
                            openFragment(new homefragment());
                            return true;
                        case R.id.item_chat:
                            openFragment(new chat());
                            return true;
                        case R.id.item_profile:
                            openFragment(new profile());
                            return true;
                        case R.id.item_filters:
                            openFragment(new filter());
                            return true;
                    }
                    return false;
                }
            };
}
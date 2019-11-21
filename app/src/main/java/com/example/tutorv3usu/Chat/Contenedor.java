package com.example.tutorv3usu.Chat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.tutorv3usu.Adaptadores.TabsPagerAdapter;
import com.example.tutorv3usu.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class Contenedor extends AppCompatActivity {


    private Toolbar mToolbarr;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabsPagerAdapter mTabsPagerAdapter;

    //Firebase
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseReference;
    public FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();
            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
        }
        mViewPager = findViewById(R.id.tabs_pager);
        mTabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabsPagerAdapter);

        mTabLayout = findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);

        Toast.makeText(this, "Hola que tal", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_search2){
            Toast.makeText(this, "hola", Toast.LENGTH_SHORT).show();
            Intent intent =  new Intent(Contenedor.this, Buscar.class);
            startActivity(intent);
        }

        return true;
    }

}

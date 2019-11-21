package com.example.tutorv3usu.VistaAlumno;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.tutorv3usu.Chat.Buscar;
import com.example.tutorv3usu.Fragment.RequestsFragment;
import com.example.tutorv3usu.FragmentAlumno.ChatFragment;
import com.example.tutorv3usu.FragmentAlumno.ReunionesFragment;
import com.example.tutorv3usu.FragmentAlumno.SocialFragment;
import com.example.tutorv3usu.Login;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaTutor.TutorInico;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.tutorv3usu.VistaAlumno.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class InicioAlumno extends AppCompatActivity  implements ChatFragment.OnFragmentInteractionListener, ReunionesFragment.OnFragmentInteractionListener, SocialFragment.OnFragmentInteractionListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseReference;
    public FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_alumno);


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();

            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
        }

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        mToolbar = findViewById(R.id.main_page_toolbar1);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        //checking logging, if not login redirect to Login ACTIVITY
        if (currentUser == null){
            logOutUser(); // Return to Login activity
        }
        if (currentUser != null){
            userDatabaseReference.child("active_now").setValue("true");
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.idsalir){

            AlertDialog.Builder builder = new AlertDialog.Builder(InicioAlumno.this);
            View v = LayoutInflater.from(InicioAlumno.this).inflate(R.layout.salir, null);

            ImageButton imageButton = v.findViewById(R.id.logoutImg);
            imageButton.setImageResource(R.drawable.logout);
            builder.setCancelable(true);

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Dese Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (currentUser != null){
                        userDatabaseReference.child("active_now").setValue(ServerValue.TIMESTAMP);
                    }
                    mAuth.signOut();
                    logOutUser();
                }
            });
            builder.setView(v);
            builder.show();
           // Intent intent =  new Intent(InicioAlumno.this, SearchActivity.class);
           // startActivity(intent);
        }

        if (item.getItemId() == R.id.menu_search1){
            Intent intent =  new Intent(InicioAlumno.this, Buscar.class);
            startActivity(intent);
        }


        return  true;
    }
    private void logOutUser() {
        Intent loginIntent =  new Intent(InicioAlumno.this, Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}
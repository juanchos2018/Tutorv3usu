package com.example.tutorv3usu.VistaAlumno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tutorv3usu.Login;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaTutor.TutorInico;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class Inicioalumon extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    public FirebaseUser currentUser;
    private Button loginButton,cerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicioalumon);
        cerrar=(Button)findViewById(R.id.cerrrarAlumno);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();

            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
        }


        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicioalumon.this);
                View v = LayoutInflater.from(Inicioalumon.this).inflate(R.layout.salir, null);

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
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            logOutUser(); // Return to Login activity
        }
        if (currentUser != null){
            userDatabaseReference.child("active_now").setValue("true");

        }
    }
    private void logOutUser() {
        Intent loginIntent =  new Intent(Inicioalumon.this, Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }
}

package com.example.tutorv3usu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorv3usu.VistaAlumno.InicioAlumno;
import com.example.tutorv3usu.VistaAlumno.Inicioalumon;
import com.example.tutorv3usu.VistaTutor.TutorInico;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import xyz.hasnat.sweettoast.SweetToast;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    public FirebaseUser currentUser;
        private EditText userEmail, userPassword;
    private Button loginButton;
    private ViewPager mViewPager;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios");

//ESto puse
         /*   currentUser = mAuth.getCurrentUser();
            if (currentUser != null){
                String user_uID = mAuth.getCurrentUser().getUid();
                userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
                Toast.makeText(this, "LLega aqui", Toast.LENGTH_SHORT).show();
            }

          */


//hasta qui
        userEmail = findViewById(R.id.inputEmail);
        userPassword = findViewById(R.id.inputPassword);
        loginButton = findViewById(R.id.loginButton);
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                loginUserAccount(email, password);
            }
        });

    }
    private void loginUserAccount(String email, String password) {
        //just validation
        if(TextUtils.isEmpty(email)){
            SweetToast.error(this, "Correo Requerido");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            SweetToast.error(this, "Corro no valido");
        } else if(TextUtils.isEmpty(password)){
            SweetToast.error(this, "Contraseña Requerida");
        } else if (password.length() < 6){
            SweetToast.error(this, "contraseña corta.");
        } else {
            //progress bar
            progressDialog.setMessage("Cargando...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            // after validation checking, log in user a/c
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                // these lines for taking DEVICE TOKEN for sending device to device notification
                                String userUID = mAuth.getCurrentUser().getUid();
                                String userDeiceToken = FirebaseInstanceId.getInstance().getToken();
                                userDatabaseReference.child(userUID).child("device_token").setValue(userDeiceToken)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                checkVerifiedEmail();
                                            }
                                        });

                            } else {
                                SweetToast.error(Login.this, "Tu Correo no esta Verificaco");
                            }

                            progressDialog.dismiss();

                        }
                    });
        }
    }
    String tipo="";
    private void checkVerifiedEmail() {
        user = mAuth.getCurrentUser();
        boolean isVerified = false;
        if (user != null) {
            isVerified = user.isEmailVerified();
        }
        if (isVerified) {
            String UID = mAuth.getCurrentUser().getUid();
            userDatabaseReference.child(UID).child("verified").setValue("true");

           reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(UID);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("tipo").getValue().toString();
                   if (name.equals("Tutor")){
                       Intent intent = new Intent(Login.this, TutorInico.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       userDatabaseReference.child("active_now").setValue("true");
                       startActivity(intent);
                       finish();
                   }
                   if (name.equals("Alumno")){
                       Intent intent = new Intent(Login.this, InicioAlumno.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       userDatabaseReference.child("active_now").setValue("true");
                       startActivity(intent);
                       finish();
                   }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        } else {
            SweetToast.info(Login.this, "Primero verifica tu correo");
            mAuth.signOut();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        //checking logging, if not login redirect to Login ACTIVITY
        if (currentUser == null){
            //logOutUser(); // Return to Login activity
        }
        if (currentUser != null){
            userDatabaseReference.child("active_now").setValue("true");
            String UID = mAuth.getCurrentUser().getUid();
            Toast.makeText(this, "Iniciando sesion", Toast.LENGTH_SHORT).show();

            reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(UID);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("tipo").getValue().toString();
                    if (name.equals("Tutor")){

                        Intent intent1= new Intent(Login.this, TutorInico.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                        //   startActivity(intent);
                       /* Intent intent = new Intent(Login.this, TutorInico.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        userDatabaseReference.child("active_now").setValue("true");
                        startActivity(intent);
                        finish();

                        */
                    }
                    if (name.equals("Alumno")){
                        Intent intent= new Intent(Login.this, InicioAlumno.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                         startActivity(intent);
                   /*     Intent intent = new Intent(Login.this, Inicioalumon.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        userDatabaseReference.child("active_now").setValue("true");
                        startActivity(intent);
                        finish();*/
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //     Intent intent= new Intent(Login.this, TutorInico.class);
         //   startActivity(intent);

        }
    }


        private void logOutUser() {
            Intent loginIntent =  new Intent(Login.this, Login.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
    //ESto puse
    public class ConnectivityReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()){

            } else {
                Snackbar snackbar = Snackbar
                        .make(mViewPager, "No internet ! ", Snackbar.LENGTH_LONG)
                        .setAction("Go settings", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(intent);
                            }
                        });


            }
        }
    }

    //hasta aqui
    }

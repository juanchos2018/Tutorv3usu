package com.example.tutorv3usu.VistaTutor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorv3usu.Chat.Chat;
import com.example.tutorv3usu.Chat.Contenedor;
import com.example.tutorv3usu.Clases.Alu;
import com.example.tutorv3usu.FragmentAlumno.ReunionesFragment;
import com.example.tutorv3usu.Info.Perfil;
import com.example.tutorv3usu.Login;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaAlumno.InicioAlumno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class TutorInico extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    Button btn1,btn2,btn3;
    public FirebaseUser currentUser;
    ImageView imgsalir;
    private Toolbar mToolbar;
    private EditText userEmail, userPassword;
    private Button loginButton,cerrar;
    TextView txttotalalu,txttalcursos,txttotalevento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_inico);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        btn3=(Button)findViewById(R.id.btn3);
        txttotalalu=(TextView)findViewById(R.id.idtxttaoalumnos);
        txttalcursos=(TextView)findViewById(R.id.txttotalcursos);
        txttotalevento=(TextView)findViewById(R.id.idtotalevento);


        mToolbar = findViewById(R.id.main_page_toolbar2);
        setSupportActionBar(mToolbar);

        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();

            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
            reference= FirebaseDatabase.getInstance().getReference("TutorAlumno").child(user_uID);
            reference2= FirebaseDatabase.getInstance().getReference("Tutorcurso").child(user_uID);
            reference3= FirebaseDatabase.getInstance().getReference("Reuniones").child(user_uID);
        }
     //   reference= FirebaseDatabase.getInstance().getReference("Tutorcurso").child(user_uID);

     /*   imgsalir=(ImageView)findViewById(R.id.imsalir);
        imgsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TutorInico.this);
                View v = LayoutInflater.from(TutorInico.this).inflate(R.layout.salir, null);

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


      */

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
        if (item.getItemId() == R.id.idsalir2){

            AlertDialog.Builder builder = new AlertDialog.Builder(TutorInico.this);
            View v = LayoutInflater.from(TutorInico.this).inflate(R.layout.salir, null);

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



        return  true;
    }
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.idcar1:

                //  startActivity(new Intent(TutorInico.this,InicioTutor.class));
                Intent intent= new Intent(TutorInico.this,Agenda.class);
                //Bundle bundle= new Bundle();
               // bundle.putString("code4",code.getText().toString());
             //   intent.putExtras(bundle);
                startActivity(intent);


                break;
            case R.id.idcar2:
               Intent intent1 = new Intent(TutorInico.this, Horario.class);
                startActivity(intent1);

                break;

            case R.id.idcar3:
                Intent intent2 =new Intent(TutorInico.this,MiPerfil.class);
                startActivity(intent2);
                break;
            case R.id.idchat:
                Intent intent4= new Intent(TutorInico.this, Contenedor.class);

                startActivity(intent4);
                break;
        }
    }

    public  void  Ir(View v){
        switch (v.getId()){
            case R.id.btn1:

                Intent intent= new Intent(TutorInico.this, Agenda.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                String fragment="AlumnosFragment";
                Intent intent2= new Intent(TutorInico.this, Agenda.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("btn1",fragment);
                intent2.putExtras(bundle2);
                startActivity(intent2);

                break;
            case R.id.btn3:
                String fragment2="Cursos_Tutores_Fragment";
                Intent intent3= new Intent(TutorInico.this, Agenda.class);
                Bundle bundle4 = new Bundle();
                bundle4.putString("btn2",fragment2);
                intent3.putExtras(bundle4);
                startActivity(intent3);
                break;

        }
    }

// esto puse recien
    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            logOutUser(); // Return to Login activity
        }
        if (currentUser != null){
            userDatabaseReference.child("active_now").setValue("true");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                  //  liscursos.clear();
                    int contador=0;
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Alu track = postSnapshot.getValue(Alu.class);
                        //liscursos.add(track);
                        Log.e("nombres "," "+track);
                        contador++;
                    }
                    txttotalalu.setText(String.valueOf( contador));

                    //   TrackList trackListAdapter = new TrackList(ArtistsActivity.this, tracks);
                    // listViewTracks.setAdapter(trackListAdapter);
                    //  liscursos.add(curso);

                  //  Toast.makeText(TutorInico.this, "tiene "+  String.valueOf(contador), Toast.LENGTH_SHORT).show();
                   // Listas trackListAdapter = new Listas(AgregarCurso.this, liscursos);
                    //lisview.setAdapter(trackListAdapter);
                    // adaperliscursos.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int contador=0;
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Alu track = postSnapshot.getValue(Alu.class);
                        contador++;
                    }
                    txttalcursos.setText(String.valueOf(contador));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int contador=0;
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Alu track = postSnapshot.getValue(Alu.class);
                        contador++;
                    }
                    txttotalevento.setText(String.valueOf(contador));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
    // hasta qui
    private void logOutUser() {
        Intent loginIntent =  new Intent(TutorInico.this, Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

}

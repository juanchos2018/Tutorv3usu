package com.example.tutorv3usu.VistaTutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tutorv3usu.AdapterTutor.AdapterRe;
import com.example.tutorv3usu.ClasesTutor.Cursos;
import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.ClasesTutor.cursotutor;
import com.example.tutorv3usu.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;

public class Horario extends AppCompatActivity {


    MCalendarView calendarView;

    String idgtrupo;
    private RecyclerView recycler1,recycler2;
    private DatabaseReference reference1,reference2;
    private FirebaseAuth mAuth;
    ArrayList<Reuniones> listaPersonaje;
    ArrayList<cursotutor> listaCursos3;
    ArrayList<String> listaCursos2;
    ArrayAdapter<String> adaptercursos2;
    AdapterRe adapter;
    String array1[]=new String[100];
    int contador=0;
    String user_id;
    Spinner spinercurso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);

        recycler1 = findViewById(R.id.idrecylcerreunionescursotutor2);


        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();

       /* idgtrupo="-Lu3hYhLvoeNRJfqzdw9";
        spinercurso=(Spinner)findViewById(R.id.spinsercutsotutor);
        listaCursos3=new ArrayList<>();
        listaCursos2=new ArrayList<String>();
        reference2= FirebaseDatabase.getInstance().getReference("Tutorcurso").child(user_id);

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String curo=postSnapshot.child("curso").getValue().toString();
                    // listaCursos2.add(curo);

                    cursotutor post = postSnapshot.getValue(cursotutor.class);
                    cursotutor btIsdDetails = new cursotutor(post.getId(), post.getCurso());
                    listaCursos3.add(btIsdDetails);
                    array1[contador] = post.getId();
                    contador++;
                    listaCursos2.add(post.getCurso());

                    Log.e("mensje",post.getId() + post.getCurso());



                }
                adaptercursos2= new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_spinner_item,listaCursos2);

                spinercurso.setAdapter(adaptercursos2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        spinercurso.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> spn,
                                               android.view.View v,
                                               int posicion,
                                               long id) {
                        int  pos=spinercurso.getSelectedItemPosition();
                        String ides =array1[pos];
                        Toast.makeText(Horario.this, "id"+ ides, Toast.LENGTH_SHORT).show();
                    }
                    public void onNothingSelected(AdapterView<?> spn) {
                    }
                });




        */
        reference1 = FirebaseDatabase.getInstance().getReference().child("MisReuniones").child(user_id);

        calendarView = (MCalendarView) findViewById(R.id.calendar);

        listaPersonaje = new ArrayList<>();
        recycler1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recycler1.setLayoutManager(linearLayoutManager2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                for (final DataSnapshot data : dataSnapshot.getChildren()) {

                    Reuniones artist = data.getValue(Reuniones.class);
                    String[] fechas= artist.getFecha().split("/");
                 //   Log.e("fehcas otro",artist.getFecha());
                    calendarView.markDate(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));
                    calendarView.setOnDateClickListener(new OnDateClickListener() {

                        @Override
                        public void onDateClick(View view, DateData date) {

                            Integer dia=Integer.parseInt(date.getDayString());
                            String mes=date.getMonthString();
                            Integer ano=date.getYear();

                            String fechareu=dia+"/"+mes+"/"+String.valueOf(ano);
                            Query q=reference1.orderByChild("fecha").equalTo(fechareu);
                            q.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    listaPersonaje.clear();

                                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                                        Reuniones artist = postSnapshot.getValue(Reuniones.class);
                                        listaPersonaje.add(artist);
                                    }

                                    adapter = new AdapterRe(listaPersonaje);
                                    recycler1.setAdapter(adapter);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
            }
        });


    }
}

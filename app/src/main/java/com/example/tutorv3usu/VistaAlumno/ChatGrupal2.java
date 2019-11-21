package com.example.tutorv3usu.VistaAlumno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorv3usu.ChatGrupal;
import com.example.tutorv3usu.Clases.Tu;
import com.example.tutorv3usu.Clases.msn;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaTutor.Archivos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChatGrupal2 extends AppCompatActivity {

    EditText e1;
    TextView t1,t2;
    private String user_name,room_name;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    DatabaseReference reference4;
    String temp_key;
    RecyclerView recycler;

    private Toolbar mToolbar;
    private DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
    ImageView imgbuton;
     String nomnreusuario,tipousuario;
     String idtutor;
     String user_id;
    String iddegrupo,nombregrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_grupal2);

        recycler = (RecyclerView) findViewById(R.id.recylermensaje2);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        e1= (EditText)findViewById(R.id.editText22);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

      //  mToolbar = findViewById(R.id.menuchat2);
       // setSupportActionBar(mToolbar);

        mAuth = FirebaseAuth.getInstance();
         user_id = mAuth.getCurrentUser().getUid();
        iddegrupo=getIntent().getStringExtra("idgrupo2" );
        nombregrupo=getIntent().getStringExtra("namegrupo2" );

        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_id);
        getUserDatabaseReference.keepSynced(true);

        getUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nomnreusuario = dataSnapshot.child("nombre").getValue().toString();
                tipousuario= dataSnapshot.child("tipo").getValue().toString();
                // nomnreusuario=name;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        reference3=FirebaseDatabase.getInstance().getReference().child("AlumnoTutor").child(user_id);
        reference4=  FirebaseDatabase.getInstance().getReference().child("ChatGrupal2").child(iddegrupo);

        setTitle("Chat de " +nombregrupo);
      //  reference3.keepSynced(true);
        //Toast.makeText(ChatGrupal2.this, "i" +user_id, Toast.LENGTH_SHORT).show();

      /*  reference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                // TODO bueno esto no debe ser asi ,ver una manera de mejorarlo

                 // final String  i=dataSnapshot.child("idtutor").getValue().toString();
                   // Toast.makeText(ChatGrupal2.this, "i" +i, Toast.LENGTH_SHORT).show();

                   for(DataSnapshot snapShot : dataSnapshot.getChildren()){
                        Tu polla = snapShot.getValue(Tu.class);
                        //Obtenemos los valores que queres
                       idtutor = polla.getIdtutor();

                  //     idtutor=a1a2_a1;
                     //   Toast.makeText(ChatGrupal2.this, "i" +idtutor, Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       */
      //  Toast.makeText(this, "f"+user_id, Toast.LENGTH_SHORT).show();



    //reference2=FirebaseDatabase.getInstance().getReference().child("ChatGrupal").child(idtutor);
      //  setTitle(" Chat -  Grupal   ");

      //  reference2=  FirebaseDatabase.getInstance().getReference().child("ChatGrupal").child(idtutor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menuchatgrupal2, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.inforgrupo2){

            Intent intent =  new Intent(ChatGrupal2.this, InfoGrupo.class);
            Bundle bundle=new Bundle();
            bundle.putString("name2",nombregrupo);
            bundle.putString("g",iddegrupo);  // llevo el id del grupo
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.idarchivos2){
           // Toast.makeText(this, "subit archivos", Toast.LENGTH_SHORT).show();
            Intent intent =  new Intent(ChatGrupal2.this, Archivos.class);
            Bundle bundle=new Bundle();
            bundle.putString("name2",nombregrupo);
            bundle.putString("g2",iddegrupo);  // llevo el id del grupo
            intent.putExtras(bundle);
            startActivity(intent);
        }

        return true;
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<msn> recyclerOptions = new FirebaseRecyclerOptions.Builder<msn>()
                .setQuery(reference4, msn.class)
                .build();

        FirebaseRecyclerAdapter<msn,ChatsVH> adapter2 = new FirebaseRecyclerAdapter<msn, ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull msn model) {

                final String userID = getRef(position).getKey();

                reference4.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            final String userName = dataSnapshot.child("mensaje").getValue().toString();
                            final String name = dataSnapshot.child("nombre").getValue().toString();
                            holder.user_presence.setText(name);
                            holder.user_name.setText(userName);


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ChatsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat2, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler.setAdapter(adapter2);
        adapter2.startListening();
      //  Toast.makeText(this, "name" + nomnreusuario, Toast.LENGTH_SHORT).show();

//Toast.makeText(this, "su id es " + user_id, Toast.LENGTH_SHORT).show();
/*
       reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    // TODO bueno esto no debe ser asi ,ver una manera de mejorarlo

                    // final String  i=dataSnapshot.child("idtutor").getValue().toString();
                    // Toast.makeText(ChatGrupal2.this, "i" +i, Toast.LENGTH_SHORT).show();

                    for(DataSnapshot snapShot : dataSnapshot.getChildren()){
                        Tu polla = snapShot.getValue(Tu.class);
                        //Obtenemos los valores que queres
                        idtutor = polla.getIdtutor();
                        Toast.makeText(ChatGrupal2.this,"idtu "+ idtutor, Toast.LENGTH_SHORT).show();
                        //     idtutor=a1a2_a1;
                        //   Toast.makeText(ChatGrupal2.this, "i" +idtutor, Toast.LENGTH_SHORT).show();

                    }
                }

                reference2=FirebaseDatabase.getInstance().getReference().child("ChatGrupal").child(idtutor);

                FirebaseRecyclerOptions<msn> recyclerOptions = new FirebaseRecyclerOptions.Builder<msn>()
                        .setQuery(reference2, msn.class)
                        .build();

                FirebaseRecyclerAdapter<msn,ChatsVH> adapter2 = new FirebaseRecyclerAdapter<msn, ChatsVH>(recyclerOptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull msn model) {

                        final String userID = getRef(position).getKey();

                        reference2.child(userID).addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){

                                    final String userName = dataSnapshot.child("mensaje").getValue().toString();
                                    final String name = dataSnapshot.child("nombre").getValue().toString();
                                    holder.user_presence.setText(name);
                                    holder.user_name.setText(userName);

                                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });

                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ChatsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat2, viewGroup, false);
                        return new ChatsVH(view);
                    }
                };

                recycler.setAdapter(adapter2);
                adapter2.startListening();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(this, "su id es " + idtutor, Toast.LENGTH_SHORT).show();

//aqui we


 */
    }


    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView user_name, user_presence;

        public ChatsVH(View itemView) {
            super(itemView);
            user_presence= itemView.findViewById(R.id.nname2);
            user_name = itemView.findViewById(R.id.txtmensaje2);

        }
    }


    public void send1(View view) {

        Map<String,Object> map = new HashMap<String,Object>();
        temp_key = reference4.push().getKey();
        reference4.updateChildren(map);

        DatabaseReference child_ref = reference4.child(temp_key);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("nombre",nomnreusuario);
        map2.put("mensaje", e1.getText().toString());
        child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        e1.setText("");

    }
}

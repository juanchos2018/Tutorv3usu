package com.example.tutorv3usu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorv3usu.Clases.msn;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatGrupal extends AppCompatActivity {
    EditText e1;
    TextView t1,t2;
    private String user_name,room_name;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    String temp_key;
    RecyclerView recycler;


    private DatabaseReference getUserDatabaseReference;
    private FirebaseAuth mAuth;
ImageView imgbuton;

String nomnreusuario,tipousuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_grupal);
        recycler = (RecyclerView) findViewById(R.id.recylermensaje);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        e1= (EditText)findViewById(R.id.editText2);

       // user_name=getIntent().getStringExtra("n" );

        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_id);
        getUserDatabaseReference.keepSynced(true);
        getUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nomnreusuario = dataSnapshot.child("nombre").getValue().toString();
                tipousuario= dataSnapshot.child("tipo").getValue().toString();
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

       // room_name = getIntent().getExtras().get("room_name").toString();

        if (tipousuario=="Alumno") {
            reference3=FirebaseDatabase.getInstance().getReference().child("AlumnoTutor").child(user_id);
            reference3.keepSynced(true);
            reference3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

       // reference = FirebaseDatabase.getInstance().getReference().child("chat1");
        reference2=  FirebaseDatabase.getInstance().getReference().child("ChatGrupal").child(user_id);

        setTitle(" Chat -  Grupal   ");
    /*    reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               append_chat(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                append_chat(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */


    }

    @Override
    public void onStart() {
        super.onStart();

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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler.setAdapter(adapter2);
        adapter2.startListening();

    }
    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView user_name, user_presence;

        public ChatsVH(View itemView) {
            super(itemView);
            user_presence= itemView.findViewById(R.id.nname);
            user_name = itemView.findViewById(R.id.txtmensaje);

        }
    }

    public void send(View v)
    {
        Map<String,Object> map = new HashMap<String,Object>();
        temp_key = reference2.push().getKey();
        reference2.updateChildren(map);

        DatabaseReference child_ref = reference2.child(temp_key);
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
    public void append_chat(DataSnapshot ss)
    {
        String chat_msg,chat_username;
        Iterator i = ss.getChildren().iterator();
        while(i.hasNext())
        {
            chat_msg = ((DataSnapshot)i.next()).getValue().toString();
            chat_username = ((DataSnapshot)i.next()).getValue().toString();

         //   t1.append(chat_username + ": " +chat_msg + " \n");
            t1.setText(chat_username);
        }
    }
}

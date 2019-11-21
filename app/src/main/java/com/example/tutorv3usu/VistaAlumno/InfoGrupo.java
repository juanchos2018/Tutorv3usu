package com.example.tutorv3usu.VistaAlumno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorv3usu.Clases.Participantes;
import com.example.tutorv3usu.Modelo.Amigos;
import com.example.tutorv3usu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoGrupo extends AppCompatActivity {


    private View view;
    private RecyclerView recycler1,recycler2;
    private DatabaseReference reference1;
    private DatabaseReference reference2;
    private DatabaseReference reference3;
    private DatabaseReference reference4;
    private FirebaseAuth mAuth;
TextView nombregrupo,descriocion;
    String current_user_id,idgrupo,namegruopo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_grupo);
        idgrupo=getIntent().getStringExtra("g" );//name2
        namegruopo=getIntent().getStringExtra("name2" );//name2

        nombregrupo=(TextView)findViewById(R.id.idnombregrupo3);
        descriocion=(TextView)findViewById(R.id.idescricion);

        nombregrupo.setText(namegruopo);
        descriocion.setText("Grupo de "+ namegruopo);
        //Toast.makeText(InfoGrupo.this, idgrupo, Toast.LENGTH_SHORT).show();

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("Info Grupo  ");

        recycler1 = findViewById(R.id.idrecyclerparticipantes);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        reference1 = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(current_user_id);
        reference2 = FirebaseDatabase.getInstance().getReference().child("Grupos").child(idgrupo);


        recycler1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recycler1.setLayoutManager(linearLayoutManager2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Participantes> recyclerOptions = new FirebaseRecyclerOptions.Builder<Participantes>()
                .setQuery(reference2, Participantes.class)
                .build();

        FirebaseRecyclerAdapter<Participantes, ChatsVH> adapter = new FirebaseRecyclerAdapter<Participantes, ChatsVH>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull Participantes model) {
                final String userID = getRef(position).getKey();
                reference2.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            final String userName = dataSnapshot.child("cpodigoalumno").getValue().toString();
                            final String code = dataSnapshot.child("nombrealumno").getValue().toString();
                            final String tipo = dataSnapshot.child("tipo").getValue().toString();

                            holder.nombre.setText(userName);
                            holder.codigoalumno.setText(code);
                            holder.tiposusu.setText(tipo);
                            //active status
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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_participantes_grupo, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler1.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView nombre, codigoalumno,tiposusu;
        CircleImageView user_photo;
        ImageView active_icon;
        public ChatsVH(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.idnombrepar);
            codigoalumno=itemView.findViewById(R.id.idcodigoalumno);
            tiposusu=itemView.findViewById(R.id.idtiposusu);
        }
    }
}

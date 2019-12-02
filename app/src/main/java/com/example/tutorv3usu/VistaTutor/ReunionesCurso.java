package com.example.tutorv3usu.VistaTutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReunionesCurso extends AppCompatActivity {

    private RecyclerView recycler1,recycler2;
    private DatabaseReference reference1;
    TextView txt1,txt2,txt3,txt4,txt5;
    String idgtrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuniones_curso);


        idgtrupo=getIntent().getStringExtra("id5" );//name2

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setTitle("Reuniones de Curso");
        recycler1 = findViewById(R.id.idrecylcerreunionescursotutor);

        reference1 = FirebaseDatabase.getInstance().getReference().child("Reuniones").child(idgtrupo);

        recycler1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recycler1.setLayoutManager(linearLayoutManager2);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Reuniones> recyclerOptions = new FirebaseRecyclerOptions.Builder<Reuniones>()
                .setQuery(reference1, Reuniones.class)
                .build();

        FirebaseRecyclerAdapter<Reuniones, ChatsVH> adapter = new FirebaseRecyclerAdapter<Reuniones, ChatsVH>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull Reuniones model) {
                final String userID = getRef(position).getKey();
                reference1.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            final String titulo = dataSnapshot.child("titulo").getValue().toString();
                            final String curso = dataSnapshot.child("curso").getValue().toString();
                            final String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                            final String fecha = dataSnapshot.child("fecha").getValue().toString();
                            final String lugar = dataSnapshot.child("lugar").getValue().toString();

                            holder.titulo.setText(titulo);
                            holder.txtfecha.setText(fecha);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ReunionesCurso.this);

                                    LayoutInflater inflater = ReunionesCurso.this.getLayoutInflater();
                                    View view2 = inflater.inflate(R.layout.dialogo_detalle_reunion, null);
                                    builder.setView(view2)
                                            .setTitle("Detalle")
                                            .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });

                                    builder.show();
                                    txt1=(TextView) view2.findViewById(R.id.iddtitulore);
                                    txt2=(TextView)view2.findViewById(R.id.iddescrire);
                                    txt3=(TextView)view2.findViewById(R.id.idcursore);
                                    txt4=(TextView)view2.findViewById(R.id.idlugarre);

                                    txt1.setText(titulo);
                                    txt2.setText(descripcion);
                                    txt3.setText(curso);
                                    txt4.setText(lugar);
                                }
                            });

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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reuniones_curso, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler1.setAdapter(adapter);
        adapter.startListening();
    }

    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView titulo, codigoalumno,tiposusu,txtfecha;
        //CircleImageView user_photo;
        // ImageView active_icon;
        public ChatsVH(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.idtuloevento4);
            txtfecha=itemView.findViewById(R.id.idfecha4);
            // codigoalumno=itemView.findViewById(R.id.idcodigoalumno);
            //tiposusu=itemView.findViewById(R.id.idtiposusu);
        }
    }
}

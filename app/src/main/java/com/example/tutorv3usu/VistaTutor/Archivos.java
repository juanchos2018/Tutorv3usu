package com.example.tutorv3usu.VistaTutor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorv3usu.Clases.ArchivosGrupo;
import com.example.tutorv3usu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;



public class Archivos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    public FirebaseUser currentUser;
    private RecyclerView recycler;

    String idrupo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos);

        idrupo=getIntent().getStringExtra("g2" );//name2
        recycler=(RecyclerView) findViewById(R.id.recyclerarchivos);
      recycler.setLayoutManager(new LinearLayoutManager(Archivos.this));
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("ArchivoGrupo").child(idrupo);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ArchivosGrupo> recyclerOptions = new FirebaseRecyclerOptions.Builder<ArchivosGrupo>()
                .setQuery(userDatabaseReference, ArchivosGrupo.class)
                .build();
        FirebaseRecyclerAdapter<ArchivosGrupo, ChatsVH> adapter2 = new FirebaseRecyclerAdapter<ArchivosGrupo, ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull ArchivosGrupo model) {

                final String userID = getRef(position).getKey();

                userDatabaseReference.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            final String nombre = dataSnapshot.child("nombre").getValue().toString();
                            final String fecha = dataSnapshot.child("fecha").getValue().toString();
                            final String ruta = dataSnapshot.child("ruta").getValue().toString();
                            holder.nombre.setText(nombre);
                            holder.fecha.setText(fecha);
                            Log.e("arhvios",nombre);
                            holder.ruta=ruta;
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Uri uri = Uri.parse(ruta);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);
                                    // user active status validation

                                    //   Toast.makeText(getContext(), "nm"+ ideevento, Toast.LENGTH_SHORT).show();

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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_archivos, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler.setAdapter(adapter2);
        adapter2.startListening();
    }
    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView nombre, fecha;

        String ruta;
        public ChatsVH(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.idnombrearchivo);
            fecha=itemView.findViewById(R.id.idfechaarichivo);

        }
    }
}

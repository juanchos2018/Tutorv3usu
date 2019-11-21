package com.example.tutorv3usu.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tutorv3usu.Chat.Chat;
import com.example.tutorv3usu.ChatGrupal;
import com.example.tutorv3usu.Modelo.Amigos;
import com.example.tutorv3usu.Modelo.Grupo;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.Util.UserLastSeenTime;
import com.example.tutorv3usu.VistaAlumno.ChatGrupal2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment2 extends Fragment {

    private RecyclerView recycler1,recycler2;
    private DatabaseReference getUserDatabaseReference;
    private DatabaseReference friendsDatabaseReference;
    private DatabaseReference userDatabaseReference;
    private DatabaseReference reference2;
    private DatabaseReference reference1;
    private FirebaseAuth mAuth;
    String current_user_id;
    public ChatsFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_chats_fragment2, container, false);
        recycler1 = vista.findViewById(R.id.idrecclyer3);
        recycler2 = vista.findViewById(R.id.idrecclyer4);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();


        friendsDatabaseReference = FirebaseDatabase.getInstance().getReference().child("friends").child(current_user_id);
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios");

        getUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(current_user_id);

        reference2 = FirebaseDatabase.getInstance().getReference().child("AlumnoGrupo").child(current_user_id);

        recycler2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        recycler2.setLayoutManager(linearLayoutManager2);


        recycler1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recycler1.setLayoutManager(linearLayoutManager);

        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Amigos> recyclerOptions = new FirebaseRecyclerOptions.Builder<Amigos>()
                .setQuery(friendsDatabaseReference, Amigos.class)
                .build();

        FirebaseRecyclerAdapter<Amigos, ChatsFragment2.ChatsVH> adapter = new FirebaseRecyclerAdapter<Amigos, ChatsFragment2.ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsFragment2.ChatsVH holder, int position, @NonNull Amigos model) {
                final String userID = getRef(position).getKey();
                userDatabaseReference.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            final String userName = dataSnapshot.child("nombre").getValue().toString();
                            final String userPresence = dataSnapshot.child("active_now").getValue().toString();
                            final String userThumbPhoto = dataSnapshot.child("user_thumb_image").getValue().toString();

                            if (!userThumbPhoto.equals("default_image")) { // default image condition for new user
                                Picasso.get()
                                        .load(userThumbPhoto)
                                        .networkPolicy(NetworkPolicy.OFFLINE) // for Offline
                                        .placeholder(R.drawable.default_profile_image)
                                        .into(holder.user_photo, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                                Picasso.get()
                                                        .load(userThumbPhoto)
                                                        .placeholder(R.drawable.default_profile_image)
                                                        .into(holder.user_photo);
                                            }
                                        });
                            }
                            holder.user_name.setText(userName);
                            //active status
                            holder.active_icon.setVisibility(View.GONE);
                            if (userPresence.contains("true")) {
                                holder.user_presence.setText("Activo");
                                holder.active_icon.setVisibility(View.VISIBLE);
                            } else {
                                holder.active_icon.setVisibility(View.GONE);
                                UserLastSeenTime lastSeenTime = new UserLastSeenTime();
                                long last_seen = Long.parseLong(userPresence);
                                String lastSeenOnScreenTime = lastSeenTime.getTimeAgo(last_seen, getContext());
                                Log.e("lastSeenTime", lastSeenOnScreenTime);
                                if (lastSeenOnScreenTime != null) {
                                    holder.user_presence.setText(lastSeenOnScreenTime);
                                }
                            }
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // user active status validation
                                    if (dataSnapshot.child("active_now").exists()) {

                                        Intent chatIntent = new Intent(getContext(), Chat.class);
                                        chatIntent.putExtra("visitUserId", userID);
                                        chatIntent.putExtra("userName", userName);
                                        startActivity(chatIntent);

                                    } else {
                                        userDatabaseReference.child(userID).child("active_now")
                                                .setValue(ServerValue.TIMESTAMP).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent chatIntent = new Intent(getContext(), Chat.class);
                                                chatIntent.putExtra("visitUserId", userID);
                                                chatIntent.putExtra("userName", userName);
                                                startActivity(chatIntent);
                                            }
                                        });
                                    }
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
            public ChatsFragment2.ChatsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_single_profile_display2, viewGroup, false);
                return new ChatsFragment2.ChatsVH(view);
            }
        };

        recycler1.setAdapter(adapter);
        adapter.startListening();



        // PARA LISTAR LOS GRUPOS  EN EL QUE ESTA  PARA PROBAR AVER SI SALE PO
        FirebaseRecyclerOptions<Grupo> recyclerOptions2 = new FirebaseRecyclerOptions.Builder<Grupo>()
                .setQuery(reference2, Grupo.class)
                .build();

        FirebaseRecyclerAdapter<Grupo, ChatsFragment2.ChatsVH2> adapter2 = new FirebaseRecyclerAdapter<Grupo, ChatsFragment2.ChatsVH2>(recyclerOptions2) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsFragment2.ChatsVH2 holder, int position, @NonNull Grupo model) {

                final String userID = getRef(position).getKey();

                reference2.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            final String userName = dataSnapshot.child("nombre").getValue().toString();
                            final String curso = dataSnapshot.child("curso").getValue().toString();
                            holder.idgrupo=dataSnapshot.child("idgrupo").getValue().toString();
                            final String idgrup=dataSnapshot.child("idgrupo").getValue().toString();
                            //  Reuniones track = dataSnapshot.getValue(Reuniones.class);
                            //listaReunion.add(track);
                            holder.nombre.setText(userName);
                            holder.curso.setText(curso);


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Toast.makeText(getContext(), userName, Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getContext(), ChatGrupal2.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("idgrupo2",idgrup);
                                    bundle.putString("namegrupo2",curso);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

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
            public ChatsFragment2.ChatsVH2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grupos_alumno, viewGroup, false);
                return new ChatsFragment2.ChatsVH2(view);
            }
        };

        recycler2.setAdapter(adapter2);
        adapter2.startListening();

    }
    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView user_name, user_presence;
        CircleImageView user_photo;
        ImageView active_icon;
        public ChatsVH(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.all_user_name2);
            user_photo = itemView.findViewById(R.id.all_user_profile_img2);
            user_presence = itemView.findViewById(R.id.all_user_status2);
            active_icon = itemView.findViewById(R.id.activeIcon2);
        }
    }

    public static class ChatsVH2 extends RecyclerView.ViewHolder{
        TextView nombre, curso;
        String idgrupo;

        public ChatsVH2(View itemView2) {
            super(itemView2);
            nombre = itemView2.findViewById(R.id.idnombregrupo2);
            curso=itemView2.findViewById(R.id.idcursogrupo2);

        }
    }
}

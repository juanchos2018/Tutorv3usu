package com.example.tutorv3usu.FragmentTutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tutorv3usu.AdapterTutor.AdaptadorReunion;
import com.example.tutorv3usu.Clases.Alu;
import com.example.tutorv3usu.Clases.Usuaros;
import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlumnosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlumnosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlumnosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    DatabaseReference reference;
    DatabaseReference reference2;
    public FirebaseUser currentUser;

    private RecyclerView recycler;
    ArrayList<Reuniones> listaReunion;
    AdaptadorReunion adapterreunion;


    public AlumnosFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlumnosFragment newInstance(String param1, String param2) {
        AlumnosFragment fragment = new AlumnosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
String idtutor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_alumnos, container, false);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recycler=vista.findViewById(R.id.recycleralumnos);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listaReunion= new ArrayList<>();

        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();
            idtutor=user_uID;
            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
            reference2= FirebaseDatabase.getInstance().getReference("TutorAlumno").child(user_uID);;

        }


        return vista;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Alu> recyclerOptions = new FirebaseRecyclerOptions.Builder<Alu>()
                .setQuery(reference2, Alu.class)
                .build();

        FirebaseRecyclerAdapter<Alu,ChatsVH> adapter2 = new FirebaseRecyclerAdapter<Alu, ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull Alu model) {

                final String userID = getRef(position).getKey();

                reference2.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            final String userName = dataSnapshot.child("nombre").getValue().toString();

                            holder.titulo.setText(userName);

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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alumnos_tutor, viewGroup, false);
                return new ChatsVH(view);
            }
        };

        recycler.setAdapter(adapter2);
        adapter2.startListening();

    }

    public static class ChatsVH extends RecyclerView.ViewHolder{
        TextView titulo, user_presence;

        String ideevento;
        public ChatsVH(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.idnombrealu);

        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.tutorv3usu.FragmentTutor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.tutorv3usu.Clases.Alu;
import com.example.tutorv3usu.ClasesTutor.Cursos;
import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaTutor.ReunionesCurso;
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


public class Cursos_Tutores_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    DatabaseReference reference;
    DatabaseReference reference2;
    public FirebaseUser currentUser;

    private RecyclerView recycler;
    private OnFragmentInteractionListener mListener;

    public Cursos_Tutores_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Cursos_Tutores_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Cursos_Tutores_Fragment newInstance(String param1, String param2) {
        Cursos_Tutores_Fragment fragment = new Cursos_Tutores_Fragment();
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
       View vista = inflater.inflate(R.layout.fragment_cursos__tutores_, container, false);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recycler=vista.findViewById(R.id.recyclercursotutor);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
       // listaReunion= new ArrayList<>();

        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();
            idtutor=user_uID;
            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
            reference2= FirebaseDatabase.getInstance().getReference("Tutorcurso").child(user_uID);;

        }

        return  vista;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cursos> recyclerOptions = new FirebaseRecyclerOptions.Builder<Cursos>()
                .setQuery(reference2, Cursos.class)
                .build();

        FirebaseRecyclerAdapter<Cursos,ChatsVH> adapter2 = new FirebaseRecyclerAdapter<Cursos, ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull Cursos model) {

                final String userID = getRef(position).getKey();
                reference2.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){

                            final String userName = dataSnapshot.child("curso").getValue().toString();
                            final String idcurso = dataSnapshot.child("id").getValue().toString();
                            holder.titulo.setText(userName);
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent  =new  Intent(getContext(), ReunionesCurso.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("id5",idcurso);
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
            public ChatsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cursos_tutor, viewGroup, false);
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
            titulo = itemView.findViewById(R.id.idcursotutor);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

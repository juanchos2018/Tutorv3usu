package com.example.tutorv3usu.FragmentAlumno;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.tutorv3usu.Clases.CursosAlumno;
import com.example.tutorv3usu.ClasesAlumno.Tutores;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaAlumno.MisReunionesAlumno;
import com.example.tutorv3usu.VistaTutor.MiPerfil;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReunionesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReunionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReunionesFragment extends Fragment {
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
    DatabaseReference reference3;
    public FirebaseUser currentUser;

    String user_id;

    RecyclerView recycler;
    public ReunionesFragment() {
        // Required empty public constructor
    }

    /**
         * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReunionesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReunionesFragment newInstance(String param1, String param2) {
        ReunionesFragment fragment = new ReunionesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View vista = inflater.inflate(R.layout.fragment_reuniones, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    //    user_id = mAuth.getCurrentUser().getUid(); recyclerreunion

      /*  userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("AlumnoTutor").child(user_id);
        reference2= FirebaseDatabase.getInstance().getReference("AlumnoTutor").child(user_id);;
        reference2.keepSynced(true);
        userDatabaseReference.keepSynced(true); // for offline

       */

        recycler=vista.findViewById(R.id.recyclerreunion);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        if (currentUser != null){
            user_id = mAuth.getCurrentUser().getUid();
           // user_id
      //      reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
            reference2= FirebaseDatabase.getInstance().getReference("AlumnoTutor").child(user_id);
            reference= FirebaseDatabase.getInstance().getReference("AlumnoGrupo").child(user_id);;

        }
        // Toast.makeText(getContext(), user_id, Toast.LENGTH_SHORT).show();
        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<CursosAlumno> recyclerOptions = new FirebaseRecyclerOptions.Builder<CursosAlumno>()
                .setQuery(reference, CursosAlumno.class).build();
        FirebaseRecyclerAdapter<CursosAlumno,Items> adapter =new FirebaseRecyclerAdapter<CursosAlumno, Items>(recyclerOptions) {

            @Override
            protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull CursosAlumno tutores) {
                final String key = getRef(i).getKey();
                reference.child(key).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            final String tituloevento=dataSnapshot.child("curso").getValue().toString();
                            final String idcurso=dataSnapshot.child("idgrupo").getValue().toString();
                            items.txtnombreevento.setText(tituloevento);
                            items.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(), tituloevento, Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(getContext(), MisReunionesAlumno.class);
                                    Bundle bus= new Bundle();
                                    bus.putString("id4",idcurso);
                                    intent.putExtras(bus);
                                    startActivity(intent);


                                }
                            });
                            // name=dataSnapshot.child("nombre").getValue().toString();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reunion2, parent, false);
                return new Items(vista);

            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();

     /*
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // tracks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Tutores track = postSnapshot.getValue(Tutores.class);
                   // Toast.makeText(getContext(), track.getIdtutor(), Toast.LENGTH_SHORT).show();
                    //  tracks.add(track);
                    reference = FirebaseDatabase.getInstance().getReference().child("Reuniones").child(track.getIdtutor());

                    FirebaseRecyclerOptions<Tutores> recyclerOptions = new FirebaseRecyclerOptions.Builder<Tutores>()
                            .setQuery(reference, Tutores.class).build();
                    FirebaseRecyclerAdapter<Tutores,Items> adapter =new FirebaseRecyclerAdapter<Tutores, Items>(recyclerOptions) {

                        @Override
                        protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull Tutores tutores) {
                            final String key = getRef(i).getKey();
                            reference.child(key).addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()){
                                        final String tituloevento=dataSnapshot.child("titulo").getValue().toString();
                                        final String fecha=dataSnapshot.child("fecha").getValue().toString();

                                        items.txtnombreevento.setText(tituloevento);
                                        items.txtfecha.setText(fecha);
                                        // name=dataSnapshot.child("nombre").getValue().toString();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reunion2, parent, false);
                            return new Items(vista);

                        }
                    };
                    recycler.setAdapter(adapter);
                    adapter.startListening();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

      */


    }

    public  static class Items extends RecyclerView.ViewHolder{

        TextView txtnombreevento,txtfecha;
        ImageView imgcam;
        public Items(@NonNull View itemView) {
            super(itemView);
            txtnombreevento=(TextView)itemView.findViewById(R.id.idtuloevento2);
            //imgcam=(ImageView)itemView.findViewById(R.id.idstream);idtufecha2
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

package com.example.tutorv3usu.FragmentAlumno;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.tutorv3usu.ClasesAlumno.Tutores;
import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.VistaAlumno.Stream;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SocialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

        VideoView videoView;
        private WebView mWebview;
        CardView car;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    DatabaseReference reference;
    DatabaseReference reference2;
    public FirebaseUser currentUser;
    RecyclerView recycler;
    private OnFragmentInteractionListener mListener;

    public SocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialFragment newInstance(String param1, String param2) {
        SocialFragment fragment = new SocialFragment();
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


        View vista=inflater.inflate(R.layout.fragment_social, container, false);

     /*   car=(CardView)vista.findViewById(R.id.idstream);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent= new Intent(getContext(),Stream.class);
                    startActivity(intent);
            }
        });

      */
        recycler=vista.findViewById(R.id.recylcermitutor);
        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();

            reference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
            reference2= FirebaseDatabase.getInstance().getReference("AlumnoTutor").child(user_uID);;


            // Toast.makeText(getContext(), "id e " +user_uID , Toast.LENGTH_SHORT).show();
        }


        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Tutores> recyclerOptions = new FirebaseRecyclerOptions.Builder<Tutores>()
                .setQuery(reference2, Tutores.class).build();
        FirebaseRecyclerAdapter<Tutores,Items> adapter =new FirebaseRecyclerAdapter<Tutores, Items>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final Items items, final int i, @NonNull Tutores tutores) {
                final String userID = getRef(i).getKey();
                reference2.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){
                            final String nombretutor=dataSnapshot.child("idtutor").getValue().toString();
                            items.txtnombre.setText(nombretutor);
                            Toast.makeText(getContext(), "lelga qui", Toast.LENGTH_SHORT).show();
                            items.imgcam.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(getContext(), "nom" + nombretutor, Toast.LENGTH_SHORT).show();
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
            public Items onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View vista =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mitutor,parent,false);
                return new Items(vista);

            }
        };
        recycler.setAdapter(adapter);
        adapter.startListening();

    }

    public  static class Items extends RecyclerView.ViewHolder{

        TextView txtnombre;
        ImageView imgcam;
        public Items(@NonNull View itemView) {
            super(itemView);
            txtnombre=(TextView)itemView.findViewById(R.id.idnombretutor);
            imgcam=(ImageView)itemView.findViewById(R.id.idstream);


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

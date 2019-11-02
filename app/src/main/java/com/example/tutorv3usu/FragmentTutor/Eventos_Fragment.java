package com.example.tutorv3usu.FragmentTutor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.tutorv3usu.AdapterTutor.AdaptadorReunion;
import com.example.tutorv3usu.Chat.Chat;
import com.example.tutorv3usu.ClasesTutor.Cursos;
import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.Fragment.ChatsFragment;
import com.example.tutorv3usu.Modelo.Amigos;
import com.example.tutorv3usu.R;
import com.example.tutorv3usu.Util.UserLastSeenTime;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Eventos_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Eventos_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Eventos_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    Button btnmostrardialogo,btneliminartodo;
    EditText ecodigo,etitulo,ecurso,elugar,efecha,edescripcion;

    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "/";

    public String ponerfecha;

    ArrayList<Cursos> listaCursos;
    ArrayAdapter<Cursos> adaptercursos;


    ArrayList<String> listaCursos2;
    ArrayAdapter<String> adaptercursos2;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private DatabaseReference userDatabaseReference;
    DatabaseReference reference;
    DatabaseReference reference2;
    DatabaseReference reference3;
    public FirebaseUser currentUser;

    Spinner spinercursos,spinerciclo1;
    private RecyclerView recycler;
    ArrayList<Reuniones> listaReunion;
    AdaptadorReunion adapterreunion;


    private OnFragmentInteractionListener mListener;

    public Eventos_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Eventos_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Eventos_Fragment newInstance(String param1, String param2) {
        Eventos_Fragment fragment = new Eventos_Fragment();
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
    String  idtutor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_eventos_, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recycler=vista.findViewById(R.id.recyclereventos);
        listaCursos=new ArrayList<>();


        recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listaReunion= new ArrayList<>();
        listaCursos2 = new ArrayList<>();

        if (currentUser != null){
            String user_uID = mAuth.getCurrentUser().getUid();
            idtutor=user_uID;
            userDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child(user_uID);
            reference2= FirebaseDatabase.getInstance().getReference("Reuniones").child(user_uID);;


            // Toast.makeText(getContext(), "id e " +user_uID , Toast.LENGTH_SHORT).show();
        }

        btnmostrardialogo=(Button)vista.findViewById(R.id.idagregaevento);
        btnmostrardialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view2 = inflater.inflate(R.layout.dialogo_agregar_evento, null);
                builder.setView(view2)
                        .setTitle("Registro")
                        .setPositiveButton("REGISTRAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                        Registrar(idtutor,etitulo.getText().toString(),spinercursos.getSelectedItem().toString(),elugar.getText().toString(),efecha.getText().toString(),edescripcion.getText().toString());
                            }
                        });

                builder.show();
                etitulo=(EditText)view2.findViewById(R.id.idtutulo);
               spinercursos=(Spinner)view2.findViewById(R.id.idcurso2);
                elugar=(EditText)view2.findViewById(R.id.idlugar);
                efecha=(EditText)view2.findViewById(R.id.idfecha);
                edescripcion=(EditText)view2.findViewById(R.id.iddescripcion);
                reference2= FirebaseDatabase.getInstance().getReference("Tutorcurso").child(idtutor);
                reference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            Cursos artist = postSnapshot.getValue(Cursos.class);
                            listaCursos.add(artist);

                            String curo=postSnapshot.child("curso").getValue().toString();
                            listaCursos2=new ArrayList<String>();
                            listaCursos2.add(curo);
                            Log.e("curso",""+ curo);
                        }

                        adaptercursos2= new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,listaCursos2);
                        spinercursos.setAdapter(adaptercursos2);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                efecha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        obtenerFecha();
                    }
                });

            }
        });

        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();

        /*reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  listaReunion.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Reuniones track = postSnapshot.getValue(Reuniones.class);
                    listaReunion.add(track);
                    Toast.makeText(getContext(), "listas", Toast.LENGTH_SHORT).show();
                }
                adapterreunion =new AdaptadorReunion(listaReunion);
              //  adapterreunion adatper= new AdaptadorReunion(listaReunion);
                recycler.setAdapter(adapterreunion);
                //   TrackList trackListAdapter = new TrackList(ArtistsActivity.this, tracks);
                // listViewTracks.setAdapter(trackListAdapter);
                //  liscursos.add(curso);

              //  Listas trackListAdapter = new Listas(AgregarCurso.this, liscursos);
                //lisview.setAdapter(trackListAdapter);
                // adaperliscursos.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        */
       FirebaseRecyclerOptions<Reuniones> recyclerOptions = new FirebaseRecyclerOptions.Builder<Reuniones>()
                .setQuery(reference2, Reuniones.class)
                .build();

        FirebaseRecyclerAdapter<Reuniones, ChatsVH> adapter2 = new FirebaseRecyclerAdapter<Reuniones, ChatsVH>(recyclerOptions) {
            @Override
            protected void onBindViewHolder(@NonNull final ChatsVH holder, int position, @NonNull Reuniones model) {

                final String userID = getRef(position).getKey();

                reference2.child(userID).addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            final String ideevento = dataSnapshot.child("id").getValue().toString();
                            final String userName = dataSnapshot.child("titulo").getValue().toString();

                          //  Reuniones track = dataSnapshot.getValue(Reuniones.class);
                            //listaReunion.add(track);
                          holder.titulo.setText(userName);
                          holder.ideevento=ideevento;
                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
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
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reunion, viewGroup, false);
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
            titulo = itemView.findViewById(R.id.idtuloevento);

        }
    }

    public void Registrar(String id ,String titulo,String curso,String lugar,String fecha,String descriocion){


      //  Toast.makeText(getContext(), "es " + id, Toast.LENGTH_SHORT).show();

      //  String current_userID =  mAuth.getCurrentUser().getUid();

      /*  reference = FirebaseDatabase.getInstance().getReference().child("Reuniones").child(id);
        // String id = reference.push().getKey();
        reference.child("idevento").push().getKey();
        reference.child("titulo").setValue(titulo);
        reference.child("lugar").setValue(lugar);
        reference.child("fecha").setValue(fecha);
        reference.child("descripcion").setValue(descriocion);
*/
        reference= FirebaseDatabase.getInstance().getReference("Reuniones").child(id);;
        String ids  = reference.push().getKey();
        Reuniones track = new Reuniones(ids, titulo,curso,lugar,fecha,descriocion);
        reference.child(ids).setValue(track);

        Toast.makeText(getContext(), "Registrado", Toast.LENGTH_SHORT).show();

    }
    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                efecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }

        },anio, mes, dia);

        recogerFecha.show();
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

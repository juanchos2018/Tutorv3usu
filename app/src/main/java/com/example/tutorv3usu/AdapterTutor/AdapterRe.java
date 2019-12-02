package com.example.tutorv3usu.AdapterTutor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorv3usu.ClasesTutor.Reuniones;
import com.example.tutorv3usu.R;

import java.util.ArrayList;

public class AdapterRe extends RecyclerView.Adapter<AdapterRe.ViewHolderDatos>  {


    ArrayList<Reuniones> listaPersonaje;

    public AdapterRe(ArrayList<Reuniones> listaPersonaje) {
        this.listaPersonaje = listaPersonaje;
    }

    @NonNull
    @Override
    public AdapterRe.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reunion_por_fecha,null,false);
        //  vista.setOnClickListener(this);
        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRe.ViewHolderDatos holder, int position) {
        if (holder instanceof ViewHolderDatos){
            //  final Dato dato=
            final ViewHolderDatos datgolder =(ViewHolderDatos)holder;
            datgolder.nombre.setText(listaPersonaje.get(position).getTitulo());
            datgolder.curso.setText(listaPersonaje.get(position).getCurso());



        }
    }

    @Override
    public int getItemCount() {
        return listaPersonaje.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView curso,nombre,ape,telefono,cancursos;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.idtuloevento5);
            curso=(TextView)itemView.findViewById(R.id.idcurso3);
        }
    }
}

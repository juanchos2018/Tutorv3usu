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

public class AdaptadorReunion extends RecyclerView.Adapter<AdaptadorReunion.ViewHolderDatos> {


    ArrayList<Reuniones> listareunion;

    public AdaptadorReunion(ArrayList<Reuniones> listareunion) {
        this.listareunion = listareunion;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reunion,null,false);

        return new ViewHolderDatos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.titulo.setText(listareunion.get(position).getTitulo());

    }

    @Override
    public int getItemCount() {
        return listareunion.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        TextView titulo;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.idtuloevento);
        }
    }
}

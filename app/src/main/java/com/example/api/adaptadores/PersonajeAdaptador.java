package com.example.api.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.R;
import com.example.api.clases.Personaje;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonajeAdaptador extends RecyclerView.Adapter<PersonajeAdaptador.ViewHolder> {

    private List<Personaje> datos;

    public PersonajeAdaptador(List<Personaje> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public PersonajeAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personaje,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeAdaptador.ViewHolder holder, int position) {
        Personaje dato = datos.get(position);
        holder.bind(dato);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_nombre, txt_especie, txt_estado;
        ImageView img_personaje;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_nombre = itemView.findViewById(R.id.txt_nombre);
            txt_especie = itemView.findViewById(R.id.txt_especie);
            txt_estado = itemView.findViewById(R.id.txt_estado);
            img_personaje = itemView.findViewById(R.id.img_personaje);
        }

        public void bind(Personaje dato){
            txt_nombre.setText(dato.getNombre());
            txt_especie.setText(dato.getEspecie());
            txt_estado.setText(dato.getEstado());
            Picasso.get().load(dato.getImagen()).into(img_personaje);
        }
    }
}

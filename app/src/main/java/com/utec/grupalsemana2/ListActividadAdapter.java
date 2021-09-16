package com.utec.grupalsemana2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import logica.ActividadDeCampo;

public class ListActividadAdapter extends RecyclerView.Adapter<ListActividadAdapter.ViewHolder> {
    private List<ActividadDeCampo> actividades;
    private LayoutInflater mInflater;
    private Context contexto;

    public ListActividadAdapter(List<ActividadDeCampo> actividades, Context contexto) {
        this.mInflater = LayoutInflater.from(contexto);
        this.contexto=contexto;
        this.actividades=actividades;
    }

    @Override
    public int getItemCount() {
        return actividades.size();
    }

    @Override
    public ListActividadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = mInflater.inflate(R.layout.list_actividad,null);
        return new ListActividadAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListActividadAdapter.ViewHolder holder, final int position) {
        holder.bindData(actividades.get(position));
    }

    public void setActividades(List<ActividadDeCampo> actividades) {
        this.actividades = actividades;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView usuario, fecha, departamento;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            usuario = itemView.findViewById(R.id.nombreUsuarioTextView);
            fecha = itemView.findViewById(R.id.fechaTextView);
            departamento = itemView.findViewById(R.id.departamentoTextView);
        }

        void bindData(final ActividadDeCampo actividad) {
            this.usuario.setText(actividad.getUsuario());
            this.fecha.setText(actividad.getFecha());
            this.departamento.setText(actividad.getDepartamento());
        }

    }

}

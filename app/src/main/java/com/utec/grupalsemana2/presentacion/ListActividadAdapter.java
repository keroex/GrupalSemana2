package com.utec.grupalsemana2.presentacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.utilidades.FormatoFecha;

public class ListActividadAdapter extends RecyclerView.Adapter<ListActividadAdapter.ViewHolder> {
    private List<ActividadDeCampo> actividades;
    private LayoutInflater mInflater;
    private Context contexto;
    private ActividadClickListener mActividadClickListener;


    public ListActividadAdapter(List<ActividadDeCampo> actividades, Context contexto, ActividadClickListener actividadClickListener) {
        this.mInflater = LayoutInflater.from(contexto);
        this.contexto=contexto;
        this.actividades=actividades;
        this.mActividadClickListener = actividadClickListener;
    }

    @Override
    public int getItemCount() {
        return actividades.size();
    }

    @Override
    public ListActividadAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = mInflater.inflate(R.layout.list_actividad,null);
        return new ListActividadAdapter.ViewHolder(view, mActividadClickListener);
    }

    @Override
    public void onBindViewHolder(final ListActividadAdapter.ViewHolder holder, final int position) {
        holder.bindData(actividades.get(position));
    }

    public void setActividades(List<ActividadDeCampo> actividades) {
        this.actividades = actividades;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        TextView usuario, fecha, departamento;
        ActividadClickListener actividadClickListener;

        public ViewHolder(View itemView, ActividadClickListener actividadClickListener) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            usuario = itemView.findViewById(R.id.nombreUsuarioTextView);
            fecha = itemView.findViewById(R.id.fechaTextView);
            departamento = itemView.findViewById(R.id.departamentoTextView);
            this.actividadClickListener = actividadClickListener;
            itemView.setOnClickListener(this);
        }

        void bindData(final ActividadDeCampo actividad) {
            this.usuario.setText(actividad.getResumen());
            //this.fecha.setText(actividad.getFecha().toString());
            this.fecha.setText(FormatoFecha.DateToString(actividad.getFecha()));
            this.departamento.setText("Departamento: " + actividad.getDepartamento());
        }

        @Override
        public void onClick(View view) {
            actividadClickListener.onActividadClick(getAdapterPosition());
        }
    }

    public interface ActividadClickListener {
        void onActividadClick(int position);
    }

}

package com.utec.grupalsemana2.presentacion;

import android.content.Context;
import android.graphics.Color;
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
import com.utec.grupalsemana2.utilidades.Converters;
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
        TextView usuario, fecha, departamento, equipamiento, ubicacion,region, localidad;
        ActividadClickListener actividadClickListener;

        public ViewHolder(View itemView, ActividadClickListener actividadClickListener) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            usuario = itemView.findViewById(R.id.nombreUsuarioTextView);
            fecha = itemView.findViewById(R.id.fechaTextView);
            departamento = itemView.findViewById(R.id.departamentoTextView);
            region = itemView.findViewById(R.id.regionTextView);
            equipamiento = itemView.findViewById(R.id.equipamientoTextView);
            ubicacion = itemView.findViewById(R.id.ubicacionTextView);
            localidad = itemView.findViewById(R.id.localidadTextView);

            this.actividadClickListener = actividadClickListener;
            itemView.setOnClickListener(this);
        }

        void bindData(final ActividadDeCampo actividad) {
            this.usuario.setText(actividad.getResumen());
            this.fecha.setText(FormatoFecha.DateToString(actividad.getFecha()));
            this.departamento.setText("Departamento: " + actividad.getDepartamento());
            this.localidad.setText("Localidad: " + actividad.getLocalidad());
            this.region.setText("Region: " + actividad.getRegion());
            this.equipamiento.setText("Equipamiento: " + actividad.getEquipamiento());
            this.ubicacion.setText("Ubicacion: " + actividad.getGeopunto());

            if(actividad.getImagen()!=null && actividad.getImagen().length>0) {
                iconImage.setImageBitmap(Converters.convertirByteArrayAImagen(actividad.getImagen()));
            }

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

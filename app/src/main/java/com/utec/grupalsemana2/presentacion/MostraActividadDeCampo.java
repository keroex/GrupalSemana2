package com.utec.grupalsemana2.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.logica.ActividadDeCampo;

public class MostraActividadDeCampo extends AppCompatActivity {

    TextView txtFecha;
    TextView txtResumen;
    TextView txtEquipamiento;
    TextView txtEstacion;
    TextView txtMetodo;
    TextView txtUbicacion;
    TextView txtZona;
    TextView txtRegion;
    TextView txtDepartamento;
    TextView txtLocalidad;
    TextView txtFormulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_actividad_de_campo);

        txtFecha = (TextView) findViewById(R.id.txtFecha);
        txtResumen = (TextView) findViewById(R.id.txtResumen);
        txtEquipamiento = (TextView) findViewById(R.id.txtEquipamiento);
        txtEstacion = (TextView) findViewById(R.id.txtEstacion);
        txtMetodo = (TextView) findViewById(R.id.txtMetodo);
        txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);
        txtZona = (TextView) findViewById(R.id.txtZona);
        txtRegion = (TextView) findViewById(R.id.txtRegion);
        txtDepartamento = (TextView) findViewById(R.id.txtDepartamento);
        txtLocalidad = (TextView) findViewById(R.id.txtLocalidad);
        txtFormulario = (TextView) findViewById(R.id.txtFormulario);

        Intent intent = getIntent();

        if(intent.hasExtra("actividad-seleccionada")) {
            ActividadDeCampo actividadDeCampo = intent.getParcelableExtra("actividad-seleccionada");
            cargarActDeCampo(actividadDeCampo);
        }
    }

    public void cargarActDeCampo(ActividadDeCampo act) {

        txtFecha.setText(act.getFecha());
        txtResumen.setText(act.getResumen());
        txtEquipamiento.setText(act.getEquipamiento());
        txtEstacion.setText(act.getEstacionDeMuestreo());
        txtMetodo.setText(act.getMetodoDeMuestreo());
        txtUbicacion.setText(act.getGeopunto());
        txtZona.setText(act.getZona());
        txtRegion.setText(act.getRegion());
        txtDepartamento.setText(act.getDepartamento());
        txtLocalidad.setText(act.getLocalidad());
        txtFormulario.setText(act.getFormulario());

    }

}
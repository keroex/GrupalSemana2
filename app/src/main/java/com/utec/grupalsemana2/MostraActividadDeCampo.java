package com.utec.grupalsemana2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import logica.ActividadDeCampo;

public class MostraActividadDeCampo extends AppCompatActivity {

    TextView txtFecha = (TextView) findViewById(R.id.txtFecha);
    TextView txtResumen = (TextView) findViewById(R.id.txtResumen);
    TextView txtEquipamiento = (TextView) findViewById(R.id.txtEquipamiento);
    TextView txtEstacion = (TextView) findViewById(R.id.txtEstacion);
    TextView txtMetodo = (TextView) findViewById(R.id.txtMetodo);
    TextView txtUbicacion = (TextView) findViewById(R.id.txtUbicacion);
    TextView txtZona = (TextView) findViewById(R.id.txtZona);
    TextView txtRegion = (TextView) findViewById(R.id.txtRegion);
    TextView txtDepartamento = (TextView) findViewById(R.id.txtDepartamento);
    TextView txtLocalidad = (TextView) findViewById(R.id.txtLocalidad);
    TextView txtFormulario = (TextView) findViewById(R.id.txtFormulario);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostra_actividad_de_campo);

        Intent intent = getIntent();
        ActividadDeCampo act = (ActividadDeCampo) intent.getSerializableExtra("actividad");
        cargarActDeCampo(act);

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
package com.utec.grupalsemana2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

import logica.ActividadDeCampo;

public class AltaActividadDeCampo extends AppCompatActivity {

    EditText txtFecha = (EditText) findViewById(R.id.txtFecha);
    EditText txtResumen = (EditText) findViewById(R.id.txtResumen);
    EditText txtEquipamiento = (EditText) findViewById(R.id.txtEquipamiento);
    EditText txtEstacion = (EditText) findViewById(R.id.txtEstacion);
    EditText txtMetodo = (EditText) findViewById(R.id.txtMetodo);
    EditText txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
    EditText txtZona = (EditText) findViewById(R.id.txtZona);
    EditText txtRegion = (EditText) findViewById(R.id.txtRegion);
    EditText txtDepartamento = (EditText) findViewById(R.id.txtDepartamento);
    EditText txtLocalidad = (EditText) findViewById(R.id.txtLocalidad);
    EditText txtFormulario = (EditText) findViewById(R.id.txtFormulario);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_actividad_de_campo);

    }

    public void CargarActividadDeCampo(View view) {

        ActividadDeCampo act = new ActividadDeCampo();

        act.setFecha(this.txtFecha.getText().toString());
        act.setResumen(this.txtResumen.getText().toString());
        act.setEquipamiento(this.txtEquipamiento.getText().toString());
        act.setEstacionDeMuestreo(this.txtEstacion.getText().toString());
        act.setMetodoDeMuestreo(this.txtMetodo.getText().toString());
        act.setGeopunto(this.txtUbicacion.getText().toString());
        act.setZona(this.txtZona.getText().toString());
        act.setRegion(this.txtRegion.getText().toString());
        act.setDepartamento(this.txtDepartamento.getText().toString());
        act.setLocalidad(this.txtLocalidad.getText().toString());
        act.setFormulario(this.txtFormulario.getText().toString());

        if(validar(act)) {
            Intent intent = new Intent(this, MostraActividadDeCampo.class);
            intent.putExtra("actividad", (Serializable) act);
            startActivity(intent);
        }

    }


    public boolean validar(ActividadDeCampo actividadDeCampo) {

        boolean retorno = true;

        if (actividadDeCampo.getFecha().isEmpty()) {
                this.txtFecha.setError("No puede quedar vacío");
                retorno = false;
        }   else {
            this.txtFecha.setError(null);
        }

        if (actividadDeCampo.getGeopunto().isEmpty()) {
            this.txtUbicacion.setError("No puede quedar vacío");
            retorno = false;

        }   else {
            this.txtUbicacion.setError(null);
        }

        if (actividadDeCampo.getFormulario().isEmpty()) {
            this.txtFormulario.setError("No puede quedar vacío");
            retorno = false;

        }   else {
            this.txtFormulario.setError(null);
        }

        return retorno;

    }

}
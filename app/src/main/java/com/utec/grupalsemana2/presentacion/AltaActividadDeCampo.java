package com.utec.grupalsemana2.presentacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.servicios.ServicioMostrarLog;

import java.util.Timer;
import java.util.TimerTask;

public class AltaActividadDeCampo extends AppCompatActivity {

    EditText txtFecha;
    EditText txtResumen;
    EditText txtEquipamiento;
    EditText txtEstacion;
    EditText txtMetodo;
    EditText txtUbicacion;
    EditText txtZona;
    EditText txtRegion;
    EditText txtDepartamento;
    EditText txtLocalidad;
    EditText txtFormulario;

    ActividadDeCampoViewModel actividadDeCampoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_actividad_de_campo);
        txtFecha = (EditText) findViewById(R.id.txtFecha);
        txtResumen = (EditText) findViewById(R.id.txtResumen);
        txtEquipamiento = (EditText) findViewById(R.id.txtEquipamiento);
        txtEstacion = (EditText) findViewById(R.id.txtEstacion);
        txtMetodo = (EditText) findViewById(R.id.txtMetodo);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtZona = (EditText) findViewById(R.id.txtZona);
        txtRegion = (EditText) findViewById(R.id.txtRegion);
        txtDepartamento = (EditText) findViewById(R.id.txtDepartamento);
        txtLocalidad = (EditText) findViewById(R.id.txtLocalidad);
        txtFormulario = (EditText) findViewById(R.id.txtFormulario);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startService(new Intent(getBaseContext(), ServicioMostrarLog.class));
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
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

        if(validarCampos(act)) {
            //Intent intent = new Intent(this, MostraActividadDeCampo.class);
            //intent.putExtra("actividad", act);
            actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
            actividadDeCampoViewModel.insert(act);



            Toast.makeText(getApplicationContext(),"Actividad de campo agregada", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"Cantidad de registros: " + actividadDeCampoViewModel.count(), Toast.LENGTH_SHORT).show();
            //startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(),"Complete los datos Obligatorios",Toast.LENGTH_LONG).show();
        }

    }

    public void VerActividades(View view) {
        Intent intentActividades = new Intent(this, ListarActividadesDeCampo.class);
        startActivity(intentActividades);
    }


    public boolean validarCampos(ActividadDeCampo actividadDeCampo) {

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
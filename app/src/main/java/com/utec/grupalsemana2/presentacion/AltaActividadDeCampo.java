package com.utec.grupalsemana2.presentacion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.servicios.ServicioMostrarLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AltaActividadDeCampo extends AppCompatActivity {

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText txtResumen;
    private EditText txtEquipamiento;
    private EditText txtEstacion;
    private EditText txtMetodo;
    private EditText txtUbicacion;
    private EditText txtZona;
    private EditText txtRegion;
    private EditText txtDepartamento;
    private EditText txtLocalidad;
    private EditText txtFormulario;

    ActividadDeCampoViewModel actividadDeCampoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_actividad_de_campo);
        mDisplayDate = (TextView) findViewById(R.id.txtViewFecha);
        //DATEPICKER
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AltaActividadDeCampo.this,mDateSetListener,anio,mes,dia);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                mes = mes +1;
                String dateMostrar = dia + "/" + mes + "/" + anio;
                mDisplayDate.setText(dateMostrar);
            }
        };
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
        /*Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startService(new Intent(getBaseContext(), ServicioMostrarLog.class));
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 5000);*/
    }

    public void CargarActividadDeCampo(View view) {

        ActividadDeCampo act = new ActividadDeCampo();

        act.setFecha(StrToDate(mDisplayDate.getText().toString()));
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
        act.setIdformulario(1);
        act.setIddepartamento(3);
        act.setIdlocalidad(581);
        act.setIdusuario(1);
        act.setUsuario("admin");
        act.setTipoDeMuestreo("hola");

        if(validarCampos(act)) {
            actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
            actividadDeCampoViewModel.insert(act);
            Toast.makeText(getApplicationContext(),"Actividad de campo agregada", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"Cantidad de registros: " + actividadDeCampoViewModel.count(), Toast.LENGTH_SHORT).show();
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

        if (actividadDeCampo.getFecha()==null) {
            this.mDisplayDate.setError("No puede quedar vacío");
            retorno = false;
        }   else {
            this.mDisplayDate.setError(null);

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

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(str);
            System.out.println(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
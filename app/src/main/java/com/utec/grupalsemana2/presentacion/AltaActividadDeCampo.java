package com.utec.grupalsemana2.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Region;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.interfaces.DepartamentoApi;
import com.utec.grupalsemana2.interfaces.FormularioApi;
import com.utec.grupalsemana2.interfaces.LocalidadApi;
import com.utec.grupalsemana2.interfaces.RegionApi;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.logica.FormularioDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.RegionDTO;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.sesion.Sesion;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaActividadDeCampo extends AppCompatActivity {


    private DepartamentoApi departamentoApi = RestAppClient.getClient().create(DepartamentoApi.class);
    private LocalidadApi localidadApi = RestAppClient.getClient().create(LocalidadApi.class);
    private RegionApi regionApi = RestAppClient.getClient().create(RegionApi.class);
    private FormularioApi formularioApi = RestAppClient.getClient().create(FormularioApi.class);
    private MutableLiveData<List<FormularioDTO>> formularios = new MutableLiveData<>();
    private MutableLiveData<List<RegionDTO>> regiones = new MutableLiveData<>();
    private MutableLiveData<List<DepartamentoDTO>> departamentos = new MutableLiveData<>();
    private MutableLiveData<List<LocalidadDTO>> localidades = new MutableLiveData<>();


    private TextView mDisplayDate;
    private TextView mDisplayTime;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private EditText txtResumen;
    private EditText txtEquipamiento;
    private EditText txtEstacion;
    private EditText txtMetodo;
    private EditText txtUbicacion;
    private EditText txtZona;
    private EditText txtTipoDeMuestreo;
    private Spinner spRegion;
    private Spinner spDepartamento;
    private Spinner spLocalidad;
    private Spinner spFormulario;

    ActividadDeCampoViewModel actividadDeCampoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_actividad_de_campo);
        mDisplayDate = (TextView) findViewById(R.id.txtViewFecha);
        mDisplayTime = (TextView) findViewById(R.id.textViewHora);
        //DATEPICKER EVENTO
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

        //HOURPICKER EVENTO
        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hora = cal.get(Calendar.HOUR);
                int minuto = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        AltaActividadDeCampo.this,mTimeSetListener,hora,minuto, DateFormat.is24HourFormat(getApplicationContext()));
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

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                //mes = mes +1;
                String timeMostrar = hora + ":" + minuto;
                mDisplayTime.setText(timeMostrar);
            }
        };


        txtResumen = (EditText) findViewById(R.id.txtResumen);
        txtEquipamiento = (EditText) findViewById(R.id.txtEquipamiento);
        txtEstacion = (EditText) findViewById(R.id.txtEstacion);
        txtMetodo = (EditText) findViewById(R.id.txtMetodo);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtZona = (EditText) findViewById(R.id.txtZona);
        txtTipoDeMuestreo = (EditText) findViewById(R.id.txtTipoDeMuestreo);
        spRegion = (Spinner) findViewById(R.id.spRegion);
        spDepartamento = (Spinner) findViewById(R.id.spDepartamento);
        spLocalidad= (Spinner) findViewById(R.id.spLocalidad);
        spFormulario= (Spinner) findViewById(R.id.spFormulario);
        getFormularios();
        getRegiones();
        deshabilitarSpinners();
        //SPINNER REGION EVENTO
        spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spRegion.getSelectedItemPosition()>0) {
                    RegionDTO regionDTO = (RegionDTO) ( (Spinner) findViewById(R.id.spRegion) ).getSelectedItem();
                    spDepartamento.setEnabled(true);
                    getDepartamentos(regionDTO);
                } else {
                    deshabilitarSpinners();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        //SPINNER DEPARTAMENTO EVENTO
        spDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spDepartamento.getSelectedItemPosition()>0) {
                    DepartamentoDTO departamentoDTO = (DepartamentoDTO) ( (Spinner) findViewById(R.id.spDepartamento) ).getSelectedItem();
                    spLocalidad.setEnabled(true);
                    getLocalidades(departamentoDTO);
                } else {
                    deshabilitarSpinners();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
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

        DepartamentoDTO departamentoDTO = (DepartamentoDTO) ( (Spinner) findViewById(R.id.spDepartamento) ).getSelectedItem();
        RegionDTO regionDTO = (RegionDTO) ( (Spinner) findViewById(R.id.spRegion) ).getSelectedItem();
        FormularioDTO formularioDTO = (FormularioDTO) ( (Spinner) findViewById(R.id.spFormulario) ).getSelectedItem();
        LocalidadDTO localidadDTO = (LocalidadDTO) ( (Spinner) findViewById(R.id.spLocalidad) ).getSelectedItem();


        act.setFecha(StrToDate(mDisplayDate.getText().toString(),mDisplayTime.getText().toString()));
        act.setResumen(this.txtResumen.getText().toString());
        act.setEquipamiento(this.txtEquipamiento.getText().toString());
        act.setEstacionDeMuestreo(this.txtEstacion.getText().toString());
        act.setMetodoDeMuestreo(this.txtMetodo.getText().toString());
        act.setGeopunto(this.txtUbicacion.getText().toString());
        act.setZona(this.txtZona.getText().toString());
        act.setRegion(regionDTO.getNombre());
        act.setDepartamento(departamentoDTO.getNombre());
        act.setLocalidad(localidadDTO.getNombre());
        act.setFormulario(formularioDTO.getNombre());
        act.setIdformulario(formularioDTO.getIdformulario());
        act.setIddepartamento(departamentoDTO.getIddepartamento());
        act.setIdlocalidad(localidadDTO.getIdlocalidad());
        act.setIdusuario(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario());
        act.setUsuario(Sesion.getInstancia().getUsuarioLogueado().getNombreUsuario());
        act.setTipoDeMuestreo(this.txtTipoDeMuestreo.getText().toString());


        try {
            if(validarCampos(act)) {
                actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
                actividadDeCampoViewModel.insert(act, this);
                //Cambiar por insertDao y probar

            }
            else {
                Toast.makeText(getApplicationContext(),"Complete los datos obligatorios",Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean validarCampos(ActividadDeCampo actividadDeCampo) {

        boolean retorno = true;

        if (actividadDeCampo.getFecha()==null) {
            this.mDisplayDate.setError("No puede quedar vacío");
            this.mDisplayTime.setError("No puede quedar vacío");
            retorno = false;
        }   else {
            this.mDisplayDate.setError(null);
            this.mDisplayTime.setError(null);
        }

        if (actividadDeCampo.getGeopunto().isEmpty()) {
            this.txtUbicacion.setError("No puede quedar vacío");
            retorno = false;

        }   else {
            this.txtUbicacion.setError(null);
        }

        if (spFormulario.getSelectedItemPosition()<1) {
            Toast.makeText(getApplicationContext(),"Debe seleccionar un formulario",Toast.LENGTH_LONG).show();
            retorno = false;
        }
        return retorno;
    }

    public static Date StrToDate(String fecha, String hora) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechastr = fecha + " " + hora + ":00";
        System.out.println("FechaSTR= " + fechastr);
        Date date = null;
        try {
            date = format.parse(fechastr);
            System.out.println(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void getFormularios() {
        try {
            formularios.setValue(new ArrayList<>());
            Call<List<FormularioDTO>> call = formularioApi.getFormularios();
            call.enqueue(new Callback<List<FormularioDTO>>() {
                @Override
                public void onResponse(Call<List<FormularioDTO>> call, Response<List<FormularioDTO>> response) {
                    if(response.isSuccessful()) {
                        List<FormularioDTO> misFormularios = response.body();
                        if(misFormularios!=null) {
                            formularios.setValue(misFormularios);
                        }
                        System.out.println("*********************************************");
                        for (FormularioDTO formularioDTO: formularios.getValue()) {
                            System.out.println(formularioDTO.toString());
                        }
                        cargarSpinnerFormularios(formularios);
                    } else {
                        System.out.println("RESPONSE NOT SUCCESSFUL" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<FormularioDTO>> call, Throwable t) {
                    System.out.println("NO anda");
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void getRegiones() {
        regiones.setValue(new ArrayList<>());
        Call<List<RegionDTO>> call = regionApi.getRegiones();
        call.enqueue(new Callback<List<RegionDTO>>() {
            @Override
            public void onResponse(Call<List<RegionDTO>> call, Response<List<RegionDTO>> response) {
                if(response.isSuccessful()) {
                    List<RegionDTO> misRegiones = response.body();
                    if(misRegiones!=null) {
                        regiones.setValue(misRegiones);
                    }
                    cargarSpinnerRegiones(regiones);
                }
            }
            @Override
            public void onFailure(Call<List<RegionDTO>> call, Throwable t) {

            }
        });
    }

    private void getDepartamentos(RegionDTO r) {
        departamentos.setValue(new ArrayList<>());
        Call<List<DepartamentoDTO>> call = departamentoApi.getDepartamentos(r);
        call.enqueue(new Callback<List<DepartamentoDTO>>() {
            @Override
            public void onResponse(Call<List<DepartamentoDTO>> call, Response<List<DepartamentoDTO>> response) {
                if(response.isSuccessful()) {
                    List<DepartamentoDTO> misDepartamentos = response.body();
                    if(misDepartamentos!=null) {
                        departamentos.setValue(misDepartamentos);
                    }
                    cargarSpinnerDepartamentos(departamentos);
                }
            }
            @Override
            public void onFailure(Call<List<DepartamentoDTO>> call, Throwable t) {

            }
        });
    }

    private void getLocalidades(DepartamentoDTO d) {
        localidades.setValue(new ArrayList<>());
        Call<List<LocalidadDTO>> call = localidadApi.getLocalidades(d);
        call.enqueue(new Callback<List<LocalidadDTO>>() {
            @Override
            public void onResponse(Call<List<LocalidadDTO>> call, Response<List<LocalidadDTO>> response) {
                if(response.isSuccessful()) {
                    List<LocalidadDTO> misLocalidades = response.body();
                    if(misLocalidades!=null) {
                        localidades.setValue(misLocalidades);
                    }
                    cargarSpinnerLocalidades(localidades);
                }
            }
            @Override
            public void onFailure(Call<List<LocalidadDTO>> call, Throwable t) {

            }
        });
    }

    private void cargarSpinnerFormularios(MutableLiveData<List<FormularioDTO>> formularios) {
        FormularioDTO fDefault = new FormularioDTO();
        fDefault.setNombre("Seleccione formulario...  *");
        fDefault.setIdformulario(0);
        formularios.getValue().add(0,fDefault);
        ArrayAdapter<FormularioDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, formularios.getValue());
        spFormulario.setAdapter(adaptador);
    }

    private void cargarSpinnerRegiones(MutableLiveData<List<RegionDTO>> regiones) {
        RegionDTO rDefault = new RegionDTO();
        rDefault.setNombre("Seleccione region...");
        rDefault.setIdregion(0);
        regiones.getValue().add(0,rDefault);
        ArrayAdapter<RegionDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, regiones.getValue());
        spRegion.setAdapter(adaptador);
    }

    private void cargarSpinnerDepartamentos(MutableLiveData<List<DepartamentoDTO>> departamentos) {
        DepartamentoDTO dDefault = new DepartamentoDTO();
        dDefault.setNombre("Seleccione departamento...");
        dDefault.setIddepartamento(0);
        departamentos.getValue().add(0,dDefault);
        ArrayAdapter<DepartamentoDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, departamentos.getValue());
        spDepartamento.setAdapter(adaptador);
    }

    private void cargarSpinnerLocalidades(MutableLiveData<List<LocalidadDTO>> localidades) {
        LocalidadDTO lDefault = new LocalidadDTO();
        lDefault.setNombre("Seleccione localidad...");
        lDefault.setIdlocalidad(0);
        localidades.getValue().add(0,lDefault);
        ArrayAdapter<LocalidadDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, localidades.getValue());
        spLocalidad.setAdapter(adaptador);
    }

    private void deshabilitarSpinners() {
        if(spRegion.getSelectedItemPosition()<1) {
            spDepartamento.setEnabled(false);
            spLocalidad.setEnabled(false);
            spDepartamento.setAdapter(null);
            spLocalidad.setAdapter(null);
        }
        if(spDepartamento.getSelectedItemPosition()<1) {
            spLocalidad.setEnabled(false);
            spLocalidad.setAdapter(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea cerrar sesión?");
            builder.setCancelable(true);
            builder.setNegativeButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Sesion.getInstancia().setUsuarioLogueado(new UsuarioDTO());
                    finish();
                    Intent intentLogin = new Intent(getApplicationContext(),login.class);
                    startActivity(intentLogin);
                }
            });
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return true;
    }

}
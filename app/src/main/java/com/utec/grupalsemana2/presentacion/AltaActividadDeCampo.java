package com.utec.grupalsemana2.presentacion;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.utec.grupalsemana2.R;
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
import com.utec.grupalsemana2.models.DepartamentoViewModel;
import com.utec.grupalsemana2.models.FormularioViewModel;
import com.utec.grupalsemana2.models.LocalidadViewModel;
import com.utec.grupalsemana2.models.RegionViewModel;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.sesion.Sesion;
import com.utec.grupalsemana2.utilidades.Converters;
import com.utec.grupalsemana2.utilidades.FormatoFecha;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AltaActividadDeCampo extends AppCompatActivity {


    private DepartamentoApi departamentoApi = RestAppClient.getClient().create(DepartamentoApi.class);
    private LocalidadApi localidadApi = RestAppClient.getClient().create(LocalidadApi.class);
    private RegionApi regionApi = RestAppClient.getClient().create(RegionApi.class);
    private FormularioApi formularioApi = RestAppClient.getClient().create(FormularioApi.class);
    private List<FormularioDTO> formularios = new ArrayList<>();
    private List<RegionDTO> regiones = new ArrayList<>();
    private List<DepartamentoDTO> departamentos = new ArrayList<>();
    private List<LocalidadDTO> localidades = new ArrayList<>();
    RegionViewModel regionViewModel;
    DepartamentoViewModel departamentoViewModel;
    LocalidadViewModel localidadViewModel;
    FormularioViewModel formularioViewModel;
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imagenCargada;

    public static final long PERIODO = 1000; // 1 segundos (1 * 1000 millisegundos)
    private Handler handler;
    private Runnable runnable;
    private ActionMenuItemView conexion;
    private FusedLocationProviderClient fusedLocationClient;
    private Location ubicacion;
    private String longitud;
    private String latitud;
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
    private TextView txtCargarImagen;

    private ActividadDeCampoViewModel actividadDeCampoViewModel;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private Dialog imgdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_actividad_de_campo);
        mDisplayDate = (TextView) findViewById(R.id.txtViewFecha);
        mDisplayTime = (TextView) findViewById(R.id.textViewHora);
        txtCargarImagen = findViewById(R.id.txtCargarImagen);
        txtResumen = (EditText) findViewById(R.id.txtResumen);
        txtEquipamiento = (EditText) findViewById(R.id.txtEquipamiento);
        txtEstacion = (EditText) findViewById(R.id.txtEstacion);
        txtMetodo = (EditText) findViewById(R.id.txtMetodo);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacion);
        txtZona = (EditText) findViewById(R.id.txtZona);
        txtTipoDeMuestreo = (EditText) findViewById(R.id.txtTipoDeMuestreo);
        spDepartamento = (Spinner) findViewById(R.id.spDepartamento);
        spLocalidad = (Spinner) findViewById(R.id.spLocalidad);
        imgdialog = new Dialog(this);


        //DATEPICKER EVENTO
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AltaActividadDeCampo.this, mDateSetListener, anio, mes, dia);
                dialog.show();
            }
        });

        if (spFormulario==null && spRegion== null) {
            spRegion = (Spinner) findViewById(R.id.spRegion);
            spFormulario = (Spinner) findViewById(R.id.spFormulario);
        }
        if (spFormulario.getAdapter() == null) {
            getFormularios();
        }


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaComoCadena = sdf.format(new Date());
        mDisplayDate.setText(fechaComoCadena);

        //HOURPICKER EVENTO
        mDisplayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hora = cal.get(Calendar.HOUR);
                int minuto = cal.get(Calendar.MINUTE);
                TimePickerDialog dialog = new TimePickerDialog(
                        AltaActividadDeCampo.this, mTimeSetListener, hora, minuto, DateFormat.is24HourFormat(getApplicationContext()));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
                mes = mes + 1;
                String dateMostrar = dia + "/" + mes + "/" + anio;
                mDisplayDate.setText(dateMostrar);
            }
        };

        SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm");
        String horaComoCadena = sdfh.format(new Date());
        mDisplayTime.setText(horaComoCadena);

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minuto) {

                String horaString = "";
                String minutoString = "";
                if (hora < 10) {
                    horaString = "0" + hora;
                } else {
                    horaString = String.valueOf(hora);
                }
                if (minuto < 10) {
                    minutoString = "0" + minuto;
                } else {
                    minutoString = String.valueOf(minuto);
                }

                String timeMostrar = horaString + ":" + minutoString;
                mDisplayTime.setText(timeMostrar);
            }
        };
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation();
            }
        }


        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    ubicacion = location;
                }
            }
        });


        fusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                latitud = "00 ";
                longitud = "00 ";
            }
        });

        if (spRegion.getAdapter() == null) {
            //SPINNER REGION EVENTO
            spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if (spRegion.getSelectedItemPosition() > 0) {
                        RegionDTO regionDTO = (RegionDTO) ((Spinner) findViewById(R.id.spRegion)).getSelectedItem();
                        spDepartamento.setEnabled(true);
                        getDepartamentos(regionDTO);
                    } else {
                        deshabilitarSpinners();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }

            });
            getRegiones();
            deshabilitarSpinners();

        }

        spDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spDepartamento.getSelectedItemPosition() > 0) {
                    DepartamentoDTO departamentoDTO = (DepartamentoDTO) ((Spinner) findViewById(R.id.spDepartamento)).getSelectedItem();
                    spLocalidad.setEnabled(true);
                    getLocalidades(departamentoDTO);
                } else {
                    deshabilitarSpinners();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Bundle extras = data.getExtras();
                            Bitmap imagen = (Bitmap) extras.get("data");
                            if(imagen!=null) {
                                //poner la img en el imgview (setimagebitmap)
                                imagenCargada = imagen;
                                txtCargarImagen.setText("Ver imagen cargada");
                            }
                        } else {
                            txtCargarImagen.setText("Cargar imagen...");
                        }
                    }
                });

        txtCargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenCargada != null) {
                    mostrarImagen();
                }
            }
        });

    }

    public void CargarActividadDeCampo(View view) {
        try {
            ActividadDeCampo act = new ActividadDeCampo();

            DepartamentoDTO departamentoDTO = (DepartamentoDTO) ((Spinner) findViewById(R.id.spDepartamento)).getSelectedItem();
            RegionDTO regionDTO = (RegionDTO) ((Spinner) findViewById(R.id.spRegion)).getSelectedItem();
            FormularioDTO formularioDTO = (FormularioDTO) ((Spinner) findViewById(R.id.spFormulario)).getSelectedItem();
            LocalidadDTO localidadDTO = (LocalidadDTO) ((Spinner) findViewById(R.id.spLocalidad)).getSelectedItem();


            act.setFecha(FormatoFecha.StrToDate(mDisplayDate.getText().toString(), mDisplayTime.getText().toString()));
            act.setResumen(this.txtResumen.getText().toString());
            act.setEquipamiento(this.txtEquipamiento.getText().toString());
            act.setEstacionDeMuestreo(this.txtEstacion.getText().toString());
            act.setMetodoDeMuestreo(this.txtMetodo.getText().toString());
            act.setGeopunto(this.txtUbicacion.getText().toString());
            act.setZona(this.txtZona.getText().toString());
            act.setRegion(regionDTO.getNombre());
            if (departamentoDTO != null) {
                act.setDepartamento(departamentoDTO.getNombre());
                act.setIddepartamento(departamentoDTO.getIddepartamento());
            }
            if (localidadDTO != null) {
                act.setLocalidad(localidadDTO.getNombre());
                act.setIdlocalidad(localidadDTO.getIdlocalidad());
            }
            act.setFormulario(formularioDTO.getNombre());
            act.setIdformulario(formularioDTO.getIdformulario());
            act.setIdusuario(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario());
            act.setUsuario(Sesion.getInstancia().getUsuarioLogueado().getNombreUsuario());
            act.setTipoDeMuestreo(this.txtTipoDeMuestreo.getText().toString());
            if(imagenCargada != null) {
                act.setImagen(Converters.convertirImagenAByteArray(imagenCargada));
            }


            if (validarCampos(act)) {
                actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
                actividadDeCampoViewModel.insertDao(act, this);

            } else {
                Toast.makeText(getApplicationContext(), "Complete los datos obligatorios", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {

        }
    }

    public boolean validarCampos(ActividadDeCampo actividadDeCampo) {

        boolean retorno = true;

        if (actividadDeCampo.getFecha() == null) {
            this.mDisplayDate.setError("No puede quedar vacío");
            this.mDisplayTime.setError("No puede quedar vacío");
            retorno = false;
        } else {
            this.mDisplayDate.setError(null);
            this.mDisplayTime.setError(null);
        }

        if (actividadDeCampo.getGeopunto().isEmpty()) {
            txtUbicacion.setError("No puede quedar vacío");
            retorno = false;

        } else {
            txtUbicacion.setError(null);
        }

        if(actividadDeCampo.getEquipamiento().isEmpty()) {
            txtEquipamiento.setError("No puede quedar vacío");
            retorno = false;
        } else {
            txtEquipamiento.setError(null);
        }

        if(actividadDeCampo.getTipoDeMuestreo().isEmpty()) {
            txtTipoDeMuestreo.setError("No puede quedar vacío");
            retorno = false;
        } else {
            txtTipoDeMuestreo.setError(null);
        }

        if(actividadDeCampo.getMetodoDeMuestreo().isEmpty()) {
            txtMetodo.setError("No puede quedar vacío");
            retorno = false;
        } else {
            txtMetodo.setError(null);
        }

        if(actividadDeCampo.getGeopunto().length()>50) {
            txtUbicacion.setError("La ubicación no puede tener más de 50 caracteres");
            retorno = false;
        } else {
            txtUbicacion.setError(null);
        }

        if(actividadDeCampo.getZona().length()>50) {
            txtZona.setError("La zona no puede tener más de 50 caracteres");
            retorno = false;
        } else {
            txtZona.setError(null);
        }

        if(actividadDeCampo.getResumen().length()>200) {
            txtResumen.setError("El resumen no puede tener más de 200 caracteres");
            retorno = false;
        } else {
            txtResumen.setError(null);
        }

        if(actividadDeCampo.getEquipamiento().length()>200) {
            txtEquipamiento.setError("El equipamiento no puede tener más de 200 caracteres");
            retorno = false;
        } else {
            txtEquipamiento.setError(null);
        }

        if(actividadDeCampo.getMetodoDeMuestreo().length()>200) {
            txtMetodo.setError("El método no puede tener más de 200 caracteres");
            retorno = false;
        } else {
            txtMetodo.setError(null);
        }

        if(actividadDeCampo.getEstacionDeMuestreo().length()>50) {
            txtEstacion.setError("La estación de muestreo no puede tener más de 50 caracteres");
            retorno = false;
        } else {
            txtEstacion.setError(null);
        }

        if(actividadDeCampo.getTipoDeMuestreo().length()>200) {
            txtTipoDeMuestreo.setError("El tipo de muestreo no puede tener más de 200 caracteres");
            retorno = false;
        } else {
            txtTipoDeMuestreo.setError(null);
        }

        if (spFormulario.getSelectedItemPosition() < 1) {
            Toast.makeText(getApplicationContext(), "Debe seleccionar un formulario", Toast.LENGTH_LONG).show();
            retorno = false;
        }

        if (spRegion.getSelectedItemPosition() < 1) {             retorno = false;         }
        if (spDepartamento.getSelectedItemPosition() < 1) {             retorno = false;         }
        if (spLocalidad.getSelectedItemPosition() < 1) {             retorno = false;         }

        return retorno;
    }

    private void getFormularios() {
        try {
            formularioViewModel = new FormularioViewModel(getApplication());
            try {
                formularios = formularioViewModel.getFormularios();
                cargarSpinnerFormularios(formularios);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRegiones() {
       regionViewModel = new RegionViewModel(getApplication());
        try {
            regiones = regionViewModel.getRegions();
            cargarSpinnerRegiones(regiones);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getDepartamentos(RegionDTO r) {
        departamentoViewModel = new DepartamentoViewModel(getApplication());
        try {
            departamentos = departamentoViewModel.getDepartamentosXRegion(r.getIdregion());
            cargarSpinnerDepartamentos(departamentos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLocalidades(DepartamentoDTO d) {
        localidadViewModel = new LocalidadViewModel(getApplication());
        try {
            localidades = localidadViewModel.getLocalidadesXDepartamento(d.getIddepartamento());
            cargarSpinnerLocalidades(localidades);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarSpinnerFormularios(List<FormularioDTO> formularios) {
        FormularioDTO fDefault = new FormularioDTO();
        fDefault.setNombre("Seleccione formulario...  *");
        fDefault.setIdformulario(0);
        formularios.add(0, fDefault);
        ArrayAdapter<FormularioDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, formularios);
        spFormulario.setAdapter(adaptador);
    }

    private void cargarSpinnerRegiones(List<RegionDTO> regiones) {
        RegionDTO rDefault = new RegionDTO();
        rDefault.setNombre("Seleccione region...  *");
        rDefault.setIdregion(0);
        regiones.add(0, rDefault);
        ArrayAdapter<RegionDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, regiones);
        spRegion.setAdapter(adaptador);
    }

    private void cargarSpinnerDepartamentos(List<DepartamentoDTO> departamentos) {
        DepartamentoDTO dDefault = new DepartamentoDTO();
        dDefault.setNombre("Seleccione departamento...");
        dDefault.setIddepartamento(0);
        departamentos.add(0, dDefault);
        ArrayAdapter<DepartamentoDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, departamentos);
        spDepartamento.setAdapter(adaptador);
    }

    private void cargarSpinnerLocalidades(List<LocalidadDTO> localidades) {
        LocalidadDTO lDefault = new LocalidadDTO();
        lDefault.setNombre("Seleccione localidad...");
        lDefault.setIdlocalidad(0);
        localidades.add(0, lDefault);
        ArrayAdapter<LocalidadDTO> adaptador = new ArrayAdapter<>(this, R.layout.spinner, localidades);
        spLocalidad.setAdapter(adaptador);
    }

    private void deshabilitarSpinners() {
        if (spRegion!=null && spDepartamento!=null && spLocalidad!=null) {
            if (spRegion.getSelectedItemPosition() < 1) {
                spDepartamento.setEnabled(false);
                spLocalidad.setEnabled(false);
                spDepartamento.setAdapter(null);
                spLocalidad.setAdapter(null);
            }
            if (spDepartamento.getSelectedItemPosition() < 1) {
                spLocalidad.setEnabled(false);
                spLocalidad.setAdapter(null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea cerrar sesión?");
            builder.setCancelable(true);
            builder.setNegativeButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Sesion.getInstancia().setUsuarioLogueado(new UsuarioDTO());
                    finish();
                    Intent intentLogin = new Intent(getApplicationContext(), login.class);
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
        if (id == R.id.conexion) {
            String mensaje = "";
            if (Sesion.isHayInternet() && Sesion.isHayRest()) {
                mensaje = "Está conectado a Internet";
            } else {
                mensaje = "No está conectado a Internet";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(mensaje);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
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

    @Override
    protected void onResume() {
        super.onResume();

        handler = new Handler();
        runnable = new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {

                Sesion s = Sesion.getInstancia();
                if (s.isHayInternet() && s.isHayRest()) {
                    conexion = findViewById(R.id.conexion);
                    conexion.setIcon(getResources().getDrawable(R.drawable.ic_baseline_cloud_done_24));
                } else {
                    conexion = findViewById(R.id.conexion);
                    conexion.setIcon(getResources().getDrawable(R.drawable.ic_baseline_cloud_off_24));
                }

                if (ubicacion!=null && txtUbicacion.getText().length()<1) {
                    latitud = Double.toString(ubicacion.getLatitude());
                    longitud = Double.toString(ubicacion.getLongitude());
                    txtUbicacion.setText(latitud + ", " + longitud);
                }





                handler.postDelayed(this, PERIODO);
            }
        };
        handler.postDelayed(runnable, PERIODO);
    }


    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    public void cargarImagen(View view) {
        permisosDeCamara();

    }

    private void permisosDeCamara() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},PackageManager.PERMISSION_GRANTED);
            if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            }
        } else {
            try {
                abrirCamara();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirCamara() {
        Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(camara);
    }

    private void mostrarImagen() {

        imgdialog.setContentView(R.layout.activity_imagen_pop_up);
        imgdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageView imgCerrar = imgdialog.findViewById(R.id.imgCerrar);
        ImageView imgActividad = imgdialog.findViewById(R.id.imgActividad);

        imgActividad.setImageBitmap(imagenCargada);

        imgdialog.show();

        imgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgdialog.cancel();
            }
        });

    }

}

//a

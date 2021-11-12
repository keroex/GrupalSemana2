package com.utec.grupalsemana2.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.sesion.Sesion;
import com.utec.grupalsemana2.utilidades.Converters;
import com.utec.grupalsemana2.utilidades.FormatoFecha;

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
    TextView txtVerImagen;
    public static final long PERIODO = 1000;
    private Handler handler;
    private Runnable runnable;
    private ActionMenuItemView conexion;
    private Bitmap imagenCargada;
    private Dialog imgDialog;

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
        txtVerImagen = (TextView) findViewById(R.id.txtVerImagen);
        imgDialog = new Dialog(this);

        Intent intent = getIntent();

        if(intent.hasExtra("actividad-seleccionada")) {
            ActividadDeCampo actividadDeCampo = intent.getParcelableExtra("actividad-seleccionada");
            cargarActDeCampo(actividadDeCampo);
        }

        txtVerImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagenCargada != null) {
                    mostrarImagen();
                } else {
                    System.out.println("F");
                }
            }
        });

    }

    public void cargarActDeCampo(ActividadDeCampo act) {

        txtFecha.setText(FormatoFecha.DateToString(act.getFecha()));
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

        if(act.getImagen().length>0) {
            imagenCargada = Converters.convertirByteArrayAImagen(act.getImagen());
        } else {
            txtVerImagen.setText("No hay imagen");
            txtVerImagen.setTextColor(Color.GRAY);
        }

    }

    @Override
    public void onBackPressed() {
    finish();
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
        if(id==R.id.conexion) {
            String mensaje = "";
            if(Sesion.isHayInternet() && Sesion.isHayRest()) {
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
                    conexion= findViewById(R.id.conexion);
                    conexion.setIcon(getResources().getDrawable(R.drawable.ic_baseline_cloud_done_24));
                }
                else {
                    conexion= findViewById(R.id.conexion);
                    conexion.setIcon(getResources().getDrawable(R.drawable.ic_baseline_cloud_off_24));
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

    private void mostrarImagen() {

        imgDialog.setContentView(R.layout.activity_imagen_pop_up);
        imgDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageView imgCerrar = imgDialog.findViewById(R.id.imgCerrar);
        ImageView imgActividad = imgDialog.findViewById(R.id.imgActividad);

        imgActividad.setImageBitmap(imagenCargada);

        imgDialog.show();

        imgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgDialog.cancel();
            }
        });

    }

}
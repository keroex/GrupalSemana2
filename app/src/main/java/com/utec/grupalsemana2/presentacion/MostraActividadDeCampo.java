package com.utec.grupalsemana2.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.sesion.Sesion;

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

        txtFecha.setText(act.getFecha().toString());
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
        return true;
    }

}
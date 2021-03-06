package com.utec.grupalsemana2.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.UsuarioApi;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.models.UsuarioViewModel;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.servicios.ServicioActividadesDeCampo;
import com.utec.grupalsemana2.servicios.ServicioInternet;
import com.utec.grupalsemana2.servicios.ServicioSpiners;
import com.utec.grupalsemana2.sesion.Sesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private UsuarioApi usuarioApi = RestAppClient.getClient().create(UsuarioApi.class);
    private EditText txtNombreUsuario;
    private EditText txtContrasenia;
    private UsuarioViewModel usuarioViewModel;
    public static final long PERIODO = 1000; // 1 segundos (1 * 1000 millisegundos)
    private Handler handler;
    private Runnable runnable;
    private ActionMenuItemView conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtNombreUsuario = (EditText) findViewById(R.id.txtNombreUsuario);
        txtContrasenia = (EditText) findViewById(R.id.txtContrasenia);
        startService(new Intent(getBaseContext(), ServicioInternet.class));
        startService(new Intent(getBaseContext(), ServicioActividadesDeCampo.class));
        startService(new Intent(getBaseContext(), ServicioSpiners.class));
        usuarioViewModel = new UsuarioViewModel(getApplication());
    }

    public void login(View view) {
        try{

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNombreUsuario(txtNombreUsuario.getText().toString());
            usuarioDTO.setContrasenia(txtContrasenia.getText().toString());
            if(validarCampos(usuarioDTO)) {
                try {
                    iniciarSesion(usuarioDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validarCampos(UsuarioDTO usuarioDTO) {

        boolean retorno = true;
        if(usuarioDTO.getNombreUsuario().isEmpty()) {
            txtNombreUsuario.setError("No puede quedar vac??o");
            retorno = false;
        } else {
            txtNombreUsuario.setError(null);
        }

        if(usuarioDTO.getContrasenia().isEmpty()) {
            txtContrasenia.setError("No puede quedar vac??o");
            retorno = false;
        } else {
            txtContrasenia.setError(null);
        }

        return retorno;
    }

    private void iniciarSesion(UsuarioDTO usuarioLoguear) {
        if(Sesion.isHayInternet() && Sesion.isHayRest()) {
            iniciarSesionOnline(usuarioLoguear);
        } else {
            iniciarSesionLocal(usuarioLoguear);
        }
    }

    private void irAListaActividades() {
        Intent intentLista = new Intent(this, ListarActividadesDeCampo.class);
        startActivity(intentLista);
        finish();
    }

    @Override
    protected void onResume() {
        if(Sesion.getInstancia().getUsuarioLogueado()==null) {
            super.onResume();
        } else if(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario()!=0) {
            super.onResume();
            irAListaActividades();
        } else {
            super.onResume();
        }


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



    private void iniciarSesionOnline(UsuarioDTO usuarioLoguear) {
        Call<UsuarioDTO> call = usuarioApi.login(usuarioLoguear);
        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful()) {
                    Sesion.getInstancia().setUsuarioLogueado(response.body());
                    System.out.println("Se logueo correctamente el usuario " + Sesion.getInstancia().getUsuarioLogueado().getNombreUsuario() + " con id " + String.valueOf(Sesion.getInstancia().getUsuarioLogueado().getIdUsuario()));

                    // evaluar si el usuario existe en bd local, si no existe: lo creamos, si existe: lo actualizamos
                    if(usuarioViewModel.login(Sesion.getInstancia().getUsuarioLogueado())!=0) {
                        usuarioViewModel.update(Sesion.getInstancia().getUsuarioLogueado());
                    } else {
                        usuarioViewModel.insert(Sesion.getInstancia().getUsuarioLogueado());
                    }

                    Toast.makeText(getApplicationContext(),"Usuario logueado correctamente",Toast.LENGTH_LONG).show();
                    irAListaActividades();

                } else if (response.code()==404){
                    Toast.makeText(getApplicationContext(),"Usuario o contrase??a incorrectos",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"No se pudo ingresar",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                System.out.println("Fallo la conexion");
                Toast.makeText(getApplicationContext(),"Usuario o contrase??a incorrectos",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void iniciarSesionLocal(UsuarioDTO usuarioLoguear) {
        if (usuarioViewModel.login(usuarioLoguear)!=0) {
            usuarioLoguear.setIdUsuario(usuarioViewModel.login(usuarioLoguear));
            Sesion.getInstancia().setUsuarioLogueado(usuarioLoguear);
            Toast.makeText(getApplicationContext(),"Usuario logueado correctamente",Toast.LENGTH_LONG).show();
            irAListaActividades();
        } else {
            System.out.println(usuarioLoguear.toString());
            Toast.makeText(getApplicationContext(),"Usuario o contrase??a incorrectos",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menulogin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.conexion) {
            String mensaje = "";
            if(Sesion.isHayInternet() && Sesion.isHayRest()) {
                mensaje = "Est?? conectado a Internet";
            } else {
                mensaje = "No est?? conectado a Internet";
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

}
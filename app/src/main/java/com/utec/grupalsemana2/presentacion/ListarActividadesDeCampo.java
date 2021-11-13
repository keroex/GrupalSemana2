package com.utec.grupalsemana2.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.repositories.ActividadDeCampoRepository;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.sesion.Sesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarActividadesDeCampo extends AppCompatActivity implements ListActividadAdapter.ActividadClickListener {


    private ActividadDeCampoAPI actividadDeCampoAPI = RestAppClient.getClient().create(ActividadDeCampoAPI.class);
    private ActividadDeCampoViewModel actividadDeCampoViewModel;
    private MutableLiveData<List<ActividadDeCampo>> actividadesDeCampo = new MutableLiveData<>();
    private TextView txtNoHay;
    private ImageView iconoNoHay;
    public static final long PERIODO = 1000; // 1 segundos (1 * 1000 millisegundos)
    private Handler handler;
    private Runnable runnable;
    private ActionMenuItemView conexion;
    private FloatingActionButton botonAlta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_listar_actividades_de_campo);

        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        try {
            if(Sesion.isHayInternet() && Sesion.isHayRest()) {
                getActividadesDeCampo(Sesion.getInstancia().getUsuarioLogueado());
            }   else {
                actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
                listarActividadesDeCampo(actividadDeCampoViewModel.getActividadDeCampos());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void getActividadesDeCampo(UsuarioDTO usuarioLogueado) {

        MutableLiveData<List<ActividadDeCampo>> ac = new MutableLiveData<List<ActividadDeCampo>>();
        Call<List<ActividadDeCampo>> call = actividadDeCampoAPI.getActividadesDeCampoXUsuario(usuarioLogueado.getIdUsuario());

        call.enqueue(new Callback<List<ActividadDeCampo>>() {
            @Override
            public void onResponse(Call<List<ActividadDeCampo>> call, Response<List<ActividadDeCampo>> response) {

                if (response.isSuccessful()) {
                    List<ActividadDeCampo> actividades = response.body();
                    if (actividades != null) {
                        ac.setValue(actividades);
                    }
                    if (actividadesDeCampo.getValue() == null) {
                        actividadesDeCampo.setValue(ac.getValue());
                        listarActividadesDeCampo(actividadesDeCampo.getValue());
                    }else if (ac.getValue().size()!=actividadesDeCampo.getValue().size()) {
                        actividadesDeCampo.setValue(ac.getValue());
                        listarActividadesDeCampo(actividadesDeCampo.getValue());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ActividadDeCampo>> call, Throwable t) {

            }
        });
    }

    private void listarActividadesDeCampo(List<ActividadDeCampo> actividadesDeCampo) {

        txtNoHay = findViewById(R.id.txtNoHay);
        txtNoHay.setVisibility(View.GONE);
        iconoNoHay = findViewById(R.id.imgNoHay);
        iconoNoHay.setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLista);
        if (actividadesDeCampo.size() == 0) {
            txtNoHay.setVisibility(View.VISIBLE);
            iconoNoHay.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(null);
        } else {
            ListActividadAdapter listActividadAdapter = new ListActividadAdapter(actividadesDeCampo, this, this);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listActividadAdapter);
        }

    }

    @Override
    public void onActividadClick(int position) {
        try {
            if (Sesion.isHayRest() && Sesion.isHayInternet()) {
                Bundle b = new Bundle();
                b.putParcelable("actividad-seleccionada",actividadesDeCampo.getValue().get(position));
                Intent intent = new Intent(this, MostraActividadDeCampo.class);
                intent.putExtra("bundle-actividad-seleccionada",b);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),"No se puede acceder al detalle sin conexion a internet",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void altaActividadDeCampo(View view) {
        Intent intentAlta = new Intent(this, AltaActividadDeCampo.class);
        startActivity(intentAlta);
    }

    @Override
    protected void onResume() {
        try {
            if(Sesion.isHayInternet() && Sesion.isHayRest()) {
                getActividadesDeCampo(Sesion.getInstancia().getUsuarioLogueado());
            }   else {
                actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
                listarActividadesDeCampo(actividadDeCampoViewModel.getActividadDeCampos());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
        handler = new Handler();
        runnable = new Runnable() {
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                botonAlta =  findViewById(R.id.botonAlta);
                if (Sesion.isHabilitaAlta()) {
                    botonAlta.setVisibility(View.VISIBLE);
                } else {
                    botonAlta.setVisibility(View.GONE);

                }

                if (!Sesion.isHayQueRecargar()) {
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
                Sesion s = Sesion.getInstancia();
                if (s.isHayInternet() && s.isHayRest()) {
                    conexion= findViewById(R.id.conexion);
                    conexion.setIcon(getResources().getDrawable(R.drawable.ic_baseline_cloud_done_24));
                    if (Sesion.isHuboPerdidaDeConexion() || Sesion.isHayQueRecargar()) {
                        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        getActividadesDeCampo(Sesion.getInstancia().getUsuarioLogueado());
                        Sesion.setHayQueRecargar(false);
                        if (Sesion.isHuboPerdidaDeConexion()) {
                            Sesion.setHuboPerdidaDeConexion(false);
                        }
                    }



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
}
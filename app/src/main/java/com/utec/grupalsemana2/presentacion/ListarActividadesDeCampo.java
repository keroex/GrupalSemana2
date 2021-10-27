package com.utec.grupalsemana2.presentacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
    private MutableLiveData<List<ActividadDeCampo>> actividadesDeCampo = new MutableLiveData<>();
    private TextView txtNoHay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_de_campo);
        txtNoHay = findViewById(R.id.txtNoHay);
        txtNoHay.setVisibility(View.INVISIBLE);
        try {
            getActividadesDeCampo(Sesion.getInstancia().getUsuarioLogueado());
            if(actividadesDeCampo.getValue().size()==0) {
                txtNoHay.setVisibility(View.VISIBLE);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

    }


    private void getActividadesDeCampo(UsuarioDTO usuarioLogueado) {

        actividadesDeCampo.setValue(new ArrayList<>());
        Call<List<ActividadDeCampo>> call = actividadDeCampoAPI.getActividadesDeCampoXUsuario(usuarioLogueado.getIdUsuario());

        call.enqueue(new Callback<List<ActividadDeCampo>>() {
            @Override
            public void onResponse(Call<List<ActividadDeCampo>> call, Response<List<ActividadDeCampo>> response) {

                if (response.isSuccessful()) {
                    List<ActividadDeCampo> actividades = response.body();
                    if(actividades!=null) {
                        actividadesDeCampo.setValue(actividades);
                    }
                    listarActividadesDeCampo(actividadesDeCampo);
                }

            }

            @Override
            public void onFailure(Call<List<ActividadDeCampo>> call, Throwable t) {

            }
        });
    }

    private void listarActividadesDeCampo(MutableLiveData<List<ActividadDeCampo>> actividadesDeCampo) {

        ListActividadAdapter listActividadAdapter = new ListActividadAdapter(actividadesDeCampo.getValue(), this, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listActividadAdapter);

    }

    @Override
    public void onActividadClick(int position) {
        try {
            Intent intent = new Intent(this, MostraActividadDeCampo.class);
            intent.putExtra("actividad-seleccionada", actividadesDeCampo.getValue().get(position));
            startActivity(intent);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void altaActividadDeCampo(View view) {
        Intent intentAlta = new Intent(this, AltaActividadDeCampo.class);
        startActivity(intentAlta);
    }

    @Override
    protected void onResume() {
        getActividadesDeCampo(Sesion.getInstancia().getUsuarioLogueado());
        super.onResume();
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
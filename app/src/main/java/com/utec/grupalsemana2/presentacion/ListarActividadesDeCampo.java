package com.utec.grupalsemana2.presentacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.repositories.ActividadDeCampoRepository;
import com.utec.grupalsemana2.servicios.RestAppClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarActividadesDeCampo extends AppCompatActivity implements ListActividadAdapter.ActividadClickListener {


    private ActividadDeCampoAPI actividadDeCampoAPI = RestAppClient.getClient().create(ActividadDeCampoAPI.class);
    private MutableLiveData<List<ActividadDeCampo>> actividadesDeCampo = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_de_campo);

        init();

    }

    private void init() {
        getActividadesDeCampo();
        Intent intent = getIntent();
    }

    private void getActividadesDeCampo() {

        actividadesDeCampo.setValue(new ArrayList<>());
        Call<List<ActividadDeCampo>> call = actividadDeCampoAPI.getActividadesDeCampo();

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
        Intent intent = new Intent(this, MostraActividadDeCampo.class);
        intent.putExtra("actividad-seleccionada", actividadesDeCampo.getValue().get(position));
        startActivity(intent);
    }

    private void altaActividadDeCampo(View view) {
        Intent intentAlta = new Intent(this, AltaActividadDeCampo.class);
        startActivity(intentAlta);
    }

}
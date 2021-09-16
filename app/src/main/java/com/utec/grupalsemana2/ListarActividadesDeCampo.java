package com.utec.grupalsemana2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import interfaces.ActividadDeCampoAPI;
import logica.ActividadDeCampo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarActividadesDeCampo extends AppCompatActivity {

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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lezicalandia.ddns.net:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ActividadDeCampoAPI actividadDeCampoAPI = retrofit.create(ActividadDeCampoAPI.class);
        Call<List<ActividadDeCampo>> call = actividadDeCampoAPI.getActividadesDeCampo();

        System.out.println("1111111111111111");

        call.enqueue(new Callback<List<ActividadDeCampo>>() {
            @Override
            public void onResponse(Call<List<ActividadDeCampo>> call, Response<List<ActividadDeCampo>> response) {

                    List<ActividadDeCampo> actividades = response.body();
                    if(actividades!=null) {
                        actividadesDeCampo.setValue(actividades);
                    }
                    System.out.println("2222222222222222");

                    listarActividadesDeCampo(actividadesDeCampo);

            }

            @Override
            public void onFailure(Call<List<ActividadDeCampo>> call, Throwable t) {

            }
        });
    }

    private void listarActividadesDeCampo(MutableLiveData<List<ActividadDeCampo>> actividadesDeCampo) {
        List<ActividadDeCampo> actividadesList = actividadesDeCampo.getValue();
        ListActividadAdapter listActividadAdapter = new ListActividadAdapter(actividadesList, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listActividadAdapter);

    }

}
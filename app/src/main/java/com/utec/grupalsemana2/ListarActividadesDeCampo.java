package com.utec.grupalsemana2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import interfaces.ActividadDeCampoAPI;
import logica.ActividadDeCampo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListarActividadesDeCampo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_de_campo);
    }

    private void getActividadesDeCampo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://lezicalandia.ddns.net:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ActividadDeCampoAPI actividadDeCampoAPI = retrofit.create(ActividadDeCampoAPI.class);
        Call<List<ActividadDeCampo>> call = actividadDeCampoAPI.getActividadesDeCampo();

        call.enqueue(new Callback<List<ActividadDeCampo>>() {
            @Override
            public void onResponse(Call<List<ActividadDeCampo>> call, Response<List<ActividadDeCampo>> response) {
                if (response.isSuccessful()) {
                    List<ActividadDeCampo> actividadDeCampos = response.body();
                    listarActividadesDeCampo(actividadDeCampos);
                }
            }

            @Override
            public void onFailure(Call<List<ActividadDeCampo>> call, Throwable t) {

            }
        });
    }

    private void listarActividadesDeCampo(List<ActividadDeCampo> actividadDeCampos) {

    }

}
package com.utec.grupalsemana2.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.interfaces.DepartamentoApi;
import com.utec.grupalsemana2.logica.DepartamentoDTO;

import com.utec.grupalsemana2.models.DepartamentoViewModel;
import com.utec.grupalsemana2.sesion.Sesion;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioDepartamentos extends Service {
    Runnable runnable;
    private Handler handler = new Handler();
    private MutableLiveData<List<DepartamentoDTO>> departamentoesRest = new MutableLiveData<>();
    private DepartamentoApi departamentoApi = RestAppClient.getClient().create(DepartamentoApi.class);
    private DepartamentoViewModel departamentoViewModel;
    private int contador=0;
    private int tiempo=1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (contador < 3) {
                    contador++;
                }
                if (contador == 2) {
                    tiempo=600000;
                }
                if (Sesion.isVolvioLaConexionDep()) {
                    contador=0;
                    tiempo=1000;
                    Sesion.setVolvioLaConexionDep(false);
                }

                if (Sesion.isHayInternet() && Sesion.isHayRest()) {
                    actualizarDepartamentos();
                }

                handler.postDelayed(this, tiempo);
            }
        };
        handler.postDelayed(runnable, tiempo);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void actualizarDepartamentos() {
        departamentoViewModel = new DepartamentoViewModel(getApplication());
        List<DepartamentoDTO> departamentoesBD = departamentoViewModel.getDepartamentos();
        getDepartamentoesRest();


        if (departamentoesRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (DepartamentoDTO r : departamentoesRest.getValue()) {
                boolean existe = false;
                for (DepartamentoDTO rdto : departamentoesBD) {
                    if (r.getIddepartamento() == rdto.getIddepartamento()) {
                        existe = true;
                    }
                }
                if (existe) {
                    departamentoViewModel.update(r);

                } else {
                    departamentoViewModel.insert(r);

                }
            }

            //Si esta en la bd y no en el rest borro
            for (DepartamentoDTO rdto : departamentoesBD) {
                boolean encontre = false;
                for (DepartamentoDTO r : departamentoesRest.getValue()) {
                    if (r.getIddepartamento() == rdto.getIddepartamento()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    departamentoViewModel.delete(rdto);

                }

            }
        }

    }

    private void getDepartamentoesRest() {

        Call<List<DepartamentoDTO>> call = departamentoApi.getDepartamentosTodos();
        call.enqueue(new Callback<List<DepartamentoDTO>>() {
            @Override
            public void onResponse(Call<List<DepartamentoDTO>> call, Response<List<DepartamentoDTO>> response) {
                if(response.isSuccessful()) {
                    List<DepartamentoDTO> misDepartamentoes = response.body();
                    if(misDepartamentoes!=null) {
                        departamentoesRest.setValue(misDepartamentoes);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<DepartamentoDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hubo un problema al actualizar los departamentos\n Si el problema persiste contactese con el administrador.", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

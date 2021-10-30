package com.utec.grupalsemana2.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.interfaces.LocalidadApi;
import com.utec.grupalsemana2.logica.LocalidadDTO;

import com.utec.grupalsemana2.models.LocalidadViewModel;
import com.utec.grupalsemana2.sesion.Sesion;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioLocalidades extends Service {
    Runnable runnable;
    private Handler handler = new Handler();
    private MutableLiveData<List<LocalidadDTO>> localidadesRest = new MutableLiveData<>();
    private LocalidadApi localidadApi = RestAppClient.getClient().create(LocalidadApi.class);
    private LocalidadViewModel localidadViewModel;
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
                Log.i("SERVICIO_LOCALIDADES", "run" );

                if (contador < 3) {
                    contador++;
                }
                if (contador == 2) {
                    tiempo=600000;
                }
                if (Sesion.isVolvioLaConexionLoc()) {
                    contador=0;
                    tiempo=1000;
                    Sesion.setVolvioLaConexionLoc(false);
                }

                if (Sesion.isHayInternet() && Sesion.isHayRest()) {
                    actualizarLocalidades();

                }


                handler.postDelayed(this, tiempo);
            }
        };
        handler.postDelayed(runnable, tiempo);
        Log.i("SERVICIO_LOCALIDADES", "Servicio iniciado " );

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICIO_LOCALIDADES", "Servicio destruido " );
    }


    private void actualizarLocalidades() {
        localidadViewModel = new LocalidadViewModel(getApplication());
        List<LocalidadDTO> localidadesBD = localidadViewModel.getLocalidades();
        getLocalidadesRest();
        System.out.println("Cantidad de localidades en BD = " + localidadesBD.size());

        if (localidadesRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (LocalidadDTO r : localidadesRest.getValue()) {
                boolean existe = false;
                for (LocalidadDTO rdto : localidadesBD) {
                    if (r.getIdlocalidad() == rdto.getIdlocalidad()) {
                        existe = true;
                    }
                }
                if (existe) {
                    localidadViewModel.update(r);
                    System.out.println("Actualice la localidad con id = " + r.getIdlocalidad());
                } else {
                    localidadViewModel.insert(r);
                    System.out.println("Agregue la localidad con id = " + r.getIdlocalidad());
                }
            }
            System.out.println("Cantidad de localidades en Rest = " + localidadesRest.getValue().size());
            System.out.println("Cantidad de localidades en BD = " + localidadesBD.size());

            //Si esta en la bd y no en el rest borro
            for (LocalidadDTO rdto : localidadesBD) {
                boolean encontre = false;
                for (LocalidadDTO r : localidadesRest.getValue()) {
                    if (r.getIdlocalidad() == rdto.getIdlocalidad()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    localidadViewModel.delete(rdto);
                    System.out.println("Borre la localidad con id = " + rdto.getIdlocalidad());
                }

            }
        }

    }

    private void getLocalidadesRest() {

        Call<List<LocalidadDTO>> call = localidadApi.getLocalidadesTodos();
        call.enqueue(new Callback<List<LocalidadDTO>>() {
            @Override
            public void onResponse(Call<List<LocalidadDTO>> call, Response<List<LocalidadDTO>> response) {
                if(response.isSuccessful()) {
                    List<LocalidadDTO> misLocalidades = response.body();
                    if(misLocalidades!=null) {
                        localidadesRest.setValue(misLocalidades);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<LocalidadDTO>> call, Throwable t) {

            }
        });
    }



}

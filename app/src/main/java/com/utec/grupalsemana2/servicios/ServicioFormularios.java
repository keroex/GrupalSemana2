package com.utec.grupalsemana2.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.interfaces.FormularioApi;
import com.utec.grupalsemana2.logica.FormularioDTO;

import com.utec.grupalsemana2.models.FormularioViewModel;
import com.utec.grupalsemana2.sesion.Sesion;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioFormularios extends Service {
    Runnable runnable;
    private Handler handler = new Handler();
    private MutableLiveData<List<FormularioDTO>> formulariosRest = new MutableLiveData<>();
    private FormularioApi formularioApi = RestAppClient.getClient().create(FormularioApi.class);
    private FormularioViewModel formularioViewModel;
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
                Log.i("SERVICIO_FORMULARIOS", "run" );
                if (contador < 3) {
                    contador++;
                }
                if (contador == 2) {
                    tiempo=60000;
                }
                if (Sesion.isVolvioLaConexionform()) {
                    contador=0;
                    tiempo=1000;
                    Sesion.setVolvioLaConexionform(false);
                }
                if (Sesion.isHayInternet() && Sesion.isHayRest()) {
                    actualizarFormularios();

                }


                handler.postDelayed(this, tiempo);
            }
        };
        handler.postDelayed(runnable, tiempo);
        Log.i("SERVICIO_FORMULARIOS", "Servicio iniciado " );

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICIO_FORMULARIOS", "Servicio destruido " );
    }


    private void actualizarFormularios() {
        formularioViewModel = new FormularioViewModel(getApplication());
        List<FormularioDTO> formulariosBD = formularioViewModel.getFormularios();
        getFormulariosRest();
        System.out.println("Cantidad de formularios en BD = " + formulariosBD.size());

        if (formulariosRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (FormularioDTO r : formulariosRest.getValue()) {
                boolean existe = false;
                for (FormularioDTO rdto : formulariosBD) {
                    if (r.getIdformulario() == rdto.getIdformulario()) {
                        existe = true;
                    }
                }
                if (existe) {
                    formularioViewModel.update(r);
                    System.out.println("Actualice la formulario con id = " + r.getIdformulario());
                } else {
                    formularioViewModel.insert(r);
                    System.out.println("Agregue la formulario con id = " + r.getIdformulario());
                }
            }
            System.out.println("Cantidad de formularios en Rest = " + formulariosRest.getValue().size());
            System.out.println("Cantidad de formularios en BD = " + formulariosBD.size());

            //Si esta en la bd y no en el rest borro
            for (FormularioDTO rdto : formulariosBD) {
                boolean encontre = false;
                for (FormularioDTO r : formulariosRest.getValue()) {
                    if (r.getIdformulario() == rdto.getIdformulario()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    formularioViewModel.delete(rdto);
                    System.out.println("Borre la formulario con id = " + rdto.getIdformulario());
                }

            }
        }

    }

    private void getFormulariosRest() {

        Call<List<FormularioDTO>> call = formularioApi.getFormularios();
        call.enqueue(new Callback<List<FormularioDTO>>() {
            @Override
            public void onResponse(Call<List<FormularioDTO>> call, Response<List<FormularioDTO>> response) {
                if(response.isSuccessful()) {
                    List<FormularioDTO> misFormularios = response.body();
                    if(misFormularios!=null) {
                        formulariosRest.setValue(misFormularios);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<FormularioDTO>> call, Throwable t) {

            }
        });
    }



}

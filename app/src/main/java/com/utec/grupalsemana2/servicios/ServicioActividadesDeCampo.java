package com.utec.grupalsemana2.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.sesion.Sesion;

import java.util.ArrayList;
import java.util.List;

public class ServicioActividadesDeCampo extends Service {

    Runnable runnable;
    private Handler handler = new Handler();
    ActividadDeCampoViewModel actividadDeCampoViewModel;
    List<ActividadDeCampo> actividadesActualizar;

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
                ServicioActividadesDeCampo.sincronizarAsyncTask sincronizarAsyncTask = new ServicioActividadesDeCampo.sincronizarAsyncTask();
                sincronizarAsyncTask.execute();

                handler.postDelayed(this, 1000 * 10);
            }
        };
        handler.postDelayed(runnable, 0);

        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class sincronizarAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                if (Sesion.isHayInternet() && Sesion.isHayRest()) {
                    actualizarActividades();
                    Sesion.setHayQueRecargar(true);
                }
                if (Sesion.isHayInternet() && Sesion.isHayRest() && actividadDeCampoViewModel.count()>0) {
                    Sesion.setHayQueRecargar(true);
                }

                Thread.sleep(10000);
            } catch (Exception e) {
                Log.e("SERVICIO_ACTIVIDADES", "Error en el servicio de Actividades de Campo");
            }
            return null;
        }
    }

    private void actualizarActividades() {
        actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
        ActividadDeCampo actividadDeCampoVieja = actividadDeCampoViewModel.actividadDeCampoVieja();
        if(actividadDeCampoVieja!=null) {
            actividadDeCampoViewModel.insertRest(actividadDeCampoVieja);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(Sesion.getInstancia().isActualizaActividadesOk()) {
                actividadDeCampoViewModel.delete(actividadDeCampoVieja);
                Sesion.getInstancia().setActualizaActividadesOk(false);
            }



        }
    }

}



package com.utec.grupalsemana2.servicios;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;

import java.util.List;

public class ServicioMostrarLog extends Service {
    public ServicioMostrarLog() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        //Toast.makeText(this,"Servicio Iniciado",Toast.LENGTH_SHORT).show();
        ActividadDeCampoViewModel actividadDeCampoViewModel = new ActividadDeCampoViewModel(getApplication());
        List<ActividadDeCampo> acts = actividadDeCampoViewModel.getActividadDeCampos().getValue();
        Log.i("Init" ,"Se inicia el servicio.");
        for (ActividadDeCampo a : acts) {
            Log.i("Actividad de Campo" + a.getIdactividadDeCampo(), a.toString());
        }
        this.stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i("End" ,"Se termina el servicio.");
        super.onDestroy();
    }
}
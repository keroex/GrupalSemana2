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

import com.utec.grupalsemana2.interfaces.RegionApi;
import com.utec.grupalsemana2.interfaces.UsuarioApi;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.RegionDTO;
import com.utec.grupalsemana2.models.ActividadDeCampoViewModel;
import com.utec.grupalsemana2.models.RegionViewModel;
import com.utec.grupalsemana2.sesion.Sesion;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioRegiones extends Service {
    Runnable runnable;
    private Handler handler = new Handler();
    private MutableLiveData<List<RegionDTO>> regionesRest = new MutableLiveData<>();
    private RegionApi regionApi = RestAppClient.getClient().create(RegionApi.class);
    private RegionViewModel regionViewModel;
    private int contador=0;
    private int tiempo=500;

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
                if (Sesion.isVolvioLaConexionReg()) {
                    contador=0;
                    tiempo=1000;
                    Sesion.setVolvioLaConexionReg(false);
                }

                if (Sesion.isHayInternet() && Sesion.isHayRest()) {
                    actualizarRegiones();

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

    private void actualizarRegiones() {
        regionViewModel = new RegionViewModel(getApplication());
        List<RegionDTO> regionesBD = regionViewModel.getRegions();
        getRegionesRest();

        if (regionesRest.getValue()!=null) {
            //Si existe actualizo y sino agrego
            for (RegionDTO r : regionesRest.getValue()) {
                boolean existe = false;
                for (RegionDTO rdto : regionesBD) {
                    if (r.getIdregion() == rdto.getIdregion()) {
                        existe = true;
                    }
                }
                if (existe) {
                    regionViewModel.update(r);
                } else {
                    regionViewModel.insert(r);
                }
            }

            //Si esta en la bd y no en el rest borro
            for (RegionDTO rdto : regionesBD) {
                boolean encontre = false;
                for (RegionDTO r : regionesRest.getValue()) {
                    if (r.getIdregion() == rdto.getIdregion()) {
                        encontre = true;
                    }
                }
                if (!encontre) {
                    regionViewModel.delete(rdto);
                }

            }
        }

    }

    private void getRegionesRest() {
        Call<List<RegionDTO>> call = regionApi.getRegiones();
        call.enqueue(new Callback<List<RegionDTO>>() {
            @Override
            public void onResponse(Call<List<RegionDTO>> call, Response<List<RegionDTO>> response) {
                if(response.isSuccessful()) {
                    List<RegionDTO> misRegiones = response.body();
                    if(misRegiones!=null) {
                        regionesRest.setValue(misRegiones);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<RegionDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hubo un problema al actualizar las regiones\n Si el problema persiste contactese con el administrador.", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

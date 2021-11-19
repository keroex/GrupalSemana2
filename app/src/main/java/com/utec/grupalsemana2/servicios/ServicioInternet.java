package com.utec.grupalsemana2.servicios;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.utec.grupalsemana2.interfaces.OnlineApi;
import com.utec.grupalsemana2.sesion.Sesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicioInternet extends Service {

    Runnable runnable;
    private Handler handler = new Handler();
    private Boolean onlineREST;
    private OnlineApi onlineApi = RestAppClient.getClient().create(OnlineApi.class);
    private Boolean yaPaso = false;
    int tiempo = 1000;

    public ServicioInternet() {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        runnable = new Runnable() {
            @Override
            public void run() {
                sincronizarAsyncTask sincronizarAsyncTask = new sincronizarAsyncTask();
                sincronizarAsyncTask.execute();
                if (yaPaso) {
                    tiempo=3000;
                }
                yaPaso=true;
                handler.postDelayed(this, tiempo);

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

                boolean conexionAntes = Sesion.isHayInternet() && Sesion.isHayRest();
                verificarInternet();
                verificarOnline();
                boolean conexionAhora = Sesion.isHayInternet() && Sesion.isHayRest();
                if ((conexionAntes == true && conexionAhora == false) || (conexionAntes == false && conexionAhora == false)) {
                    Sesion.setHuboPerdidaDeConexion(true);
                }

                if (Sesion.isHuboPerdidaDeConexion() && conexionAhora == true) {
                    Sesion.setVolvioLaConexionDep(true);
                    Sesion.setVolvioLaConexionLoc(true);
                    Sesion.setVolvioLaConexionReg(true);
                    Sesion.setVolvioLaConexionform(true);

                }



                Thread.sleep(1000);
            } catch (Exception e) {
                Log.e("SERVICIO_INTERNET", "sincronizarAsyncTask ERRROR");
            }
            return null;
        }
    }

    private void verificarOnline () {
        Call<Boolean> call = onlineApi.online();
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    onlineREST = response.body();

                } else {
                    onlineREST = false;
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                onlineREST =false;
            }
        });
        Sesion s = Sesion.getInstancia();
        s.setHayRest(onlineREST);
    }

    private void verificarInternet() {
        Context context = getApplicationContext();
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            Sesion s = Sesion.getInstancia();
            s.setHayInternet(true);
        } else {
            Sesion s = Sesion.getInstancia();
            s.setHayInternet(false);
        }
    }

}

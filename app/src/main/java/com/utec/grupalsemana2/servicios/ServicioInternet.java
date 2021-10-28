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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.utec.grupalsemana2.R;
import com.utec.grupalsemana2.interfaces.OnlineApi;
import com.utec.grupalsemana2.interfaces.UsuarioApi;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.sesion.Sesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ServicioInternet extends Service {

    Runnable runnable;
    private Handler handler = new Handler();
    private Boolean onlineREST;
    private OnlineApi onlineApi = RestAppClient.getClient().create(OnlineApi.class);

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
                Log.i("SERVICIO", "run" );
                sincronizarAsyncTask sincronizarAsyncTask = new sincronizarAsyncTask();
                sincronizarAsyncTask.execute();

                handler.postDelayed(this, 1000 * 15);
            }
        };
        handler.postDelayed(runnable, 0);
        Log.i("SERVICIO", "Servicio iniciado " );

        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICIO", "Servicio destruido " );
    }

    private class sincronizarAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Log.i("SERVICIO", "sincronizarAsyncTask INI" );

            try {

                verificarInternet();
                verificarOnline();



                Thread.sleep(1000);
            } catch (Exception e) {
                Log.i("SERVICIO", "sincronizarAsyncTask ERRROR");
            }
            Log.i("SERVICIO", "sincronizarAsyncTask FIN" );
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
                    Log.i("SERVICIO", "El rest responde" );



                } else {
                    onlineREST =false;
                    Log.i("SERVICIO", "El rest no responde" );

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("SERVICIO", "El rest no responde" );
                System.out.println("Fallo la conexion");

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
            Log.i("SERVICIO", "Conectado a Internet" );
            Sesion s = Sesion.getInstancia();
            s.setHayInternet(true);
        } else {
            Log.i("SERVICIO", "Sin conexion a Internet" );
            Sesion s = Sesion.getInstancia();
            s.setHayInternet(false);
        }
    }

}

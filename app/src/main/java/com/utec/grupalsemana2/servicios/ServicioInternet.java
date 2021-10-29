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
        Log.i("SERVICIO_INTERNET", "Servicio iniciado " );
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("SERVICIO_INTERNET", "run" );
                sincronizarAsyncTask sincronizarAsyncTask = new sincronizarAsyncTask();
                sincronizarAsyncTask.execute();

                handler.postDelayed(this, 500 * 10);
            }
        };
        handler.postDelayed(runnable, 0);


        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICIO_INTERNET", "Servicio destruido " );
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
                    Log.i("SERVICIO_INTERNET", "Hubo perdida de conexion" );
                }

                if (Sesion.isHuboPerdidaDeConexion() && conexionAhora == true) {
                    Sesion.setVolvioLaConexionDep(true);
                    Sesion.setVolvioLaConexionLoc(true);
                    Sesion.setVolvioLaConexionReg(true);
                    Sesion.setVolvioLaConexionform(true);

                }



                Thread.sleep(1000);
            } catch (Exception e) {
                Log.i("SERVICIO_INTERNET", "sincronizarAsyncTask ERRROR");
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
                    Log.i("SERVICIO_INTERNET", "El rest responde" );



                } else {
                    onlineREST = false;
                    Log.i("SERVICIO_INTERNET", "El rest no responde" );

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("SERVICIO_INTERNET", "El rest no responde" );

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
            Log.i("SERVICIO_INTERNET", "Conectado a Internet" );
            Sesion s = Sesion.getInstancia();
            s.setHayInternet(true);
        } else {
            Log.i("SERVICIO_INTERNET", "Sin conexion a Internet" );
            Sesion s = Sesion.getInstancia();
            s.setHayInternet(false);
        }
    }

}

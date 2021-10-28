package com.utec.grupalsemana2.repositories;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.presentacion.AltaActividadDeCampo;
import com.utec.grupalsemana2.presentacion.ListarActividadesDeCampo;
import com.utec.grupalsemana2.servicios.RestAppClient;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadDeCampoRepository {

    private ActividadDeCampoAPI actividadDeCampoAPI = RestAppClient.getClient().create(ActividadDeCampoAPI.class);
    private ActividadDeCampoDao actividadDeCampoDao;

    //private LiveData<List<ActividadDeCampo>> actividadDeCampos;
    private List<ActividadDeCampo> actividadDeCampos;
    public ActividadDeCampoRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        actividadDeCampoDao = db.actividadDeCampoDao();
        //actividadDeCampos = actividadDeCampoDao.findAll();
    }

    //public LiveData<List<ActividadDeCampo>> getActividadDeCampos() { return actividadDeCampos;    }

    public List<ActividadDeCampo> getActividadDeCampos() { return actividadDeCampos;   }

    public void insert (ActividadDeCampo actividadDeCampo, Context context) {

        actividadDeCampoAPI.agregarActividadDeCampo(actividadDeCampo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    System.out.println("SE AGREGO LA ACTIVIDAD DE CAMPO A LA BD");
                    Toast.makeText(context,"Se agregó la actividad de campo",Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("RESPONSE NOT SUCCESSFUL" + response.message());
                    Toast.makeText(context,"Falló en agregar actividad de campo",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("NO SE AGREGO" + t.fillInStackTrace().toString());
                Toast.makeText(context,"Error en conexión",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertDao(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.insert(actividadDeCampo);
    }

    public void update(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.delete(actividadDeCampo);
    }

    public int count() { return actividadDeCampoDao.count();    }


}
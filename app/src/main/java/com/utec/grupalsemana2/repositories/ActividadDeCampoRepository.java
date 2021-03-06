package com.utec.grupalsemana2.repositories;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.presentacion.AltaActividadDeCampo;
import com.utec.grupalsemana2.presentacion.ListarActividadesDeCampo;
import com.utec.grupalsemana2.servicios.RestAppClient;
import com.utec.grupalsemana2.sesion.Sesion;

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
    private List<ActividadDeCampo> actividadDeCampos;

    public ActividadDeCampoRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        actividadDeCampoDao = db.actividadDeCampoDao();
        actividadDeCampos = actividadDeCampoDao.findAll();
    }

    public List<ActividadDeCampo> getActividadDeCampos() { return actividadDeCampos;    }


    public void insertRest (ActividadDeCampo actividadDeCampo, Context context) {

        actividadDeCampoAPI.agregarActividadDeCampo(actividadDeCampo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Sesion.getInstancia().setActualizaActividadesOk(true);
                } else {
                    Sesion.getInstancia().setActualizaActividadesOk(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Hubo un problema al actualizar la actividad de campo\n El problema persiste contactese con el administrador.", Toast.LENGTH_SHORT).show();
                Sesion.getInstancia().setActualizaActividadesOk(false);
            }
        });
    }

    public void insertDao(ActividadDeCampo actividadDeCampo,Context context) {
        try {
            actividadDeCampoDao.insert(actividadDeCampo);
            Toast.makeText(context,"Se agreg?? la actividad de campo",Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"Fall?? en agregar actividad de campo",Toast.LENGTH_LONG).show();
        }

    }

    public void update(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.delete(actividadDeCampo);
    }

    public int count() { return actividadDeCampoDao.count();    }

    public ActividadDeCampo actividadDeCampoVieja() { return actividadDeCampoDao.actividadDeCampoVieja();}


}
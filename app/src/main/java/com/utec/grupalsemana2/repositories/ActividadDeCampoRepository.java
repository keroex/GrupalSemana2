package com.utec.grupalsemana2.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.presentacion.ListarActividadesDeCampo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadDeCampoRepository {
    private ActividadDeCampoDao actividadDeCampoDao;
    private static Retrofit retrofit;

    //private LiveData<List<ActividadDeCampo>> actividadDeCampos;
    private List<ActividadDeCampo> actividadDeCampos;
    private MutableLiveData<List<ActividadDeCampo>> actividadesDeCampoXUsuario = new MutableLiveData<>();

    public ActividadDeCampoRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        actividadDeCampoDao = db.actividadDeCampoDao();
        actividadDeCampos = actividadDeCampoDao.findAll();
    }

    //public LiveData<List<ActividadDeCampo>> getActividadDeCampos() { return actividadDeCampos;    }

    public List<ActividadDeCampo> getActividadDeCampos() {
        //si no tiene internet
        return actividadDeCampos;
    }

    public MutableLiveData<List<ActividadDeCampo>> getActividadesDeCampoXUsuario() {
        //si tiene internet
        actividadesDeCampoXUsuario.setValue(new ArrayList<>());
        Call<List<ActividadDeCampo>> call = crearActividadDeCampoApi().getActividadesDeCampo();
        call.enqueue(new Callback<List<ActividadDeCampo>>() {
            @Override
            public void onResponse(Call<List<ActividadDeCampo>> call, Response<List<ActividadDeCampo>> response) {
                if (response.isSuccessful()) {
                    List<ActividadDeCampo> actividades = response.body();
                    if(actividades!=null) {
                        actividadesDeCampoXUsuario.setValue(actividades);
                    } else {
                        System.out.println("*********************************************************************************No hay actividades de campo");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ActividadDeCampo>> call, Throwable t) {

            }
        });
        return actividadesDeCampoXUsuario;
    }

    public void insert (ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.insert(actividadDeCampo);
        //si tiene internet
        Call<ActividadDeCampo> call = crearActividadDeCampoApi().agregarActividadDeCampo(actividadDeCampo);
        call.enqueue(new Callback<ActividadDeCampo>() {
            @Override
            public void onResponse(Call<ActividadDeCampo> call, Response<ActividadDeCampo> response) {
                if(response.isSuccessful()) {
                    System.out.println("Se agregó la actividad de campo");
                    Log.i("Se agrego", "Se agrego la actividad de campo");
                }
            }

            @Override
            public void onFailure(Call<ActividadDeCampo> call, Throwable t) {
                System.out.println("Falló al agregar la actividad de campo");
            }
        });
    }

    public void update(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.delete(actividadDeCampo);
    }

    public int count() { return actividadDeCampoDao.count();    }


    //instanciando retrofit
    private static ActividadDeCampoAPI crearActividadDeCampoApi() {
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.10.2:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ActividadDeCampoAPI actividadDeCampoAPI = retrofit.create(ActividadDeCampoAPI.class);

            return actividadDeCampoAPI;
        } catch(Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }
}

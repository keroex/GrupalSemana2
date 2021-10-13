package com.utec.grupalsemana2.repositories;

import android.app.Application;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.interfaces.ActividadDeCampoAPI;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.presentacion.ListarActividadesDeCampo;
import com.utec.grupalsemana2.servicios.RestAppClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActividadDeCampoRepository {

    private ActividadDeCampoAPI actividadDeCampoAPI = RestAppClient.getClient().create(ActividadDeCampoAPI.class); // me traigo el cliente retrofit

    private ActividadDeCampoDao actividadDeCampoDao; // dao para base de datos local

    private List<ActividadDeCampo> actividadDeCampos; // lista de actividades de base de datos local

    private MutableLiveData<List<ActividadDeCampo>> actividadesDeCampoXUsuario = new MutableLiveData<>(); // lista de actividades del rest

    public MutableLiveData<List<ActividadDeCampo>> getActividadesDeCampoXUsuario() {
        return actividadesDeCampoXUsuario;
    }

    // constructor
    public ActividadDeCampoRepository(Application application) {
        try {
            AppDataBase db = AppDataBase.getInstance((application)); // instancio base de datos local
            actividadDeCampoDao = db.actividadDeCampoDao(); // instancio dao
            actividadDeCampos = actividadDeCampoDao.findAll(); // traigo la lista de la base de datos local
            loadActividadesDeCampoXUsuario(); // cargo el repositorio con las actividades de campo del rest
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<ActividadDeCampo> getActividadDeCampos() {
        //si no tiene internet
        return actividadDeCampos;
    }

    public void loadActividadesDeCampoXUsuario() {
        //si tiene internet
        actividadesDeCampoXUsuario.setValue(new ArrayList<>());
        Call<List<ActividadDeCampo>> call = actividadDeCampoAPI.getActividadesDeCampo();
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
    }

    public void insert (ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.insert(actividadDeCampo);  // inserto a la base de datos local
        //si tiene internet
        actividadDeCampoAPI.agregarActividadDeCampo(actividadDeCampo).enqueue(new Callback<ActividadDeCampo>() {
            @Override
            public void onResponse(Call<ActividadDeCampo> call, Response<ActividadDeCampo> response) {
                loadActividadesDeCampoXUsuario();
            }

            @Override
            public void onFailure(Call<ActividadDeCampo> call, Throwable t) {

            }
        });
    }

    /*public void update(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.update(actividadDeCampo);
    }

    public void delete(ActividadDeCampo actividadDeCampo) {
        actividadDeCampoDao.delete(actividadDeCampo);
    }
*/
    public int count() { return actividadDeCampoDao.count();    }

}

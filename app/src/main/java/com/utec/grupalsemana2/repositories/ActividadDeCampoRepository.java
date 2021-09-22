package com.utec.grupalsemana2.repositories;

import android.app.Application;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.logica.ActividadDeCampo;

import java.util.List;

public class ActividadDeCampoRepository {
    private ActividadDeCampoDao actividadDeCampoDao;

    //private LiveData<List<ActividadDeCampo>> actividadDeCampos;
    private List<ActividadDeCampo> actividadDeCampos;
    public ActividadDeCampoRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        actividadDeCampoDao = db.actividadDeCampoDao();
        actividadDeCampos = actividadDeCampoDao.findAll();
    }

    //public LiveData<List<ActividadDeCampo>> getActividadDeCampos() { return actividadDeCampos;    }

    public List<ActividadDeCampo> getActividadDeCampos() { return actividadDeCampos;   }

    public void insert (ActividadDeCampo actividadDeCampo) {
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

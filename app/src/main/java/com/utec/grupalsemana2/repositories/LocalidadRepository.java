package com.utec.grupalsemana2.repositories;

import android.app.Application;

import com.utec.grupalsemana2.dao.LocalidadDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.logica.LocalidadDTO;

import java.util.List;

public class LocalidadRepository {
    private LocalidadDao localidadDao;

    public LocalidadRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        localidadDao = db.localidadDao();
    }

    public void insert(LocalidadDTO localidadDTO) {
        localidadDao.insert(localidadDTO);
    }

    public void update(LocalidadDTO localidadDTO) {
        localidadDao.update(localidadDTO);
    }

    public void delete(LocalidadDTO localidadDTO) {
        localidadDao.delete(localidadDTO);
    }

    public int count() { return localidadDao.count();    }

    public List<LocalidadDTO> getLocalidades() { return localidadDao.findAll();    }

    public List<LocalidadDTO> getLocalidadesXDepartamento(long iddepartamento) {
        return localidadDao.findAllXDepartamento(iddepartamento);
    }
}

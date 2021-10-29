package com.utec.grupalsemana2.repositories;

import android.app.Application;

import com.utec.grupalsemana2.dao.DepartamentoDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.logica.DepartamentoDTO;

import java.util.List;

public class DepartamentoRepository {
    private DepartamentoDao departamentoDao;

    public DepartamentoRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        departamentoDao = db.departamentoDao();
    }

    public void insert(DepartamentoDTO departamentoDTO) {
        departamentoDao.insert(departamentoDTO);
    }

    public void update(DepartamentoDTO departamentoDTO) {
        departamentoDao.update(departamentoDTO);
    }

    public void delete(DepartamentoDTO departamentoDTO) {
        departamentoDao.delete(departamentoDTO);
        System.out.println("ELIMINO LA REGIOB");
    }

    public int count() { return departamentoDao.count();    }

    public List<DepartamentoDTO> getDepartamentoes() { return departamentoDao.findAll();    }

    public List<DepartamentoDTO> getDepartamentosXRegion(long idregion) {
        return departamentoDao.findDepartamentosXRegion(idregion);
    }
}

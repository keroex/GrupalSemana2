package com.utec.grupalsemana2.repositories;

import android.app.Application;

import com.utec.grupalsemana2.dao.RegionDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.logica.RegionDTO;

import java.util.List;

public class RegionRepository {

    private RegionDao regionDao;

    public RegionRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        regionDao = db.regionDao();
    }

    public void insert(RegionDTO regionDTO) {
        regionDao.insert(regionDTO);
    }

    public void update(RegionDTO regionDTO) {
        regionDao.update(regionDTO);
    }

    public void delete(RegionDTO regionDTO) {
        regionDao.delete(regionDTO);
        System.out.println("ELIMINO LA REGIOB");
    }

    public int count() { return regionDao.count();    }

    public List<RegionDTO> getRegiones() { return regionDao.findAll();    }
}

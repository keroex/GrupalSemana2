package com.utec.grupalsemana2.repositories;

import android.app.Application;

import com.utec.grupalsemana2.dao.FormularioDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.logica.FormularioDTO;

import java.util.List;

public class FormularioRepository {

    private FormularioDao formularioDao;

    public FormularioRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        formularioDao = db.formularioDao();
    }

    public void insert(FormularioDTO formularioDTO) {
        formularioDao.insert(formularioDTO);
    }

    public void update(FormularioDTO formularioDTO) {
        formularioDao.update(formularioDTO);
    }

    public void delete(FormularioDTO formularioDTO) {
        formularioDao.delete(formularioDTO);
    }

    public int count() { return formularioDao.count();    }

    public List<FormularioDTO> getFormularioes() { return formularioDao.findAll();    }
}

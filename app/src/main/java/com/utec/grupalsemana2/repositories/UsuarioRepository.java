package com.utec.grupalsemana2.repositories;

import android.app.Application;
import android.content.Context;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.dao.UsuarioDao;
import com.utec.grupalsemana2.database.AppDataBase;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.UsuarioDTO;

public class UsuarioRepository {

    private UsuarioDao usuarioDao;

    public UsuarioRepository(Application application) {
        AppDataBase db = AppDataBase.getInstance((application));
        usuarioDao = db.usuarioDao();
    }

    public void insert(UsuarioDTO usuarioDTO) {
        usuarioDao.insert(usuarioDTO);
    }

    public long login(UsuarioDTO usuarioDTO) {
        return usuarioDao.login(usuarioDTO.getNombreUsuario(), usuarioDTO.getContrasenia());
    }

    public void update(UsuarioDTO usuarioDTO) {
        usuarioDao.update(usuarioDTO);
    }

}
// si el usuario esta en la base de datos local, lo tomo de ahi, sino sigo con el paso 2

// 2 si el usuario existe en la base de datos del servidor, lo creo en la local.


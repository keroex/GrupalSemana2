package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.UsuarioDTO;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("Select * from UsuarioDTO")
    List<UsuarioDTO> findAll();

    @Insert
    void insert(UsuarioDTO usuarioDTO);

    @Update
    void update(UsuarioDTO usuarioDTO);

    @Delete
    void delete (UsuarioDTO usuarioDTO);

}

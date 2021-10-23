package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.UsuarioDTO;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Query("Select * from UsuarioDTO")
    List<UsuarioDTO> findAll();

    @Query("Select idusuario from UsuarioDTO where nombreUsuario = :nombreUsuario and contrasenia = :contrasenia")
    long login(String nombreUsuario, String contrasenia);

    @Insert
    void insert(UsuarioDTO usuarioDTO);

    @Update
    void update(UsuarioDTO usuarioDTO);

    @Delete
    void delete (UsuarioDTO usuarioDTO);

}

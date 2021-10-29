package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.FormularioDTO;

import java.util.List;

@Dao
public interface FormularioDao {

    @Query("Select * from FormularioDTO")
    List<FormularioDTO> findAll();

    @Insert
    void insert(FormularioDTO formularioDTO);

    @Update
    void update(FormularioDTO formularioDTO);

    @Delete
    void delete (FormularioDTO formularioDTO);

    @Query("Select * from FormularioDTO WHERE idformulario = :id")
    FormularioDTO findById(int id);

    @Query("Select count(*) from FormularioDTO")
    int count();
}

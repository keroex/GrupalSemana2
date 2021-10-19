package com.utec.grupalsemana2.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;

import java.util.List;

public interface LocalidadDao {

    @Query("Select * from LocalidadDTO")
    List<LocalidadDTO> findAll();

    @Insert
    void insert(LocalidadDTO localidadDTO);

}

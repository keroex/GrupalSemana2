package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.utec.grupalsemana2.logica.DepartamentoDTO;

import java.util.List;

@Dao
public interface DepartamentoDao {

    @Query("Select * from DepartamentoDTO")
    List<DepartamentoDTO> findAll();

    @Insert
    void insert(DepartamentoDTO departamentoDTO);

}

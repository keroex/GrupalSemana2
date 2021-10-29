package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.DepartamentoDTO;


import java.util.List;

@Dao
public interface DepartamentoDao {

    @Query("Select * from DepartamentoDTO")
    List<DepartamentoDTO> findAll();

    @Insert
    void insert(DepartamentoDTO departamentoDTO);
   
    @Delete
    void delete (DepartamentoDTO departamentoDTO);

    @Update
    void update(DepartamentoDTO departamentoDTO);

    @Query("Select count(*) from DepartamentoDTO")
    int count();

    @Query("Select * from DepartamentoDTO where idregion=:idregion")
    List<DepartamentoDTO> findDepartamentosXRegion(long idregion);

}

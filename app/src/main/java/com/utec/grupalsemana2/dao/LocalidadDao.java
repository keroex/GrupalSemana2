package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;

import java.util.List;
@Dao
public interface LocalidadDao {

    @Query("Select * from LocalidadDTO")
    List<LocalidadDTO> findAll();

    @Insert
    void insert(LocalidadDTO localidadDTO);

    @Delete
    void delete (LocalidadDTO localidadDTO);

    @Update
    void update(LocalidadDTO localidadDTO);

    @Query("Select count(*) from LocalidadDTO")
    int count();

    @Query("Select * from LocalidadDTO where iddepartamento=:iddepartamento")
    List<LocalidadDTO> findAllXDepartamento(long iddepartamento);
}

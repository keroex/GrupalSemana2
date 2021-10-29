package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.FormularioDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.RegionDTO;

import java.util.List;

@Dao
public interface RegionDao {


    @Insert
    void insert(RegionDTO regionDTO);

    @Delete
    void delete (RegionDTO regionDTO);

    @Update
    void update(RegionDTO regionDTO);

    @Query("Select * from RegionDTO")
    List<RegionDTO> findAll();

    @Query("Select count(*) from RegionDTO")
    int count();

}

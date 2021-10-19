package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.RegionDTO;

import java.util.List;

@Dao
public interface RegionDao {

    @Query("Select * from RegionDTO")
    List<RegionDTO> findAll();

    @Insert
    void insert(RegionDTO regionDTO);

}

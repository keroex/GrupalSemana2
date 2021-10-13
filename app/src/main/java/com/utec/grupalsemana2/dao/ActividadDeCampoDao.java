package com.utec.grupalsemana2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.utec.grupalsemana2.logica.ActividadDeCampo;

import java.util.List;

@Dao
public interface ActividadDeCampoDao {

        @Query("Select * from ActividadDeCampo")
            //LiveData<List<ActividadDeCampo>> findAll();
        List<ActividadDeCampo> findAll();

        @Insert
        void insert(ActividadDeCampo actividadDeCampo);

        @Update
        void update(ActividadDeCampo actividadDeCampo);

        @Delete
        void delete (ActividadDeCampo actividadDeCampo);

        @Query("Select * from ActividadDeCampo WHERE id = :id")
        ActividadDeCampo findById(int id);

        @Query("Select count(*) from ActividadDeCampo")
        int count();

}


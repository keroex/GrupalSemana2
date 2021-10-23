package com.utec.grupalsemana2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.utec.grupalsemana2.dao.ActividadDeCampoDao;
import com.utec.grupalsemana2.dao.UsuarioDao;
import com.utec.grupalsemana2.logica.ActividadDeCampo;
import com.utec.grupalsemana2.logica.DepartamentoDTO;
import com.utec.grupalsemana2.logica.FormularioDTO;
import com.utec.grupalsemana2.logica.LocalidadDTO;
import com.utec.grupalsemana2.logica.RegionDTO;
import com.utec.grupalsemana2.logica.UsuarioDTO;
import com.utec.grupalsemana2.servicios.Converters;

@Database(entities = {ActividadDeCampo.class, UsuarioDTO.class, DepartamentoDTO.class, FormularioDTO.class, LocalidadDTO.class, RegionDTO.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    public abstract ActividadDeCampoDao actividadDeCampoDao();

    public abstract UsuarioDao usuarioDao();

    private static volatile AppDataBase instance;

    public static AppDataBase getInstance(Context context) {
        if(instance==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "IAGRO")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}

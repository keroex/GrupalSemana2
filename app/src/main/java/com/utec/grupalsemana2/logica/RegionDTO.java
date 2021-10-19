package com.utec.grupalsemana2.logica;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RegionDTO {

    @PrimaryKey()
    @ColumnInfo(index = true, name = "idregion")
    private long idregion;
    @ColumnInfo(name = "nombre")
    private String nombre;

    public long getIdregion() {
        return idregion;
    }
    public void setIdregion(long idregion) {
        this.idregion = idregion;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public RegionDTO(long idregion, String nombre) {
        super();
        this.idregion = idregion;
        this.nombre = nombre;
    }

    public RegionDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return nombre;
    }
}
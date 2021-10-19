package com.utec.grupalsemana2.logica;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class LocalidadDTO {

    @PrimaryKey()
    @ColumnInfo(index = true, name = "idlocalidad")
    private long idlocalidad;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "iddepartamento")
    private long iddepartamento;
    @ColumnInfo(name = "nombreDepartamento")
    private String nombreDepartamento;

    public long getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(long iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public long getIdlocalidad() {
        return idlocalidad;
    }
    public void setIdlocalidad(long idlocalidad) {
        this.idlocalidad = idlocalidad;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalidadDTO(long idlocalidad, String nombre) {
        super();
        this.idlocalidad = idlocalidad;
        this.nombre = nombre;
    }

    public LocalidadDTO() {
        super();
    }

    @Override
    public String toString() {
        return nombre;
    }
}


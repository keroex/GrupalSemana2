package com.utec.grupalsemana2.logica;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DepartamentoDTO {

    @PrimaryKey()
    @ColumnInfo(index = true, name = "iddepartamento")
    private long iddepartamento;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "idregion")
    private long idregion;
    @ColumnInfo(name = "nombreRegion")
    private String nombreRegion;

    public long getIdregion() {
        return idregion;
    }

    public void setIdregion(long idregion) {
        this.idregion = idregion;
    }

    public String getNombreRegion() {
        return nombreRegion;
    }

    public void setNombreRegion(String nombreRegion) {
        this.nombreRegion = nombreRegion;
    }

    public long getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(long iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DepartamentoDTO(long iddepartamento, String nombre) {
        super();
        this.iddepartamento = iddepartamento;
        this.nombre = nombre;
    }

    public DepartamentoDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return nombre;
    }

}

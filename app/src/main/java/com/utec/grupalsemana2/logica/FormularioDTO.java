package com.utec.grupalsemana2.logica;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class FormularioDTO {

    @PrimaryKey()
    @ColumnInfo(index = true, name = "idformulario")
    private long idformulario;
    @ColumnInfo(name = "descripcion")
    private String descripcion;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "usuario")
    private String usuario;
    @ColumnInfo(name = "idusuario")
    private long idusuario;
    @ColumnInfo(name = "fechaCreacion")
    private Date fechaCreacion;


    public FormularioDTO() {
    }

    public FormularioDTO(String descripcion,String nombre,
                         String usuario, long idusuario, Date fechaCreacion, long idformulario) {
        super();
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.usuario = usuario;
        this.idusuario = idusuario;
        this.fechaCreacion = fechaCreacion;
        this.idformulario = idformulario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getIdformulario() {
        return idformulario;
    }

    public void setIdformulario(long idformulario) {
        this.idformulario = idformulario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }

    public String toString() {
        return nombre;
    }

}
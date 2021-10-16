package com.utec.grupalsemana2.logica;

import java.util.Date;

public class FormularioDTO {


    private String descripcion;
    private String nombre;
    private String usuario;
    private long idusuario;
    private Date fechaCreacion;
    private long idformulario;

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
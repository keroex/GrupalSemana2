package com.utec.grupalsemana2.logica;

public class LocalidadDTO {

    private long idlocalidad;
    private String nombre;

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


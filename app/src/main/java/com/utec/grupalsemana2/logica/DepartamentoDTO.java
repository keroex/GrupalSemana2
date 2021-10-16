package com.utec.grupalsemana2.logica;

public class DepartamentoDTO {

    private long iddepartamento;
    private String nombre;

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

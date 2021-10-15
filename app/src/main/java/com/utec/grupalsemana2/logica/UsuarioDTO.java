package com.utec.grupalsemana2.logica;

public class UsuarioDTO {

    private long idUsuario;
    private String apellido;
    private String cedula;
    private String contrasenia;
    private String email;
    private String instituto;
    private String profesion;
    private String nombre;
    private String nombreUsuario;
    private String rol;

    private long idRol;

    public long getIdUsuario() {
        return idUsuario;
    }


    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituto() {
        return instituto;
    }

    public void setInstituto(String instituto) {
        this.instituto = instituto;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    public UsuarioDTO(long idusuario, String apellido, String cedula, String contrasenia, String email,
                      String instituto, String profesion, String nombre, String nombreUsuario, String rol, long idRol) {
        super();
        this.idUsuario = idusuario;
        this.apellido = apellido;
        this.cedula = cedula;
        this.contrasenia = contrasenia;
        this.email = email;
        this.instituto = instituto;
        this.profesion = profesion;
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.idRol = idRol;
    }

    public UsuarioDTO() {
        super();
    }

}
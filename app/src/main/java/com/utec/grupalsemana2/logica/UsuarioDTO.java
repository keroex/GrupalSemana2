package com.utec.grupalsemana2.logica;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UsuarioDTO {

    @PrimaryKey()
    @ColumnInfo(index = true, name = "idusuario")
    private long idUsuario;
    @ColumnInfo(name = "apellido")
    private String apellido;
    @ColumnInfo(name = "cedula")
    private String cedula;
    @ColumnInfo(name = "contrasenia")
    private String contrasenia;
    @ColumnInfo(name = "email")
    private String email;
    private String instituto;
    private String profesion;
    @ColumnInfo(name = "nombre")
    private String nombre;
    @ColumnInfo(name = "nombreUsuario")
    private String nombreUsuario;
    @ColumnInfo(name = "rol")
    private String rol;
    @ColumnInfo(name = "idrol")
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
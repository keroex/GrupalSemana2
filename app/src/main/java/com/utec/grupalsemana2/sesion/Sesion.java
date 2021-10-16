package com.utec.grupalsemana2.sesion;

import com.utec.grupalsemana2.logica.UsuarioDTO;

public class Sesion {

    private static Sesion instancia;
    private static UsuarioDTO usuarioLogueado;

    public static Sesion getInstancia() {
        if(instancia==null) {
            instancia = new Sesion();
            usuarioLogueado = new UsuarioDTO();
        }
        return instancia;
    }

    public static UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public static void setUsuarioLogueado(UsuarioDTO usuarioLogueado) {
        Sesion.usuarioLogueado = usuarioLogueado;
    }

    private Sesion() {
    }

}

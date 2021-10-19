package com.utec.grupalsemana2.sesion;

import com.utec.grupalsemana2.logica.UsuarioDTO;

public class Sesion {

    private static Sesion instancia;
    private static UsuarioDTO usuarioLogueado;
    private static boolean hayInternet;
    private static boolean hayRest;

    public static Sesion getInstancia() {
        if(instancia==null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public static UsuarioDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public static void setUsuarioLogueado(UsuarioDTO usuarioLogueado) {
        Sesion.usuarioLogueado = usuarioLogueado;
    }

    public static boolean isHayInternet() {
        return hayInternet;
    }

    public static void setHayInternet(boolean hayInternet) {
        Sesion.hayInternet = hayInternet;
    }

    public static boolean isHayRest() {
        return hayRest;
    }

    public static void setHayRest(boolean hayRest) {
        Sesion.hayRest = hayRest;
    }

    private Sesion() {
    }

}

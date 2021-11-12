package com.utec.grupalsemana2.sesion;

import com.utec.grupalsemana2.logica.UsuarioDTO;

public class Sesion {

    private static Sesion instancia;
    private static UsuarioDTO usuarioLogueado;
    private static boolean hayInternet=false;
    private static boolean hayRest=false;
    private static boolean huboPerdidaDeConexion = false;
    private static boolean volvioLaConexionDep = false;
    private static boolean volvioLaConexionform = false;
    private static boolean volvioLaConexionReg = false;
    private static boolean volvioLaConexionLoc = false;
    private static boolean actualizaActividadesOk = false;
    private static boolean hayQueRecargar = false;


    public static boolean isActualizaActividadesOk() {
        return actualizaActividadesOk;
    }

    public static void setActualizaActividadesOk(boolean actualizaActividadesOk) {
        Sesion.actualizaActividadesOk = actualizaActividadesOk;
    }

    public static boolean isHayQueRecargar() {
        return hayQueRecargar;
    }

    public static void setHayQueRecargar(boolean hayQueRecargar) {
        Sesion.hayQueRecargar = hayQueRecargar;
    }

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

    public static boolean isHuboPerdidaDeConexion() {
        return huboPerdidaDeConexion;
    }

    public static void setHuboPerdidaDeConexion(boolean huboPerdidaDeConexion) {
        Sesion.huboPerdidaDeConexion = huboPerdidaDeConexion;
    }

    public static boolean isVolvioLaConexionDep() {
        return volvioLaConexionDep;
    }

    public static void setVolvioLaConexionDep(boolean volvioLaConexionDep) {
        Sesion.volvioLaConexionDep = volvioLaConexionDep;
    }

    public static boolean isVolvioLaConexionform() {
        return volvioLaConexionform;
    }

    public static void setVolvioLaConexionform(boolean volvioLaConexionform) {
        Sesion.volvioLaConexionform = volvioLaConexionform;
    }

    public static boolean isVolvioLaConexionReg() {
        return volvioLaConexionReg;
    }

    public static void setVolvioLaConexionReg(boolean volvioLaConexionReg) {
        Sesion.volvioLaConexionReg = volvioLaConexionReg;
    }

    public static boolean isVolvioLaConexionLoc() {
        return volvioLaConexionLoc;
    }

    public static void setVolvioLaConexionLoc(boolean volvioLaConexionLoc) {
        Sesion.volvioLaConexionLoc = volvioLaConexionLoc;
    }

    private Sesion() {
    }

}

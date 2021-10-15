package com.utec.grupalsemana2.logica;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class ActividadDeCampo implements Parcelable {

    //Atributos
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "id")
    private long idactividadDeCampo;
    @ColumnInfo(name = "equipamiento")
    private String equipamiento;
    @ColumnInfo(name = "estacionDeMuestreo")
    private String estacionDeMuestreo;
    @ColumnInfo(name = "fecha")
    private Date fecha;
    @ColumnInfo(name = "geopunto")
    private String geopunto;
    @ColumnInfo(name = "metodoDeMuestreo")
    private String metodoDeMuestreo;
    @ColumnInfo(name = "resumen")
    private String resumen;
    @ColumnInfo(name = "zona")
    private String zona;
    @ColumnInfo(name = "region")
    private String region;
    @ColumnInfo(name = "localidad")
    private String localidad;
    private long idlocalidad;
    @ColumnInfo(name = "usuario")
    private String usuario;
    private long idusuario;
    @ColumnInfo(name = "departamento")
    private String departamento;
    private long iddepartamento;
    @ColumnInfo(name = "formulario")
    private String formulario;
    private long idformulario;
    private String tipoDeMuestreo;

    //Constructores
    public ActividadDeCampo() {
        super();
    }

    public ActividadDeCampo(long idactividadDeCampo, String equipamiento, String estacionDeMuestreo, Date fecha,
                            String geopunto, String metodoDeMuestreo, String resumen, String zona, String region, String localidad, String usuario,
                            String departamento, String formulario) {
        super();
        this.idactividadDeCampo = idactividadDeCampo;
        this.equipamiento = equipamiento;
        this.estacionDeMuestreo = estacionDeMuestreo;
        this.fecha = fecha;
        this.geopunto = geopunto;
        this.metodoDeMuestreo = metodoDeMuestreo;
        this.resumen = resumen;
        this.zona = zona;
        this.region = region;
        this.localidad = localidad;
        this.usuario = usuario;
        this.departamento = departamento;
        this.formulario = formulario;
    }

    // Metodos
    protected ActividadDeCampo(Parcel in) {
        idactividadDeCampo = in.readLong();
        equipamiento = in.readString();
        estacionDeMuestreo = in.readString();
        fecha = new Date(in.readLong());
        geopunto = in.readString();
        metodoDeMuestreo = in.readString();
        resumen = in.readString();
        zona = in.readString();
        region = in.readString();
        localidad = in.readString();
        usuario = in.readString();
        departamento = in.readString();
        formulario = in.readString();
    }

    public static final Creator<ActividadDeCampo> CREATOR = new Creator<ActividadDeCampo>() {
        @Override
        public ActividadDeCampo createFromParcel(Parcel in) {
            return new ActividadDeCampo(in);
        }

        @Override
        public ActividadDeCampo[] newArray(int size) {
            return new ActividadDeCampo[size];
        }
    };

    public long getIdactividadDeCampo() {
        return this.idactividadDeCampo;
    }

    public void setIdactividadDeCampo(long idactividadDeCampo) {
        this.idactividadDeCampo = idactividadDeCampo;
    }

    public String getEquipamiento() {
        return this.equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public String getEstacionDeMuestreo() {
        return this.estacionDeMuestreo;
    }

    public void setEstacionDeMuestreo(String estacionDeMuestreo) {
        this.estacionDeMuestreo = estacionDeMuestreo;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getGeopunto() {
        return this.geopunto;
    }

    public void setGeopunto(String geopunto) {
        this.geopunto = geopunto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMetodoDeMuestreo() {
        return this.metodoDeMuestreo;
    }

    public void setMetodoDeMuestreo(String metodoDeMuestreo) {
        this.metodoDeMuestreo = metodoDeMuestreo;
    }

    public String getResumen() {
        return this.resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getZona() {
        return this.zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFormulario() {
        return this.formulario;
    }

    public void setFormulario(String formulario) {
        this.formulario = formulario;
    }

    public long getIdlocalidad() {
        return idlocalidad;
    }

    public void setIdlocalidad(long idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    public long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(long idusuario) {
        this.idusuario = idusuario;
    }

    public long getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(long iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public long getIdformulario() {
        return idformulario;
    }

    public void setIdformulario(long idformulario) {
        this.idformulario = idformulario;
    }

    public String getTipoDeMuestreo() {
        return tipoDeMuestreo;
    }

    public void setTipoDeMuestreo(String tipoDeMuestreo) {
        this.tipoDeMuestreo = tipoDeMuestreo;
    }

    @Override
    public String toString() {
        return "ActividadDeCampo{" +
                "idactividadDeCampo=" + idactividadDeCampo +
                ", equipamiento='" + equipamiento + '\'' +
                ", estacionDeMuestreo='" + estacionDeMuestreo + '\'' +
                ", fecha='" + fecha.toString() + '\'' +
                ", geopunto='" + geopunto + '\'' +
                ", metodoDeMuestreo='" + metodoDeMuestreo + '\'' +
                ", resumen='" + resumen + '\'' +
                ", zona='" + zona + '\'' +
                ", region='" + region + '\'' +
                ", localidad='" + localidad + '\'' +
                ", usuario='" + usuario + '\'' +
                ", departamento='" + departamento + '\'' +
                ", formulario='" + formulario + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(idactividadDeCampo);
        parcel.writeString(equipamiento);
        parcel.writeString(estacionDeMuestreo);
        parcel.writeLong(fecha.getTime());
        parcel.writeString(geopunto);
        parcel.writeString(metodoDeMuestreo);
        parcel.writeString(resumen);
        parcel.writeString(zona);
        parcel.writeString(region);
        parcel.writeString(localidad);
        parcel.writeString(usuario);
        parcel.writeString(departamento);
        parcel.writeString(formulario);
    }
}
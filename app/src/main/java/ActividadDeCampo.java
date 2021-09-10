public class ActividadDeCampo {

    //Atributos

    private long idactividadDeCampo;
    private String equipamiento;
    private String estacionDeMuestreo;
    private String fecha;
    private String geopunto;
    private String metodoDeMuestreo;
    private String resumen;
    private String zona;
    private String localidad;
    private String usuario;
    private String departamento;
    private String formulario;


    //Constructores

    public ActividadDeCampo() {
        super();
    }

    public ActividadDeCampo(long idactividadDeCampo, String equipamiento, String estacionDeMuestreo, String fecha,
                            String geopunto, String metodoDeMuestreo, String resumen, String zona, String localidad, String usuario,
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
        this.localidad = localidad;
        this.usuario = usuario;
        this.departamento = departamento;
        this.formulario = formulario;
    }

    // Metodos

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

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
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

    @Override
    public String toString() {
        return resumen;
    }

}
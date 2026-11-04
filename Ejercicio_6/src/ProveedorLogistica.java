public class ProveedorLogistica extends IntegracionAPI implements Documentable, Auditable {
    private final double costoBase;
    public ProveedorLogistica(String identificador, String nombreProveedor,
            String categoria, String parametrosConfiguracion, double latenciaPromedio,
            boolean conexionActiva, double costoBase) {
        super(identificador, nombreProveedor, categoria,parametrosConfiguracion,latenciaPromedio,
                conexionActiva);
        this.costoBase = costoBase;
    }
    public double cotizarEnvio(double pesoKg, String destino) {
        if (!esConexionActiva() || pesoKg <= 0 || destino == null|| destino.trim().isEmpty()) {
            return -1;
        }
        return costoBase * pesoKg;
    }

    public String consultarEstadoEnvio(String codigoSeguimiento) {
        if (!esConexionActiva() || codigoSeguimiento == null || codigoSeguimiento.trim().isEmpty()) {
            return "No fue posible consultar el envío.";
        }
        return "Paquete en tránsito";
    }
    @Override
    public String generarDocumento(String datosEnvio) {
        if (!esConexionActiva()) {
            return "No fue posible generar la guía de envío.";
        }
        return "Guía de envío: " + datosEnvio;
    }
    @Override
    public String registrarAuditoria(String operacion) {
        return "Logística " + getNombreProveedor() + ": " + operacion;
    }
    @Override
    public String toString() {
        return super.toString()
                + ", costo base por kilogramo: " + costoBase;
    }
}
public class CertificadorFEL extends IntegracionAPI implements Documentable, Auditable {
    private final String nitEmisor;
    public CertificadorFEL(String identificador,String nombreProveedor,String categoria,
            String parametrosConfiguracion,double latenciaPromedio,boolean conexionActiva,
            String nitEmisor) {
        super(identificador,nombreProveedor,categoria,
                parametrosConfiguracion,latenciaPromedio,conexionActiva);
        this.nitEmisor = nitEmisor;
    }
    public String getNitEmisor() {
        return nitEmisor;
    }
    @Override
    public String generarDocumento(String datosFactura) {
        if (!esConexionActiva()|| datosFactura == null || datosFactura.trim().isEmpty()) {
            return "";
        }
        return "<FacturaFEL nitEmisor=\"" + nitEmisor + "\">" + datosFactura+ "</FacturaFEL>";
    }
    public boolean validarDocumento(String xml) {
        return esConexionActiva() && xml != null && xml.contains("<FacturaFEL") && xml.contains("</FacturaFEL>");
    }
    @Override
    public String registrarAuditoria(String operacion) {
        return "Certificador FEL " + getNombreProveedor() + ": " + operacion;
    }
    @Override
    public String toString() {
        return super.toString() + ", NIT emisor: " + nitEmisor;
    }
}

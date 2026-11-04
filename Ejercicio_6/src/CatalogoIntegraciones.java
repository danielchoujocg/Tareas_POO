import java.util.ArrayList;
import java.util.Collections;
public class CatalogoIntegraciones {
    private final ArrayList<IntegracionAPI> integraciones;
    public CatalogoIntegraciones() {
        integraciones = new ArrayList<>();
    }
    public void cargarIntegracionesIniciales() {
        agregarIntegracion(new PasarelaPago("PAG-01", "PagoTarjeta", "Pagos","moneda=GTQ; metodo=tarjeta", 120, true, 2.5));
        agregarIntegracion(new PasarelaPago("PAG-02", "TransferenciaGT", "Pagos","moneda=GTQ; metodo=transferencia", 180, true, 1.0));
        agregarIntegracion(new PasarelaPago("PAG-03", "BilleteraDigital", "Pagos","moneda=GTQ; metodo=billetera", 95, true, 2.0));
        agregarIntegracion(new PasarelaPago("PAG-04", "PagoExpress", "Pagos","moneda=GTQ; metodo=mixto", 210, false, 1.5));
        agregarIntegracion(new ProveedorLogistica("LOG-01", "EnviosGT", "Logistica","zona=nacional; unidad=kg", 250, true, 3.5));
        agregarIntegracion(new ProveedorLogistica("LOG-02", "RutaRapida", "Logistica","zona=urbana; unidad=kg", 160, true, 2.8));
        agregarIntegracion(new ProveedorLogistica("LOG-03", "CargaSegura", "Logistica","zona=nacional; unidad=kg", 320, false, 4.0));
        agregarIntegracion(new CertificadorFEL("FEL-01", "CertiFEL", "FEL","pais=GT; formato=XML", 140, true, "1234567-8"));
        agregarIntegracion(new CertificadorFEL("FEL-02", "FacturaGT", "FEL","pais=GT; formato=XML", 175, true, "2345678-9"));
        agregarIntegracion(new CertificadorFEL("FEL-03", "FELCloud", "FEL","pais=GT; formato=XML", 230, true, "3456789-0"));
    }
    public void agregarIntegracion(IntegracionAPI integracion) {
        if (integracion == null) {
            return;
        }
        if (buscarIntegracion(integracion.getIdentificador()) == null) {
            integraciones.add(integracion);
        }
    }
    public IntegracionAPI buscarIntegracion(String id) {
        if (id == null) {
            return null;
        }
        for (IntegracionAPI integracion : integraciones) {
            if (integracion.getIdentificador().equalsIgnoreCase(id)) {
                return integracion;
            }
        }
        return null;
    }
    public String listarIntegracionesActivas() {
        StringBuilder listado = new StringBuilder();
        for (IntegracionAPI integracion : integraciones) {
            if (integracion.esConexionActiva()) {
                listado.append(integracion).append("\n");
            }
        }
        if (listado.length() == 0) {
            return "No hay integraciones activas.";
        }
        return listado.toString();
    }
    public void ordenarPorLatencia() {
        Collections.sort(integraciones);
    }
    public boolean procesarCobro(Transaccional servicio,double monto,String referencia) {
        return servicio != null && servicio.cobrar(monto, referencia);
    }
    public boolean procesarReintegro(
            IntegracionAPI integracion,
            double monto,
            String referencia) {
        if (!(integracion instanceof Transaccional)) {
            return false;
        }
        return procesarReintegro((Transaccional) integracion,monto,referencia);
    }
    public boolean procesarReintegro(Transaccional servicio, double monto, String referencia) {
        return servicio != null && servicio.reintegrar(monto, referencia);
    }
    public String generarDocumento(
            Documentable servicio,
            String datos) {
        if (servicio == null) {
            return "La integración no genera documentos.";
        }
        return servicio.generarDocumento(datos);
    }
    public String registrarAuditoria(Auditable servicio,String operacion) {
        if (servicio == null) {
            return "La integración no admite auditoría.";
        }
        return servicio.registrarAuditoria(operacion);
    }
    public String listarIntegraciones() {
        StringBuilder listado = new StringBuilder();
        for (IntegracionAPI integracion : integraciones) {
            listado.append(integracion).append("\n");
        }
        if (listado.length() == 0) {
            return "No hay integraciones registradas.";
        }
        return listado.toString();
    }
}
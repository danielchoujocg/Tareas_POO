public class PasarelaPago extends IntegracionAPI implements Transaccional, Auditable {        
    private final double comisionPorcentaje;
    public PasarelaPago(String identificador,String nombreProveedor,String categoria,
            String parametrosConfiguracion,double latenciaPromedio,boolean conexionActiva,
            double comisionPorcentaje) {
        super(identificador,nombreProveedor,categoria,parametrosConfiguracion,latenciaPromedio,conexionActiva);
        this.comisionPorcentaje = comisionPorcentaje;
    }
    public double getComisionPorcentaje() {
        return comisionPorcentaje;
    }
    public double calcularComision(double monto) {
        return monto * comisionPorcentaje / 100;
    }
    @Override
    public boolean cobrar(double monto, String referencia) {
        return esOperacionValida(monto, referencia);
    }
    @Override
    public boolean reintegrar(double monto, String referencia) {
        return esOperacionValida(monto, referencia);
    }
    @Override
    public String registrarAuditoria(String operacion) {
        return "Pasarela " + getNombreProveedor() + ": " + operacion;
    }
    private boolean esOperacionValida(double monto, String referencia) {
        return esConexionActiva() && monto > 0 && referencia != null&& !referencia.trim().isEmpty();
    }
    @Override
    public String toString() {
        return super.toString() + ", comisión: " + comisionPorcentaje + "%";
    }
}
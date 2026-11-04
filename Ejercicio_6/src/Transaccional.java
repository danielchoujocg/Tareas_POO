public interface Transaccional {

    boolean cobrar(double monto, String referencia);

    boolean reintegrar(double monto, String referencia);
}
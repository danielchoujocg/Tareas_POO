import java.time.LocalDate;
public interface ICalendario extends Comparable<ICalendario> {
    int getId();
    LocalDate getFecha();
    int getHora();
    String getSede();
    boolean estaProgramado();
    String getDescripcion();
    void programar(LocalDate fecha, int hora, String sede);
    void reprogramar(LocalDate fecha, int hora, String sede);
    @Override
    default int compareTo(ICalendario otro) {
        if (!estaProgramado() || !otro.estaProgramado()) {
            throw new IllegalStateException("Solo se pueden ordenar eventos con programación.");
        }
        int comparacion = getFecha().compareTo(otro.getFecha());
        if (comparacion != 0) {
            return comparacion;
        }
        comparacion = Integer.compare(getHora(), otro.getHora());
        if (comparacion != 0) {
            return comparacion;
        }
        return getSede().compareTo(otro.getSede());
    }
}
import java.util.List;
import java.util.Objects;

public class ControladorCopa {
    private final Copa copa;

    public ControladorCopa(Copa copa) {
        this.copa = Objects.requireNonNull(copa, "La copa no puede ser nula.");
    }

    public Equipo agregarEquipo(String codigo, String nombre, String empresa) {
        return copa.agregarEquipo(codigo, nombre, empresa);
    }

    public void eliminarEquipo(String codigo) {
        copa.eliminarEquipo(codigo);
    }

    public void agregarSede(String nombreSede) {
        copa.agregarSede(nombreSede);
    }

    public void eliminarSede(String nombreSede) {
        copa.eliminarSede(nombreSede);
    }

    public List<String> listarSedes() {
        return copa.listarSedes();
    }

    public List<Equipo> obtenerTablaPosiciones() {
        return copa.obtenerTablaPosiciones();
    }

    public List<ICalendario> obtenerCalendario() {
        return copa.obtenerCalendario();
    }
}

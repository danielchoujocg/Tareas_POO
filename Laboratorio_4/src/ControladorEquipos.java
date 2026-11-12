import java.util.List;
import java.util.Objects;

public class ControladorEquipos {
    private final Copa copa;

    public ControladorEquipos(Copa copa) {
        this.copa = Objects.requireNonNull(copa, "La copa no puede ser nula.");
    }

    public Equipo buscarEquipo(String codigo) {
        return copa.buscarEquipo(codigo);
    }

    public List<Equipo> listarEquipos() {
        return copa.listarEquipos();
    }

    public int consultarPuntos(String codigo) {
        return copa.buscarEquipo(codigo).getPuntos();
    }
}

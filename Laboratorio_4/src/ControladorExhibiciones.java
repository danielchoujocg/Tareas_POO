import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ControladorExhibiciones {
    private final Copa copa;

    public ControladorExhibiciones(Copa copa) {
        this.copa = Objects.requireNonNull(copa, "La copa no puede ser nula.");
    }

    public Exhibicion crearExhibicion(String titulo) {
        return copa.crearExhibicion(titulo);
    }

    public Exhibicion buscarExhibicion(int idExhibicion) {
        return copa.buscarExhibicion(idExhibicion);
    }

    public List<Exhibicion> listarExhibiciones() {
        return copa.listarExhibiciones();
    }

    public void programarExhibicion(int idExhibicion, LocalDate fecha, int hora, String sede) {
        copa.buscarExhibicion(idExhibicion);
        copa.programarEvento(idExhibicion, fecha, hora, sede);
    }

    public void reprogramarExhibicion(int idExhibicion, LocalDate nuevaFecha,
                                     int nuevaHora, String nuevaSede) {
        copa.buscarExhibicion(idExhibicion);
        copa.reprogramarEvento(idExhibicion, nuevaFecha, nuevaHora, nuevaSede);
    }

    public void eliminarExhibicion(int idExhibicion) {
        copa.eliminarExhibicion(idExhibicion);
    }
}

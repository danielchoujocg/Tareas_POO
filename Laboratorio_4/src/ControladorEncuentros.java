import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ControladorEncuentros {
    private final Copa copa;

    public ControladorEncuentros(Copa copa) {
        this.copa = Objects.requireNonNull(copa, "La copa no puede ser nula.");
    }

    public FutSal crearFutSal(String codigoEquipo1, String codigoEquipo2) {
        return copa.crearFutSal(codigoEquipo1, codigoEquipo2);
    }

    public Baloncesto crearBaloncesto(String codigoEquipo1, String codigoEquipo2) {
        return copa.crearBaloncesto(codigoEquipo1, codigoEquipo2);
    }

    public Encuentros buscarEncuentro(int idEncuentro) {
        return copa.buscarEncuentro(idEncuentro);
    }

    public List<Encuentros> listarEncuentros() {
        return copa.listarEncuentros();
    }

    public void programarEncuentro(int idEncuentro, LocalDate fecha, int hora, String sede) {
        copa.buscarEncuentro(idEncuentro);
        copa.programarEvento(idEncuentro, fecha, hora, sede);
    }

    public void reprogramarEncuentro(int idEncuentro, LocalDate nuevaFecha,
                                    int nuevaHora, String nuevaSede) {
        copa.buscarEncuentro(idEncuentro);
        copa.reprogramarEvento(idEncuentro, nuevaFecha, nuevaHora, nuevaSede);
    }

    public void registrarResultado(int idEncuentro, int marcadorE1, int marcadorE2) {
        copa.buscarEncuentro(idEncuentro).registrarResultado(marcadorE1, marcadorE2);
    }

    public void eliminarEncuentro(int idEncuentro) {
        copa.eliminarEncuentro(idEncuentro);
    }
}

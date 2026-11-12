import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Copa implements Iterable<ICalendario> {
    private final String nombre;
    private final List<Equipo> equipos;
    private final List<ICalendario> eventos;
    private final Set<String> sedes;
    private int siguienteIdEvento;
    public Copa(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la copa no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
        this.equipos = new ArrayList<>();
        this.eventos = new ArrayList<>();
        this.sedes = new LinkedHashSet<>();
        this.siguienteIdEvento = 1;
    }

    public Equipo agregarEquipo(
            String codigo, String nombre, String empresa) {

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código no puede estar vacío."
            );
        }

        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                throw new IllegalArgumentException(
                        "Ya existe un equipo con ese código."
                );
            }
        }

        Equipo equipo = new Equipo(codigo, nombre, empresa);
        equipos.add(equipo);
        return equipo;
    }

    public Equipo buscarEquipo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe indicar el código del equipo."
            );
        }

        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return equipo;
            }
        }

        throw new IllegalArgumentException(
                "No se encontró el equipo: " + codigo
        );
    }

    public List<Equipo> listarEquipos() {
        return Collections.unmodifiableList(new ArrayList<>(equipos));
    }

    public void eliminarEquipo(String codigo) {
        Equipo equipo = buscarEquipo(codigo);

        for (ICalendario evento : eventos) {
            if (evento instanceof Encuentros) {
                Encuentros encuentro = (Encuentros) evento;

                if (encuentro.getEquipo1() == equipo
                        || encuentro.getEquipo2() == equipo) {
                    throw new IllegalStateException(
                            "El equipo participa en un encuentro registrado."
                    );
                }
            }
        }

        equipos.remove(equipo);
    }

    public void agregarSede(String nombreSede) {
        if (nombreSede == null || nombreSede.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la sede no puede estar vacío."
            );
        }

        String sede = nombreSede.trim();

        for (String registrada : sedes) {
            if (registrada.equalsIgnoreCase(sede)) {
                throw new IllegalArgumentException(
                        "La sede ya está registrada."
                );
            }
        }

        sedes.add(sede);
    }

    public void eliminarSede(String nombreSede) {
        String sede = buscarSedeRegistrada(nombreSede);

        for (ICalendario evento : eventos) {
            if (evento.estaProgramado()
                    && evento.getSede().equalsIgnoreCase(sede)) {
                throw new IllegalStateException(
                        "La sede está asignada a un evento."
                );
            }
        }

        sedes.remove(sede);
    }

    public List<String> listarSedes() {
        return Collections.unmodifiableList(new ArrayList<>(sedes));
    }

    public FutSal crearFutSal(
            String codigoEquipo1, String codigoEquipo2) {

        Equipo equipo1 = buscarEquipo(codigoEquipo1);
        Equipo equipo2 = buscarEquipo(codigoEquipo2);

        FutSal encuentro = new FutSal(
                generarIdEvento(), equipo1, equipo2
        );

        eventos.add(encuentro);
        return encuentro;
    }

    public Baloncesto crearBaloncesto(
            String codigoEquipo1, String codigoEquipo2) {

        Equipo equipo1 = buscarEquipo(codigoEquipo1);
        Equipo equipo2 = buscarEquipo(codigoEquipo2);

        Baloncesto encuentro = new Baloncesto(
                generarIdEvento(), equipo1, equipo2
        );

        eventos.add(encuentro);
        return encuentro;
    }

    public Exhibicion crearExhibicion(String titulo) {
        Exhibicion exhibicion = new Exhibicion(
                generarIdEvento(), titulo
        );

        eventos.add(exhibicion);
        return exhibicion;
    }

    public Encuentros buscarEncuentro(int id) {
        ICalendario evento = buscarEvento(id);

        if (!(evento instanceof Encuentros)) {
            throw new IllegalArgumentException(
                    "El identificador no corresponde a un encuentro."
            );
        }

        return (Encuentros) evento;
    }

    public Exhibicion buscarExhibicion(int id) {
        ICalendario evento = buscarEvento(id);

        if (!(evento instanceof Exhibicion)) {
            throw new IllegalArgumentException(
                    "El identificador no corresponde a una exhibición."
            );
        }

        return (Exhibicion) evento;
    }

    public List<Encuentros> listarEncuentros() {
        List<Encuentros> encuentros = new ArrayList<>();

        for (ICalendario evento : eventos) {
            if (evento instanceof Encuentros) {
                encuentros.add((Encuentros) evento);
            }
        }

        return Collections.unmodifiableList(encuentros);
    }

    public List<Exhibicion> listarExhibiciones() {
        List<Exhibicion> exhibiciones = new ArrayList<>();

        for (ICalendario evento : eventos) {
            if (evento instanceof Exhibicion) {
                exhibiciones.add((Exhibicion) evento);
            }
        }

        return Collections.unmodifiableList(exhibiciones);
    }

    public void programarEvento(
            int id, LocalDate fecha, int hora, String sede) {

        ICalendario evento = buscarEvento(id);
        String sedeRegistrada = buscarSedeRegistrada(sede);

        validarDisponibilidad(evento, fecha, hora, sedeRegistrada);
        evento.programar(fecha, hora, sedeRegistrada);
    }

    public void reprogramarEvento(
            int id, LocalDate fecha, int hora, String sede) {

        ICalendario evento = buscarEvento(id);
        String sedeRegistrada = buscarSedeRegistrada(sede);

        validarDisponibilidad(evento, fecha, hora, sedeRegistrada);
        evento.reprogramar(fecha, hora, sedeRegistrada);
    }

    public void eliminarEncuentro(int id) {
        Encuentros encuentro = buscarEncuentro(id);

        if (encuentro.getEstado() == EstadoEncuentro.FINALIZADO) {
            throw new IllegalStateException(
                    "No se puede eliminar un encuentro finalizado."
            );
        }

        eventos.remove(encuentro);
    }

    public void eliminarExhibicion(int id) {
        Exhibicion exhibicion = buscarExhibicion(id);
        eventos.remove(exhibicion);
    }

    public List<Equipo> obtenerTablaPosiciones() {
        List<Equipo> tabla = new ArrayList<>(equipos);
        Collections.sort(tabla);

        return Collections.unmodifiableList(tabla);
    }

    public List<ICalendario> obtenerCalendario() {
        List<ICalendario> calendario = new ArrayList<>();

        for (ICalendario evento : eventos) {
            if (evento.estaProgramado()) {
                calendario.add(evento);
            }
        }

        Collections.sort(calendario);
        return Collections.unmodifiableList(calendario);
    }

    @Override
    public Iterator<ICalendario> iterator() {
        return obtenerCalendario().iterator();
    }

    private int generarIdEvento() {
        return siguienteIdEvento++;
    }

    private ICalendario buscarEvento(int id) {
        for (ICalendario evento : eventos) {
            if (evento.getId() == id) {
                return evento;
            }
        }

        throw new IllegalArgumentException(
                "No se encontró el evento: " + id
        );
    }

    private String buscarSedeRegistrada(String nombreSede) {
        if (nombreSede == null || nombreSede.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe indicar una sede."
            );
        }

        for (String sede : sedes) {
            if (sede.equalsIgnoreCase(nombreSede.trim())) {
                return sede;
            }
        }

        throw new IllegalArgumentException(
                "La sede no está registrada."
        );
    }

    private void validarDisponibilidad(
            ICalendario evento,
            LocalDate fecha,
            int hora,
            String sede) {

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "Debe indicar una fecha válida."
            );
        }

        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException(
                    "La hora debe estar entre 0 y 23."
            );
        }

        for (ICalendario otro : eventos) {
            // Excluir al propio evento y a los eventos sin programación.
            if (otro.getId() == evento.getId()
                    || !otro.estaProgramado()) {
                continue;
            }

            boolean mismoHorario = fecha.equals(otro.getFecha())
                    && hora == otro.getHora();

            if (mismoHorario) {
                if (sede.equalsIgnoreCase(otro.getSede())) {
                    throw new IllegalStateException(
                            "La sede está ocupada en esa fecha y hora."
                    );
                }

                if (evento instanceof Encuentros
                        && otro instanceof Encuentros) {

                    Encuentros actual = (Encuentros) evento;
                    Encuentros existente = (Encuentros) otro;

                    if (compartenEquipo(actual, existente)) {
                        throw new IllegalStateException(
                                "Un equipo ya tiene otro encuentro "
                                        + "en esa fecha y hora."
                        );
                    }
                }
            }
        }
    }
    private boolean compartenEquipo(
            Encuentros primero, Encuentros segundo) {

        return primero.getEquipo1() == segundo.getEquipo1()
                || primero.getEquipo1() == segundo.getEquipo2()
                || primero.getEquipo2() == segundo.getEquipo1()
                || primero.getEquipo2() == segundo.getEquipo2();
    }
}
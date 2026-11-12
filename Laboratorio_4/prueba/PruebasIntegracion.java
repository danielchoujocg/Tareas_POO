import java.time.LocalDate;
import java.util.List;

public class PruebasIntegracion {
    private static int comprobaciones;

    public static void main(String[] args) {
        Copa copa = new Copa("Pruebas");
        ControladorCopa general = new ControladorCopa(copa);
        ControladorEncuentros encuentros = new ControladorEncuentros(copa);
        ControladorExhibiciones exhibiciones = new ControladorExhibiciones(copa);
        ControladorEquipos equipos = new ControladorEquipos(copa);
        LocalDate fecha = LocalDate.of(2026, 11, 12);

        general.agregarSede("Cancha A");
        general.agregarSede("Cancha B");
        for (String codigo : new String[] {"FAR", "SOL", "LUN", "MAR"}) {
            general.agregarEquipo(codigo, codigo, "Empresa");
        }
        verificar(equipos.listarEquipos().size() == 4, "Controladores comparten la copa");

        FutSal futsal = encuentros.crearFutSal("FAR", "SOL");
        encuentros.programarEncuentro(futsal.getId(), fecha, 9, "Cancha A");
        encuentros.registrarResultado(futsal.getId(), 1, 1);
        verificar(equipos.consultarPuntos("FAR") == 1
                && equipos.consultarPuntos("SOL") == 1, "Empate en fútbol sala");
        rechazar(() -> encuentros.registrarResultado(futsal.getId(), 2, 1));
        verificar(equipos.consultarPuntos("FAR") == 1, "El resultado solo suma una vez");

        Baloncesto baloncesto = encuentros.crearBaloncesto("LUN", "MAR");
        encuentros.programarEncuentro(baloncesto.getId(), fecha, 9, "Cancha B");
        rechazar(() -> encuentros.registrarResultado(baloncesto.getId(), 70, 70));
        verificar(baloncesto.getEstado() == EstadoEncuentro.PROGRAMADO
                && equipos.consultarPuntos("LUN") == 0, "Empate de baloncesto no cambia estado ni puntos");
        encuentros.registrarResultado(baloncesto.getId(), 78, 74);
        verificar(equipos.consultarPuntos("LUN") == 2
                && equipos.consultarPuntos("MAR") == 1, "Puntos de baloncesto");

        FutSal pendiente = encuentros.crearFutSal("FAR", "LUN");
        rechazar(() -> encuentros.programarEncuentro(pendiente.getId(), fecha, 9, "Cancha A"));
        rechazar(() -> encuentros.programarEncuentro(pendiente.getId(), fecha, 9, "Cancha B"));
        verificar(!pendiente.estaProgramado(), "Los conflictos no programan un encuentro");
        encuentros.programarEncuentro(pendiente.getId(), fecha.plusDays(1), 9, "Cancha A");
        rechazar(() -> encuentros.reprogramarEncuentro(pendiente.getId(), fecha, 9, "Cancha B"));
        verificar(pendiente.getFecha().equals(fecha.plusDays(1)),
                "Reprogramación rechazada conserva la fecha");

        Exhibicion exhibicion = exhibiciones.crearExhibicion("Clínica");
        exhibiciones.programarExhibicion(exhibicion.getId(), fecha, 10, "Cancha A");
        rechazar(() -> encuentros.programarEncuentro(exhibicion.getId(), fecha, 11, "Cancha B"));
        verificar(exhibicion.getHora() == 10, "Id de exhibición no entra al controlador de encuentros");

        List<ICalendario> calendario = general.obtenerCalendario();
        verificar(calendario.size() == 4
                && calendario.get(0) == futsal
                && calendario.get(1) == baloncesto
                && calendario.get(2) == exhibicion,
                "Calendario mezcla y ordena encuentros y exhibiciones");
        rechazar(() -> general.eliminarEquipo("FAR"));
        rechazar(() -> general.eliminarSede("Cancha A"));
        rechazar(() -> encuentros.eliminarEncuentro(futsal.getId()));
        verificar(general.obtenerTablaPosiciones().get(0).getCodigo().equals("LUN"),
                "Tabla conserva el orden después de rechazos");

        System.out.println("OK: " + comprobaciones + " comprobaciones de integración.");
    }

    private static void verificar(boolean condicion, String descripcion) {
        if (!condicion) {
            throw new AssertionError(descripcion);
        }
        comprobaciones++;
    }

    private static void rechazar(Runnable operacion) {
        try {
            operacion.run();
        } catch (IllegalArgumentException | IllegalStateException esperado) {
            comprobaciones++;
            return;
        }
        throw new AssertionError("Se esperaba el rechazo de una operación inválida.");
    }
}

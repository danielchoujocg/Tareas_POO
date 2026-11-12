import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Vista {
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);

    private final Scanner entrada;
    private final ControladorCopa controladorCopa;
    private final ControladorEncuentros controladorEncuentros;
    private final ControladorExhibiciones controladorExhibiciones;
    private final ControladorEquipos controladorEquipos;

    public Vista(Scanner entrada, ControladorCopa controladorCopa,
                 ControladorEncuentros controladorEncuentros,
                 ControladorExhibiciones controladorExhibiciones,
                 ControladorEquipos controladorEquipos) {
        this.entrada = Objects.requireNonNull(entrada, "El lector no puede ser nulo.");
        this.controladorCopa = Objects.requireNonNull(controladorCopa);
        this.controladorEncuentros = Objects.requireNonNull(controladorEncuentros);
        this.controladorExhibiciones = Objects.requireNonNull(controladorExhibiciones);
        this.controladorEquipos = Objects.requireNonNull(controladorEquipos);
    }

    public void iniciar() {
        System.out.println("Bienvenido a ArenaGT");
        try {
            boolean continuar = true;
            while (continuar) {
                mostrarMenuPrincipal();
                int opcion = leerEnteroEnRango("Opción: ", 0, 6);
                try {
                    switch (opcion) {
                        case 1: menuEquipos(); break;
                        case 2: menuSedes(); break;
                        case 3: menuEncuentros(); break;
                        case 4: menuExhibiciones(); break;
                        case 5: mostrarTablaPosiciones(); break;
                        case 6: mostrarCalendario(); break;
                        case 0: continuar = false; break;
                        default: break;
                    }
                } catch (IllegalArgumentException | IllegalStateException error) {
                    mostrarError(error);
                }
            }
        } catch (NoSuchElementException finEntrada) {
            System.out.println("Entrada finalizada.");
        }
        System.out.println("Hasta luego.");
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Equipos\n2. Sedes\n3. Encuentros\n4. Exhibiciones");
        System.out.println("5. Tabla de posiciones\n6. Calendario\n0. Salir");
    }

    private void menuEquipos() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== EQUIPOS ===");
            System.out.println("1. Registrar\n2. Eliminar\n3. Listar\n4. Consultar\n0. Regresar");
            int opcion = leerEnteroEnRango("Opción: ", 0, 4);
            try {
                switch (opcion) {
                    case 1: registrarEquipo(); break;
                    case 2: eliminarEquipo(); break;
                    case 3: mostrarEquipos(); break;
                    case 4: consultarEquipo(); break;
                    case 0: continuar = false; break;
                    default: break;
                }
            } catch (IllegalArgumentException | IllegalStateException error) {
                mostrarError(error);
            }
        }
    }

    private void menuSedes() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== SEDES ===");
            System.out.println("1. Agregar\n2. Eliminar\n3. Listar\n0. Regresar");
            int opcion = leerEnteroEnRango("Opción: ", 0, 3);
            try {
                switch (opcion) {
                    case 1: agregarSede(); break;
                    case 2: eliminarSede(); break;
                    case 3: mostrarSedes(); break;
                    case 0: continuar = false; break;
                    default: break;
                }
            } catch (IllegalArgumentException | IllegalStateException error) {
                mostrarError(error);
            }
        }
    }

    private void menuEncuentros() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== ENCUENTROS ===");
            System.out.println("1. Crear\n2. Listar\n3. Consultar\n4. Programar");
            System.out.println("5. Reprogramar\n6. Registrar resultado\n7. Eliminar\n0. Regresar");
            int opcion = leerEnteroEnRango("Opción: ", 0, 7);
            try {
                switch (opcion) {
                    case 1: crearEncuentro(); break;
                    case 2: mostrarEncuentros(); break;
                    case 3: consultarEncuentro(); break;
                    case 4: programarEncuentro(); break;
                    case 5: reprogramarEncuentro(); break;
                    case 6: registrarResultado(); break;
                    case 7: eliminarEncuentro(); break;
                    case 0: continuar = false; break;
                    default: break;
                }
            } catch (IllegalArgumentException | IllegalStateException error) {
                mostrarError(error);
            }
        }
    }

    private void menuExhibiciones() {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== EXHIBICIONES ===");
            System.out.println("1. Crear\n2. Listar\n3. Consultar\n4. Programar");
            System.out.println("5. Reprogramar\n6. Eliminar\n0. Regresar");
            int opcion = leerEnteroEnRango("Opción: ", 0, 6);
            try {
                switch (opcion) {
                    case 1: crearExhibicion(); break;
                    case 2: mostrarExhibiciones(); break;
                    case 3: consultarExhibicion(); break;
                    case 4: programarExhibicion(); break;
                    case 5: reprogramarExhibicion(); break;
                    case 6: eliminarExhibicion(); break;
                    case 0: continuar = false; break;
                    default: break;
                }
            } catch (IllegalArgumentException | IllegalStateException error) {
                mostrarError(error);
            }
        }
    }

    private void registrarEquipo() {
        String codigo = leerTextoObligatorio("Código: ");
        String nombre = leerTextoObligatorio("Nombre: ");
        String empresa = leerTextoObligatorio("Empresa: ");
        Equipo equipo = controladorCopa.agregarEquipo(codigo, nombre, empresa);
        System.out.println("Equipo registrado: " + equipo.getCodigo());
    }

    private void eliminarEquipo() {
        controladorCopa.eliminarEquipo(leerTextoObligatorio("Código: "));
        System.out.println("Equipo eliminado.");
    }

    private void mostrarEquipos() {
        List<Equipo> equipos = controladorEquipos.listarEquipos();
        if (equipos.isEmpty()) {
            System.out.println("No hay equipos registrados.");
            return;
        }
        for (Equipo equipo : equipos) {
            System.out.printf("%s | %s | %s%n", equipo.getCodigo(),
                    equipo.getNombre(), equipo.getEmpresa());
        }
    }

    private void consultarEquipo() {
        Equipo equipo = controladorEquipos.buscarEquipo(leerTextoObligatorio("Código: "));
        System.out.printf("Código: %s | Nombre: %s | Empresa: %s | Puntos: %d%n",
                equipo.getCodigo(), equipo.getNombre(), equipo.getEmpresa(),
                equipo.getPuntos());
    }

    private void agregarSede() {
        controladorCopa.agregarSede(leerTextoObligatorio("Sede: "));
        System.out.println("Sede registrada.");
    }

    private void eliminarSede() {
        controladorCopa.eliminarSede(leerTextoObligatorio("Sede: "));
        System.out.println("Sede eliminada.");
    }

    private void mostrarSedes() {
        List<String> sedes = controladorCopa.listarSedes();
        if (sedes.isEmpty()) {
            System.out.println("No hay sedes registradas.");
            return;
        }
        for (String sede : sedes) {
            System.out.println(sede);
        }
    }

    private void crearEncuentro() {
        System.out.println("1. Fútbol sala\n2. Baloncesto");
        int deporte = leerEnteroEnRango("Deporte: ", 1, 2);
        String equipo1 = leerTextoObligatorio("Código del equipo 1: ");
        String equipo2 = leerTextoObligatorio("Código del equipo 2: ");
        Encuentros encuentro = deporte == 1
                ? controladorEncuentros.crearFutSal(equipo1, equipo2)
                : controladorEncuentros.crearBaloncesto(equipo1, equipo2);
        System.out.println("Encuentro creado con id " + encuentro.getId() + ".");
    }

    private void mostrarEncuentros() {
        List<Encuentros> encuentros = controladorEncuentros.listarEncuentros();
        if (encuentros.isEmpty()) {
            System.out.println("No hay encuentros registrados.");
            return;
        }
        for (Encuentros encuentro : encuentros) {
            mostrarDetalleEncuentro(encuentro);
        }
    }

    private void consultarEncuentro() {
        mostrarDetalleEncuentro(controladorEncuentros.buscarEncuentro(leerId()));
    }

    private void mostrarDetalleEncuentro(Encuentros encuentro) {
        System.out.printf("Id %d | %s | %s | %s | %s%n", encuentro.getId(),
                encuentro.getDescripcion(), encuentro.getEstado(),
                mostrarProgramacion(encuentro), encuentro.getMarcador());
    }

    private void programarEncuentro() {
        int id = leerId();
        LocalDate fecha = leerFecha("Fecha (ejemplo 2026-11-12): ");
        int hora = leerEnteroEnRango("Hora (0 a 23): ", 0, 23);
        String sede = leerTextoObligatorio("Sede: ");
        controladorEncuentros.programarEncuentro(id, fecha, hora, sede);
        System.out.println("Encuentro programado.");
    }

    private void reprogramarEncuentro() {
        int id = leerId();
        LocalDate fecha = leerFecha("Nueva fecha (ejemplo 2026-11-12): ");
        int hora = leerEnteroEnRango("Nueva hora (0 a 23): ", 0, 23);
        String sede = leerTextoObligatorio("Nueva sede: ");
        controladorEncuentros.reprogramarEncuentro(id, fecha, hora, sede);
        System.out.println("Encuentro reprogramado.");
    }

    private void registrarResultado() {
        int id = leerId();
        int marcadorE1 = leerEnteroEnRango("Marcador del equipo 1: ", 0, Integer.MAX_VALUE);
        int marcadorE2 = leerEnteroEnRango("Marcador del equipo 2: ", 0, Integer.MAX_VALUE);
        controladorEncuentros.registrarResultado(id, marcadorE1, marcadorE2);
        System.out.println("Resultado registrado.");
    }

    private void eliminarEncuentro() {
        controladorEncuentros.eliminarEncuentro(leerId());
        System.out.println("Encuentro eliminado.");
    }

    private void crearExhibicion() {
        Exhibicion exhibicion = controladorExhibiciones.crearExhibicion(
                leerTextoObligatorio("Título: "));
        System.out.println("Exhibición creada con id " + exhibicion.getId() + ".");
    }

    private void mostrarExhibiciones() {
        List<Exhibicion> exhibiciones = controladorExhibiciones.listarExhibiciones();
        if (exhibiciones.isEmpty()) {
            System.out.println("No hay exhibiciones registradas.");
            return;
        }
        for (Exhibicion exhibicion : exhibiciones) {
            mostrarDetalleExhibicion(exhibicion);
        }
    }

    private void consultarExhibicion() {
        mostrarDetalleExhibicion(controladorExhibiciones.buscarExhibicion(leerId()));
    }

    private void mostrarDetalleExhibicion(Exhibicion exhibicion) {
        System.out.printf("Id %d | %s | %s | %s%n", exhibicion.getId(),
                exhibicion.getTitulo(), exhibicion.getEstado(),
                mostrarProgramacion(exhibicion));
    }

    private void programarExhibicion() {
        int id = leerId();
        LocalDate fecha = leerFecha("Fecha (ejemplo 2026-11-12): ");
        int hora = leerEnteroEnRango("Hora (0 a 23): ", 0, 23);
        String sede = leerTextoObligatorio("Sede: ");
        controladorExhibiciones.programarExhibicion(id, fecha, hora, sede);
        System.out.println("Exhibición programada.");
    }

    private void reprogramarExhibicion() {
        int id = leerId();
        LocalDate fecha = leerFecha("Nueva fecha (ejemplo 2026-11-12): ");
        int hora = leerEnteroEnRango("Nueva hora (0 a 23): ", 0, 23);
        String sede = leerTextoObligatorio("Nueva sede: ");
        controladorExhibiciones.reprogramarExhibicion(id, fecha, hora, sede);
        System.out.println("Exhibición reprogramada.");
    }

    private void eliminarExhibicion() {
        controladorExhibiciones.eliminarExhibicion(leerId());
        System.out.println("Exhibición eliminada.");
    }

    private void mostrarTablaPosiciones() {
        List<Equipo> tabla = controladorCopa.obtenerTablaPosiciones();
        if (tabla.isEmpty()) {
            System.out.println("No hay equipos en la tabla.");
            return;
        }
        System.out.println("\nPos. | Código | Nombre | Empresa | Puntos");
        for (int i = 0; i < tabla.size(); i++) {
            Equipo equipo = tabla.get(i);
            System.out.printf("%d | %s | %s | %s | %d%n", i + 1,
                    equipo.getCodigo(), equipo.getNombre(), equipo.getEmpresa(),
                    equipo.getPuntos());
        }
    }

    private void mostrarCalendario() {
        List<ICalendario> calendario = controladorCopa.obtenerCalendario();
        if (calendario.isEmpty()) {
            System.out.println("No hay eventos programados.");
            return;
        }
        for (ICalendario evento : calendario) {
            System.out.printf("%s %02d:00 | %s | %s%n", evento.getFecha(),
                    evento.getHora(), evento.getSede(), evento.getDescripcion());
        }
    }

    private int leerId() {
        return leerEnteroEnRango("Id: ", 1, Integer.MAX_VALUE);
    }

    private String mostrarProgramacion(ICalendario evento) {
        if (!evento.estaProgramado()) {
            return "Sin programar";
        }
        return String.format("%s %02d:00 en %s", evento.getFecha(),
                evento.getHora(), evento.getSede());
    }

    private String leerTextoObligatorio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El texto no puede estar vacío.");
        }
    }

    private int leerEnteroEnRango(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException error) {
                // Se informa el mismo rango para texto inválido y desbordamiento.
            }
            System.out.printf("Ingrese un entero entre %d y %d.%n", minimo, maximo);
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return LocalDate.parse(entrada.nextLine().trim(), FORMATO_FECHA);
            } catch (DateTimeParseException error) {
                System.out.println("Fecha inválida. Use el formato 2026-11-12.");
            }
        }
    }

    private void mostrarError(RuntimeException error) {
        System.out.println("No se pudo completar la operación: " + error.getMessage());
    }
}

import java.time.LocalDate;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Copa copa = new Copa("ArenaGT");
        cargarDatosIniciales(copa);

        ControladorCopa controladorCopa = new ControladorCopa(copa);
        ControladorEncuentros controladorEncuentros = new ControladorEncuentros(copa);
        ControladorExhibiciones controladorExhibiciones = new ControladorExhibiciones(copa);
        ControladorEquipos controladorEquipos = new ControladorEquipos(copa);

        try (Scanner entrada = new Scanner(System.in)) {
            Vista vista = new Vista(entrada, controladorCopa, controladorEncuentros,
                    controladorExhibiciones, controladorEquipos);
            vista.iniciar();
        }
    }

    private static void cargarDatosIniciales(Copa copa) {
        copa.agregarSede("Cancha A");
        copa.agregarSede("Cancha B");

        copa.agregarEquipo("FAR", "Faro", "Empresa A");
        copa.agregarEquipo("SOL", "Sol", "Empresa A");
        copa.agregarEquipo("LUN", "Luna", "Empresa B");
        copa.agregarEquipo("MAR", "Mar", "Empresa B");

        FutSal primero = copa.crearFutSal("FAR", "SOL");
        copa.programarEvento(primero.getId(), LocalDate.of(2026, 11, 12), 9, "Cancha A");
        primero.registrarResultado(1, 1);

        Baloncesto segundo = copa.crearBaloncesto("LUN", "MAR");
        copa.programarEvento(segundo.getId(), LocalDate.of(2026, 11, 12), 10, "Cancha B");
        segundo.registrarResultado(78, 74);

        FutSal tercero = copa.crearFutSal("FAR", "LUN");
        copa.programarEvento(tercero.getId(), LocalDate.of(2026, 11, 13), 9, "Cancha A");

        Baloncesto cuarto = copa.crearBaloncesto("SOL", "MAR");
        copa.programarEvento(cuarto.getId(), LocalDate.of(2026, 11, 13), 10, "Cancha B");

        Exhibicion exhibicion = copa.crearExhibicion("Clínica de baloncesto");
        copa.programarEvento(exhibicion.getId(), LocalDate.of(2026, 11, 12), 9, "Cancha B");
    }
}

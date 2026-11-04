import java.util.Scanner;
public class VistaConsola {
    private final Scanner scanner;
    public VistaConsola() {
        scanner = new Scanner(System.in);
    }
    public void mostrarMenu() {
        Main.imprimirLinea("\n--- MENÚ DE INTEGRACIONES ---");
        Main.imprimirLinea("1. Listar integraciones activas");
        Main.imprimirLinea("2. Buscar integración");
        Main.imprimirLinea("3. Ordenar por latencia");
        Main.imprimirLinea("4. Procesar cobro");
        Main.imprimirLinea("5. Procesar reintegro");
        Main.imprimirLinea("6. Generar documento");
        Main.imprimirLinea("0. Salir");
    }
    public int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public String leerTexto(String mensaje) {
        Main.imprimirLinea(mensaje);
        return scanner.nextLine();
    }

    public double leerMonto() {
        try {
            return Double.parseDouble(leerTexto("Ingrese el monto:"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    public void mostrarMensaje(String mensaje) {
        Main.imprimirLinea(mensaje);
    }
    public void mostrarError(String mensaje) {
        Main.imprimirLinea("Error: " + mensaje);
    }
    public void mostrarListado(String listado) {
        Main.imprimirLinea(listado);
    }
    public void mostrarConfiguracion(IntegracionAPI integracion) {
        if (integracion == null) {
            mostrarError("No se encontró la integración.");
            return;
        }
        mostrarMensaje("Identificador: " + integracion.getIdentificador());
        mostrarMensaje("Proveedor: " + integracion.getNombreProveedor());
        mostrarMensaje("Categoría: " + integracion.getCategoria());
        mostrarMensaje("Parámetros: " + integracion.getParametrosConfiguracion());
        mostrarMensaje("Latencia: " + integracion.getLatenciaPromedio() + " ms");
        mostrarMensaje("Conexión: " + (integracion.esConexionActiva() ? "Activa" : "Inactiva"));
    }
    
}
public class ControladorIntegraciones {
    private final CatalogoIntegraciones catalogo;
    private final VistaConsola vista;
    public ControladorIntegraciones(CatalogoIntegraciones catalogo,VistaConsola vista) {
        this.catalogo = catalogo;
        this.vista = vista;
    }

    public void iniciar() {
        boolean continuar = true;
        while (continuar) {
            vista.mostrarMenu();
            continuar = ejecutarOpcion(vista.leerOpcion());
        }
    }
    private boolean ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                listarIntegraciones();
                break;
            case 2:
                buscarIntegracion();
                break;
            case 3:
                ordenarIntegraciones();
                break;
            case 4:
                procesarCobro();
                break;
            case 5:
                procesarReintegro();
                break;
            case 6:
                generarDocumento();
                break;
            case 0:
                vista.mostrarMensaje("Programa finalizado.");
                return false;
            default:
                vista.mostrarError("La opción ingresada no es válida.");
        }
        return true;
    }
    private void listarIntegraciones() {
        vista.mostrarListado(catalogo.listarIntegracionesActivas());
    }
    private void buscarIntegracion() {
        String id = vista.leerTexto("Ingrese el identificador:");
        IntegracionAPI integracion = catalogo.buscarIntegracion(id);
        vista.mostrarConfiguracion(integracion);
    }
    private void ordenarIntegraciones() {
        catalogo.ordenarPorLatencia();
        vista.mostrarListado(catalogo.listarIntegraciones());
    }
    private void procesarCobro() {
        IntegracionAPI integracion = buscarPorId();
        if (!(integracion instanceof Transaccional)) {
            vista.mostrarError("Esta integración no admite cobros.");
            return;
        }
        double monto = vista.leerMonto();
        String referencia = vista.leerTexto("Ingrese la referencia:");
        boolean procesado = catalogo.procesarCobro((Transaccional) integracion, monto, referencia
        );
        mostrarResultadoOperacion(procesado, "Cobro");
    }
    private void procesarReintegro() {
        IntegracionAPI integracion = buscarPorId();
        if (integracion == null) {
            return;
        }
        double monto = vista.leerMonto();
        String referencia = vista.leerTexto("Ingrese la referencia:");
        boolean procesado = catalogo.procesarReintegro(integracion, monto, referencia
        );
        mostrarResultadoOperacion(procesado, "Reintegro");
    }
    private void generarDocumento() {
        IntegracionAPI integracion = buscarPorId();
        if (!(integracion instanceof Documentable)) {
            vista.mostrarError("Esta integración no genera documentos.");
            return;
        }
        String datos = vista.leerTexto("Ingrese los datos del documento:");
        String documento = catalogo.generarDocumento((Documentable) integracion, datos
        );
        vista.mostrarMensaje(documento);
    }
    private IntegracionAPI buscarPorId() {
        String id = vista.leerTexto("Ingrese el identificador:");
        IntegracionAPI integracion = catalogo.buscarIntegracion(id);
        if (integracion == null) {
            vista.mostrarError("No se encontró la integración.");
        }
        return integracion;
    }
    private void mostrarResultadoOperacion(boolean procesado,String operacion) {
        if (procesado) {
            vista.mostrarMensaje(operacion + " procesado correctamente.");
        } else {
            vista.mostrarError("No se pudo procesar el " + operacion.toLowerCase() + "."
            );
        }
    }
}
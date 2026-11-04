public class Main {
    public static void main(String[] args) {
        CatalogoIntegraciones catalogo = new CatalogoIntegraciones();
        catalogo.cargarIntegracionesIniciales();
        VistaConsola vista = new VistaConsola();
        ControladorIntegraciones controlador = new ControladorIntegraciones(catalogo, vista);
        controlador.iniciar();
    }
    public static void imprimirLinea(String texto) {
        System.out.println(texto);
    }
}
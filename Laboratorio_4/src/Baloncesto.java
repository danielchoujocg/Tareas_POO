public class Baloncesto extends Encuentros {
    private static final int PUNTOS_GANADOR = 2;
    private static final int PUNTOS_PERDEDOR = 1;
    public Baloncesto(int id, Equipo equipo1, Equipo equipo2) {
        super(id, equipo1, equipo2);
    }
    @Override
    protected boolean validarMarcador(int marcadorE1, int marcadorE2) {
        return marcadorE1 >= 0
                && marcadorE2 >= 0
                && marcadorE1 != marcadorE2;
    }
    @Override
    protected int calcularPuntosEquipo1(int marcadorE1, int marcadorE2) {
        if (marcadorE1 > marcadorE2) {
            return PUNTOS_GANADOR;
        }
        return PUNTOS_PERDEDOR;
    }
    @Override
    protected int calcularPuntosEquipo2(int marcadorE1, int marcadorE2) {
        if (marcadorE2 > marcadorE1) {
            return PUNTOS_GANADOR;
        }
        return PUNTOS_PERDEDOR;
    }
}
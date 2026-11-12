public class FutSal extends Encuentros {
    private static final int PUNTOS_GANADOR = 3;
    private static final int PUNTOS_PERDEDOR = 0;
    private static final int PUNTOS_EMPATE = 1;
    public FutSal(int id, Equipo equipo1, Equipo equipo2) {
        super(id, equipo1, equipo2);
    }
    @Override
    protected boolean validarMarcador(int marcadorE1, int marcadorE2) {
        return marcadorE1 >= 0 && marcadorE2 >= 0;
    }
    @Override
    protected int calcularPuntosEquipo1(int marcadorE1, int marcadorE2) {
        if (marcadorE1 > marcadorE2) {
            return PUNTOS_GANADOR;
        }
        if (marcadorE1 == marcadorE2) {
            return PUNTOS_EMPATE;
        }
        return PUNTOS_PERDEDOR;
    }
    @Override
    protected int calcularPuntosEquipo2(int marcadorE1, int marcadorE2) {
        if (marcadorE2 > marcadorE1) {
            return PUNTOS_GANADOR;
        }

        if (marcadorE1 == marcadorE2) {
            return PUNTOS_EMPATE;
        }

        return PUNTOS_PERDEDOR;
    }
}
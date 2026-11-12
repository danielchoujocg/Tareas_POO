import java.time.LocalDate;
public abstract class Encuentros implements ICalendario, IResultado {
    private final int id;
    private final Equipo equipo1;
    private final Equipo equipo2;
    private int marcadorE1;
    private int marcadorE2;
    private EstadoEncuentro estado;
    private LocalDate fecha;
    private int hora;
    private String sede;
    protected Encuentros(int id, Equipo equipo1, Equipo equipo2) {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "El identificador debe ser positivo."
            );
        }
        if (equipo1 == null || equipo2 == null) {
            throw new IllegalArgumentException(
                    "Los equipos no pueden ser nulos."
            );
        }
        if (equipo1.getCodigo().equals(equipo2.getCodigo())) {
            throw new IllegalArgumentException("Los equipos deben ser distintos.");
        }
        this.id = id;
        this.equipo1 = equipo1;
        this.equipo2 = equipo2;
        this.marcadorE1 = 0;
        this.marcadorE2 = 0;
        this.estado = EstadoEncuentro.SIN_PROGRAMAR;
        this.fecha = null;
        this.hora = -1;
        this.sede = null;
    
    }
    public Equipo getEquipo1() {
        return equipo1;
    }
    public Equipo getEquipo2() {
        return equipo2;
    }
    public int getMarcadorE1() {
        if (estado != EstadoEncuentro.FINALIZADO) {
            throw new IllegalStateException("El encuentro todavía no tiene resultado.");
        }
        return marcadorE1;
    }
    public int getMarcadorE2() {
        if (estado != EstadoEncuentro.FINALIZADO) {
            throw new IllegalStateException("El encuentro todavía no tiene resultado.");
        }
        return marcadorE2;
    }
    public String getMarcador() {
        if (estado != EstadoEncuentro.FINALIZADO) {
            return "Sin resultado";
        }
        return marcadorE1 + " - " + marcadorE2;
    }
    public EstadoEncuentro getEstado() {
        return estado;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public LocalDate getFecha() {
        return fecha;
    }
    @Override
    public int getHora() {
        return hora;
    }
    @Override
    public String getSede() {
        return sede;
    }
    @Override
    public boolean estaProgramado() {
        return estado != EstadoEncuentro.SIN_PROGRAMAR;
    }
    @Override
    public String getDescripcion() {
        return equipo1.getNombre() + " vs. " + equipo2.getNombre();
    }
    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if (estado != EstadoEncuentro.SIN_PROGRAMAR) {
            throw new IllegalStateException(
                    "El encuentro ya tiene programación."
            );
        }
        validarProgramacion(fecha, hora, sede);
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        this.estado = EstadoEncuentro.PROGRAMADO;
    }
    @Override
    public void reprogramar(LocalDate fecha, int hora, String sede) {
        if (estado != EstadoEncuentro.PROGRAMADO) {
            throw new IllegalStateException("Solo se puede reprogramar un encuentro programado "+ "y sin resultado.");
        }
        validarProgramacion(fecha, hora, sede);
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
    }
    @Override
    public final void registrarResultado(int marcadorE1, int marcadorE2) {
        if (estado != EstadoEncuentro.PROGRAMADO) {
            throw new IllegalStateException("El encuentro debe estar programado y sin resultado.");
        }
        if (!validarMarcador(marcadorE1, marcadorE2)) {
            throw new IllegalArgumentException("El marcador no es válido para este deporte.");
        }
        int puntosEquipo1 = calcularPuntosEquipo1(marcadorE1, marcadorE2);
        int puntosEquipo2 = calcularPuntosEquipo2(marcadorE1, marcadorE2);
        if (puntosEquipo1 < 0 || puntosEquipo2 < 0) {
            throw new IllegalArgumentException(
                    "Los puntos de tabla no pueden ser negativos."
            );
        }
        this.marcadorE1 = marcadorE1;
        this.marcadorE2 = marcadorE2;
        equipo1.sumarPuntos(puntosEquipo1);
        equipo2.sumarPuntos(puntosEquipo2);
        this.estado = EstadoEncuentro.FINALIZADO;
    }
    private void validarProgramacion(LocalDate fecha, int hora, String sede) {
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
        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La sede no puede estar vacía."
            );
        }
    }
    protected abstract boolean validarMarcador(int marcadorE1, int marcadorE2);
    protected abstract int calcularPuntosEquipo1(int marcadorE1, int marcadorE2);
    protected abstract int calcularPuntosEquipo2(int marcadorE1, int marcadorE2);
}
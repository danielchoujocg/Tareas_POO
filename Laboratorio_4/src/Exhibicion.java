import java.time.LocalDate;
public class Exhibicion implements ICalendario {
    private final int id;
    private String titulo;
    private EstadoExhibicion estado;
    private LocalDate fecha;
    private int hora;
    private String sede;
    public Exhibicion(int id, String titulo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El identificador debe ser positivo.");
        }
        this.id = id;
        setTitulo(titulo);
        this.estado = EstadoExhibicion.SIN_PROGRAMAR;
        this.fecha = null;
        this.hora = -1;
        this.sede = null;
    }
    public String getTitulo() {
        return titulo;
    }
    public EstadoExhibicion getEstado() {
        return estado;
    }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }
        this.titulo = titulo.trim();
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
        return estado == EstadoExhibicion.PROGRAMADA;
    }
    @Override
    public String getDescripcion() {
        return titulo;
    }
    @Override
    public void programar(LocalDate fecha, int hora, String sede) {
        if (estado != EstadoExhibicion.SIN_PROGRAMAR) {
            throw new IllegalStateException("La exhibición ya tiene programación.");
        }
        validarProgramacion(fecha, hora, sede);
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
        this.estado = EstadoExhibicion.PROGRAMADA;
    }
    @Override
    public void reprogramar(LocalDate fecha, int hora, String sede) {
        if (estado != EstadoExhibicion.PROGRAMADA) {
            throw new IllegalStateException("Primero debe programar la exhibición.");
        }
        validarProgramacion(fecha, hora, sede);
        this.fecha = fecha;
        this.hora = hora;
        this.sede = sede.trim();
    }
    private void validarProgramacion(
            LocalDate fecha, int hora, String sede) {
        if (fecha == null) {
            throw new IllegalArgumentException("Debe indicar una fecha válida.");
        }
        if (hora < 0 || hora > 23) {
            throw new IllegalArgumentException("La hora debe estar entre 0 y 23.");
        }
        if (sede == null || sede.trim().isEmpty()) {
            throw new IllegalArgumentException("La sede no puede estar vacía.");
        }
    }
}
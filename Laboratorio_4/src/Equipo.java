public class Equipo implements Comparable<Equipo> {

    private final String codigo;
    private String nombre;
    private String empresa;
    private int puntos;
    public Equipo(String codigo, String nombre, String empresa) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del equipo no puede estar vacío.");
        }
        this.codigo = codigo.trim();
        setNombre(nombre);
        setEmpresa(empresa);
        this.puntos = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getPuntos() {
        return puntos;
    }

    void sumarPuntos(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException(
                    "No se pueden sumar puntos negativos."
            );
        }

        puntos += cantidad;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }
    public void setEmpresa(String empresa) {
        if (empresa == null || empresa.trim().isEmpty()) {
            throw new IllegalArgumentException("La empresa no puede estar vacía.");
        }
        this.empresa = empresa.trim();
    }
    @Override
    public int compareTo(Equipo otro) {
        int comparacion = Integer.compare(otro.puntos, this.puntos);
        if (comparacion == 0) {
            return this.nombre.compareTo(otro.nombre);
        }
        return comparacion;
    }
}
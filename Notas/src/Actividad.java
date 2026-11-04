public abstract class Actividad {
    protected String nombre;
    protected double peso;
    protected double puntaje;
    public Actividad(String nombre, double peso, double puntaje){
        this.nombre = nombre;
        this.peso = peso;
        this.puntaje = puntaje;
    }
}

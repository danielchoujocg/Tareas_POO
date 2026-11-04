import java.util.ArrayList;
public class Estudiante{
    private String nombre;
    private int id;    
    private ArrayList<Actividad> actividades;
    public Estudiante (String nombre,int id){
        this.nombre = nombre;
        this.id = id;
        actividades = new ArrayList<>();
    }
    public void AgregarActividad(Actividad actividad){
        actividades.add(actividad);
    }

}

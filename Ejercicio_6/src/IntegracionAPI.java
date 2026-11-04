public abstract class IntegracionAPI implements Comparable<IntegracionAPI>{
    private final String identificador;
    private final String nombreProveedor;
    private final String categoria;
    private final String parametrosConfiguracion;
    private final double latenciaPromedio;
    private boolean conexionActiva;
    protected IntegracionAPI(String identificador, String nombreProveedor, String categoria, 
        String parametrosConfiguracion, double latenciaPromedio, boolean conexionActiva){
            this.identificador = identificador;
            this.nombreProveedor = nombreProveedor;
            this.categoria = categoria;
            this.parametrosConfiguracion = parametrosConfiguracion;
            this.latenciaPromedio = latenciaPromedio;
            this.conexionActiva = conexionActiva;
        }
        public String getIdentificador(){
            return identificador;
        }
        public String getNombreProveedor(){
            return nombreProveedor;
        }
        public String getCategoria(){
            return categoria;
        }
        public String getParametrosConfiguracion(){
            return parametrosConfiguracion;
        }
        public double getLatenciaPromedio(){
            return latenciaPromedio;
        }
        public boolean esConexionActiva(){
            return conexionActiva;
        }
        public void setConexionActiva(boolean activa){
            this.conexionActiva = activa;
        }
        @Override 
        public int compareTo(IntegracionAPI otra){
            return Double.compare(this.latenciaPromedio, otra.latenciaPromedio);
        }
        @Override 
        public String toString(){
            String estado = conexionActiva ? "activa": "inactiva";
            return identificador + "-" + nombreProveedor + "(" + categoria +"), latencia: " + latenciaPromedio 
            +"ms, conexión: "+ estado; 
        }
}

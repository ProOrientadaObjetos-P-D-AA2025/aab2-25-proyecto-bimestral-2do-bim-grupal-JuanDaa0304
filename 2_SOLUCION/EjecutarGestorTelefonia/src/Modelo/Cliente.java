package modelo;

public class Cliente {

    private String cedula;
    private String nombre;
    private String ciudad;
    private String email;
    private String fechaNacimiento;
    private Dispositivo dispositivo;
    private Plan planPrincipal;
    private Plan planSecundario;

    public Cliente(String cedula, String nombre, String ciudad, String email, String fechaNacimiento) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Plan getPlanPrincipal() {
        return planPrincipal;
    }

    public void setPlanPrincipal(Plan plan) {
        this.planPrincipal = plan;
    }

    public Plan getPlanSecundario() {
        return planSecundario;
    }

    public void setPlanSecundario(Plan plan) {
        this.planSecundario = plan;
    }

    public Plan getPlan(int posicion) {
        return posicion == 1 ? planPrincipal : planSecundario;
    }

    public void setPlan(Plan plan, int posicion) {
        if (posicion == 1) {
            setPlanPrincipal(plan);
        } else {
            setPlanSecundario(plan);
        }
    }

    @Override
    public String toString() {
        return "Cedula: " + cedula + "-- Nombre=" + nombre;
    }
    
    
}

package servicio;

public class ValidadorDatos {
    
    public boolean validarCedula(String cedula) {
        return cedula != null && cedula.trim().length() >= 10;
    }
    
    public boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public boolean validarTelefono(String telefono) {
        return telefono != null && telefono.matches("\\d{9,10}");
    }
}
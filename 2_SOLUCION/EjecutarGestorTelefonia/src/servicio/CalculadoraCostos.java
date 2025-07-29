package servicio;

import modelo.Cliente;
import modelo.Plan;

public class CalculadoraCostos {
    
    public double calcularPagoTotal(Cliente cliente) {
        double total = 0.0;
        
        if (cliente.getPlanPrincipal() != null) {
            total += cliente.getPlanPrincipal().calcularCosto();
        }
        
        if (cliente.getPlanSecundario() != null) {
            total += cliente.getPlanSecundario().calcularCosto();
        }
        
        return total;
    }
    
    public double aplicarDescuentoFidelidad(double monto, boolean esFiel) {
        return esFiel ? monto * 0.95 : monto; 
    }
}
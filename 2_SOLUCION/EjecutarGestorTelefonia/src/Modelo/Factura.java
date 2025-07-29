package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Factura {

    private String numero;
    private String cedulaCliente;
    private String nombreCliente;
    private double total;
    private LocalDateTime fechaGeneracion;

    public Factura(String cedulaCliente, String nombreCliente, double total) {
        this.numero = "FAC-" + System.currentTimeMillis();
        this.cedulaCliente = cedulaCliente;
        this.nombreCliente = nombreCliente;
        this.total = total;
        this.fechaGeneracion = LocalDateTime.now();
    }

    public String getNumero() {
        return numero;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public String getFechaFormateada() {
        return fechaGeneracion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}

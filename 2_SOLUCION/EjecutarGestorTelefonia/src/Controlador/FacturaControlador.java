package controlador;

import dao.FacturaDAO;
import modelo.Cliente;
import modelo.Factura;
import servicio.CalculadoraCostos;

import java.sql.SQLException;
import java.util.List;

public class FacturaControlador {

    private final FacturaDAO dao = new FacturaDAO();
    private final CalculadoraCostos calc = new CalculadoraCostos();

    public Factura generarPara(Cliente c) {
        if (c == null) {
            throw new IllegalArgumentException("Cliente no existe");
        }
        double total = calc.calcularPagoTotal(c);
        if (total <= 0) {
            throw new IllegalStateException("El cliente no tiene planes asignados");
        }
        Factura f = new Factura(c.getCedula(), c.getNombre(), total);
        try {
            dao.crear(f);
        } catch (SQLException ex) {
            throw new RuntimeException("Error al guardar factura: " + ex.getMessage(), ex);
        }
        return f;
    }

    public List<Factura> listarPorCliente(String cedula) {
        try {
            return dao.listarPorCliente(cedula);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Factura> listarTodas() {
        try {
            return dao.listarTodas();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

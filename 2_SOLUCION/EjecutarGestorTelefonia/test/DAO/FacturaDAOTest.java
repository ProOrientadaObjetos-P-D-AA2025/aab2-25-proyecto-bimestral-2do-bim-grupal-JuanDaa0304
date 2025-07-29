package DAO;

import modelo.Factura;
import util.ConexionSQLite;
import org.junit.Before;
import org.junit.Test;
import dao.FacturaDAO;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class FacturaDAOTest {

    private FacturaDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new FacturaDAO();
        try (Connection c = ConexionSQLite.conectar(); Statement s = c.createStatement()) {
            s.executeUpdate("DELETE FROM facturas");
        }
    }

    @Test
    public void testCrearYListarPorCliente() throws Exception {
        Factura f1 = new Factura("123", "Juan", 50.0);
        Factura f2 = new Factura("123", "Juan", 75.5);
        assertTrue("Debe crear f1", dao.crear(f1));
        assertTrue("Debe crear f2", dao.crear(f2));

        List<Factura> lista = dao.listarPorCliente("123");
        assertEquals("Debe devolver 2 facturas", 2, lista.size());

        List<Double> totales = lista.stream()
                .map(Factura::getTotal)
                .toList();
        assertTrue("Debe contener total 50.0", totales.contains(50.0));
        assertTrue("Debe contener total 75.5", totales.contains(75.5));
    }

    @Test
    public void testListarTodas() throws Exception {
        assertTrue("Crear factura A1", dao.crear(new Factura("A1", "Ana", 100.0)));
        assertTrue("Crear factura B2", dao.crear(new Factura("B2", "Beto", 200.0)));

        List<Factura> all = dao.listarTodas();
        assertEquals("Debe listar 2 facturas en total", 2, all.size());

        List<Double> totales = all.stream()
                .map(Factura::getTotal)
                .toList();
        assertTrue("Debe contener total 100.0", totales.contains(100.0));
        assertTrue("Debe contener total 200.0", totales.contains(200.0));
    }
}

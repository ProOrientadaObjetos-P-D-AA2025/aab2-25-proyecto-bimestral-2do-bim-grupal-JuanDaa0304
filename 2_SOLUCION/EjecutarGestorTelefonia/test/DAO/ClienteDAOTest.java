package DAO;

import modelo.Cliente;
import modelo.Dispositivo;
import modelo.PlanPostPagoMegas;
import modelo.PlanPostPagoMinutos;
import util.ConexionSQLite;
import org.junit.Before;
import org.junit.Test;
import dao.ClienteDAO;
import dao.PlanDAO;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class ClienteDAOTest {

    private ClienteDAO clienteDAO;
    private PlanDAO planDAO;

    @Before
    public void setUp() throws Exception {
        clienteDAO = new ClienteDAO();
        planDAO = new PlanDAO();

        try (Connection c = ConexionSQLite.conectar(); Statement s = c.createStatement()) {
            s.executeUpdate("DELETE FROM facturas");
            s.executeUpdate("DELETE FROM clientes");
            s.executeUpdate("DELETE FROM planes");
        }
    }

    @Test
    public void testCrearYBuscarSimple() throws Exception {
        Cliente c = new Cliente("123", "Juan", "Quito", "juan@u.com", "01/01/2000");
        assertTrue("Debería crear el cliente", clienteDAO.crear(c));

        Cliente buscado = clienteDAO.buscarPorCedula("123");
        assertNotNull("Cliente no encontrado", buscado);
        assertEquals("Juan", buscado.getNombre());
        assertNull("No debe tener dispositivo", buscado.getDispositivo());
    }

    @Test
    public void testCrearConDispositivoYPlanes() throws Exception {
        PlanPostPagoMegas planMegas = new PlanPostPagoMegas(2.5, 5.0, 10.0);
        int pid1 = planDAO.crear(planMegas);

        PlanPostPagoMinutos planMin = new PlanPostPagoMinutos(100, 0.1, 50, 0.2);
        int pid2 = planDAO.crear(planMin);

        Cliente c = new Cliente("999", "Ana", "Loja", "ana@l.com", "02/02/1990");
        c.setDispositivo(new Dispositivo("XBrand", "X1", "09990000"));
        c.setPlanPrincipal(planMegas);
        c.setPlanSecundario(planMin);
        assertTrue("Crear cliente con dispositivo y dos planes", clienteDAO.crear(c));

        Cliente f = clienteDAO.buscarPorCedula("999");
        assertNotNull("Cliente recuperado debe existir", f);
        assertEquals("XBrand", f.getDispositivo().getMarca());
        assertNotNull("Debe tener plan principal", f.getPlanPrincipal());
        assertNotNull("Debe tener plan secundario", f.getPlanSecundario());
        assertTrue(f.getPlanPrincipal() instanceof PlanPostPagoMegas);
        assertTrue(f.getPlanSecundario() instanceof PlanPostPagoMinutos);
    }

    @Test
    public void testListarYEliminar() throws Exception {
        clienteDAO.crear(new Cliente("A1", "X", "C", "x@c.com", "01/01/01"));
        clienteDAO.crear(new Cliente("B2", "Y", "D", "y@d.com", "02/02/02"));

        List<Cliente> all = clienteDAO.listar();
        assertTrue("Debe listar al menos 2 clientes", all.size() >= 2);

        assertTrue("Eliminar cliente B2", clienteDAO.eliminar("B2"));
        assertNull("B2 ya no debe existir", clienteDAO.buscarPorCedula("B2"));
    }
}

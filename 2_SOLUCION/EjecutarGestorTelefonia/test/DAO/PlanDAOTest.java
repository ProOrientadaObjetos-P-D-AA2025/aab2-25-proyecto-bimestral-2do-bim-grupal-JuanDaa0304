package DAO;

import modelo.Plan;
import modelo.PlanPostPagoMegas;
import modelo.PlanPostPagoMinutos;
import modelo.PlanPostPagoMinutosMegas;
import modelo.PlanPostPagoMinutosMegasEconomico;
import util.ConexionSQLite;
import org.junit.Before;
import org.junit.Test;
import dao.PlanDAO;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class PlanDAOTest {

    private PlanDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new PlanDAO();
        try (Connection c = ConexionSQLite.conectar(); Statement s = c.createStatement()) {
            s.executeUpdate("DELETE FROM planes");
        }
    }

    @Test
    public void testCrearYBuscarMegas() throws Exception {
        Plan p = new PlanPostPagoMegas(1.5, 5.0, 10.0);
        int id = dao.crear(p);
        assertTrue(id > 0);
        Plan fetched = dao.buscarPorId(id);
        assertTrue(fetched instanceof PlanPostPagoMegas);
        PlanPostPagoMegas m = (PlanPostPagoMegas) fetched;
        assertEquals(1.5, m.getMegasEnGB(), 1e-6);
        assertEquals(5.0, m.getCostoPorGB(), 1e-6);
        assertEquals(10.0, m.getTarifaBase(), 1e-6);
    }

    @Test
    public void testCrearYEliminarMinutos() throws Exception {
        Plan p = new PlanPostPagoMinutos(100, 0.1, 50, 0.2);
        int id = dao.crear(p);
        assertTrue(dao.eliminar(id));
        assertNull(dao.buscarPorId(id));
    }

    @Test
    public void testListarMixtoYEconomico() throws Exception {
        dao.crear(new PlanPostPagoMinutosMegas(200, 0.05, 2.0, 8.0));
        dao.crear(new PlanPostPagoMinutosMegasEconomico(100, 0.1, 1.0, 4.0, 15.0));
        List<Plan> all = dao.listar();
        assertEquals(2, all.size());
    }
}

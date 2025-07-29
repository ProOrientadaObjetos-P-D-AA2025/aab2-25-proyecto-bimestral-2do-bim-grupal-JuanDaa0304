package controlador;

import dao.ClienteDAO;
import dao.PlanDAO;
import modelo.Cliente;
import modelo.Plan;

import java.sql.SQLException;
import java.util.List;

public class ClienteControlador {

    private final ClienteDAO dao = new ClienteDAO();
    private final PlanDAO pDao = new PlanDAO();

    public boolean crear(Cliente c) {
        try {
            return dao.crear(c);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente buscar(String cedula) {
        try {
            return dao.buscarPorCedula(cedula);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> listar() {
        try {
            return dao.listar();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean actualizar(Cliente c) {
        try {
            return dao.actualizar(c);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(String cedula) {
        try {
            return dao.eliminar(cedula);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean asignarPlan(String cedula, int planId, int posicion) {
        try {
            Cliente c = dao.buscarPorCedula(cedula);
            if (c == null) {
                return false;
            }
            Plan p = pDao.buscarPorId(planId);
            if (p == null) {
                return false;
            }
            c.setPlan(p, posicion);
            return dao.actualizar(c);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

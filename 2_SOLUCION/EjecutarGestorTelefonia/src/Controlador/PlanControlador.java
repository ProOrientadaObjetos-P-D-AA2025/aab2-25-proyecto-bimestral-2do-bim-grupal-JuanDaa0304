package controlador;

import dao.PlanDAO;
import modelo.Plan;

import java.sql.SQLException;
import java.util.List;

public class PlanControlador {

    private final PlanDAO dao = new PlanDAO();
    private PlanDAO planDAO = new PlanDAO(); 


    public int crear(Plan p) {
        try {
            return dao.crear(p);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Plan buscar(int id) {
        try {
            return dao.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Plan> listar() {
        try {
            return dao.listar();
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public boolean eliminar(int id) {
        try {
            return dao.eliminar(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean puedeRegistrarTipo(String cedula, String nuevoTipo) {
    try {
        List<String> tipos = planDAO.obtenerTiposDePlanesPorCliente(cedula);
        if (tipos.contains(nuevoTipo)) {
            return true; 
        }
        return tipos.size() < 2; 
    } catch (SQLException er) {
        er.printStackTrace();
        return false; 
    }
}

}

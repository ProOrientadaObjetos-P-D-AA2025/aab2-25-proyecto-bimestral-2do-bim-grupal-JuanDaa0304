package dao;

import modelo.*;
import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    public int crear(Plan p) throws SQLException {
        String sql = """
            INSERT INTO planes
              (tipo, tarifaBase,
               minutosNacionales, costoMinutoNacional,
               minutosInternacionales, costoMinutoInternacional,
               megasEnGB, costoPorGB,
               porcentajeDescuento)
            VALUES (?,?,?,?,?,?,?,?,?)
            """;
        try (Connection conn = ConexionSQLite.conectar();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTipo());
            ps.setDouble(2, p.getTarifaBase());
            ps.setNull(3, Types.INTEGER);
            ps.setNull(4, Types.DOUBLE);
            ps.setNull(5, Types.INTEGER);
            ps.setNull(6, Types.DOUBLE);
            ps.setNull(7, Types.DOUBLE);
            ps.setNull(8, Types.DOUBLE);
            ps.setNull(9, Types.DOUBLE);

            if (p instanceof PlanPostPagoMegas m) {
                ps.setDouble(7, m.getMegasEnGB());
                ps.setDouble(8, m.getCostoPorGB());
            } else if (p instanceof PlanPostPagoMinutos pm) {
                ps.setInt(3, pm.getMinutosNacionales());
                ps.setDouble(4, pm.getCostoMinutoNacional());
                ps.setInt(5, pm.getMinutosInternacionales());
                ps.setDouble(6, pm.getCostoMinutoInternacional());
            } else if (p instanceof PlanPostPagoMinutosMegas pmm) {
                ps.setInt(3, pmm.getMinutos());
                ps.setDouble(4, pmm.getCostoMinuto());
                ps.setDouble(7, pmm.getMegasEnGB());
                ps.setDouble(8, pmm.getCostoPorGB());
            } else if (p instanceof PlanPostPagoMinutosMegasEconomico e) {
                ps.setInt(3, e.getMinutos());
                ps.setDouble(4, e.getCostoMinuto());
                ps.setDouble(7, e.getMegasEnGB());
                ps.setDouble(8, e.getCostoPorGB());
                ps.setDouble(9, e.getPorcentajeDescuento());
            } else {
                throw new IllegalArgumentException("Plan no soportado");
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
        }
    }

    public Plan buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM planes WHERE id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return switch (rs.getString("tipo")) {
                    case "PostPago Megas" ->
                        new PlanPostPagoMegas(
                        rs.getDouble("megasEnGB"),
                        rs.getDouble("costoPorGB"),
                        rs.getDouble("tarifaBase")
                        );
                    case "PostPago Minutos" ->
                        new PlanPostPagoMinutos(
                        rs.getInt("minutosNacionales"),
                        rs.getDouble("costoMinutoNacional"),
                        rs.getInt("minutosInternacionales"),
                        rs.getDouble("costoMinutoInternacional")
                        );
                    case "PostPago Minutos+Megas" ->
                        new PlanPostPagoMinutosMegas(
                        rs.getInt("minutosNacionales"),
                        rs.getDouble("costoMinutoNacional"),
                        rs.getDouble("megasEnGB"),
                        rs.getDouble("costoPorGB")
                        );
                    case "PostPago Minutos+Megas Económico" ->
                        new PlanPostPagoMinutosMegasEconomico(
                        rs.getInt("minutosNacionales"),
                        rs.getDouble("costoMinutoNacional"),
                        rs.getDouble("megasEnGB"),
                        rs.getDouble("costoPorGB"),
                        rs.getDouble("porcentajeDescuento")
                        );
                    default ->
                        null;
                };
            }
        }
    }

    public List<Plan> listar() throws SQLException {
        List<Plan> lista = new ArrayList<>();
        String sql = "SELECT id FROM planes";
        try (Connection conn = ConexionSQLite.conectar(); Statement st = conn.createStatement(); 
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(buscarPorId(rs.getInt("id")));
            }
        }
        return lista;
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM planes WHERE id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    public List<String> obtenerTiposDePlanesPorCliente(String cedula) throws SQLException {
        List<String> tipos = new ArrayList<>();
        String sql = "SELECT DISTINCT tipo FROM planes WHERE cedulaCliente = ?";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cedula);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tipos.add(rs.getString("tipo"));
            }
        }

        return tipos;
    }
}

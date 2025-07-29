package dao;

import modelo.Cliente;
import modelo.Dispositivo;
import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean crear(Cliente c) throws SQLException {
        String sql = """
            INSERT INTO clientes
              (cedula, nombre, ciudad, email, fechaNacimiento,
               marca, modelo, numero,
               planPrincipalId, planSecundarioId)
            VALUES (?,?,?,?,?,?,?,?,?,?)
            """;
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getCedula());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getCiudad());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getFechaNacimiento());

            Dispositivo d = c.getDispositivo();
            ps.setString(6, d != null ? d.getMarca() : null);
            ps.setString(7, d != null ? d.getModelo() : null);
            ps.setString(8, d != null ? d.getNumero() : null);

            Integer id1 = c.getPlanPrincipal() != null
                    ? new PlanDAO().crear(c.getPlanPrincipal())
                    : null;
            Integer id2 = c.getPlanSecundario() != null
                    ? new PlanDAO().crear(c.getPlanSecundario())
                    : null;
            ps.setObject(9, id1);
            ps.setObject(10, id2);

            return ps.executeUpdate() == 1;
        }
    }

    public Cliente buscarPorCedula(String cedula) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE cedula = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Cliente c = new Cliente(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("ciudad"),
                        rs.getString("email"),
                        rs.getString("fechaNacimiento")
                );
                String marca = rs.getString("marca");
                if (marca != null) {
                    c.setDispositivo(new Dispositivo(
                            marca, rs.getString("modelo"), rs.getString("numero")
                    ));
                }
                int pid1 = rs.getInt("planPrincipalId");
                if (!rs.wasNull()) {
                    c.setPlanPrincipal(new PlanDAO().buscarPorId(pid1));
                }
                int pid2 = rs.getInt("planSecundarioId");
                if (!rs.wasNull()) {
                    c.setPlanSecundario(new PlanDAO().buscarPorId(pid2));
                }
                return c;
            }
        }
    }

    public List<Cliente> listar() throws SQLException {
        List<Cliente> out = new ArrayList<>();
        String sql = "SELECT cedula FROM clientes";
        try (Connection conn = ConexionSQLite.conectar(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                out.add(buscarPorCedula(rs.getString("cedula")));
            }
        }
        return out;
    }

    public boolean actualizar(Cliente c) throws SQLException {
        String sql = """
            UPDATE clientes SET
              nombre=?, ciudad=?, email=?, fechaNacimiento=?,
              marca=?, modelo=?, numero=?,
              planPrincipalId=?, planSecundarioId=?
            WHERE cedula=?
            """;
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getCiudad());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getFechaNacimiento());

            Dispositivo d = c.getDispositivo();
            ps.setString(5, d != null ? d.getMarca() : null);
            ps.setString(6, d != null ? d.getModelo() : null);
            ps.setString(7, d != null ? d.getNumero() : null);

            Integer id1 = c.getPlanPrincipal() != null ? new PlanDAO().crear(c.getPlanPrincipal()) : null;
            Integer id2 = c.getPlanSecundario() != null
                    ? new PlanDAO().crear(c.getPlanSecundario())
                    : null;
            ps.setObject(8, id1);
            ps.setObject(9, id2);

            ps.setString(10, c.getCedula());
            return ps.executeUpdate() == 1;
        }
    }

    public boolean eliminar(String cedula) throws SQLException {
        String sql = "DELETE FROM clientes WHERE cedula = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            return ps.executeUpdate() == 1;
        }
    }
}

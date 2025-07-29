package dao;

import modelo.Factura;
import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    public boolean crear(Factura f) throws SQLException {
        String sql = """
            INSERT INTO facturas
              (numero, cedulaCliente, nombreCliente, total, fechaGeneracion)
            VALUES (?,?,?,?,?)
            """;
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, f.getNumero());
            ps.setString(2, f.getCedulaCliente());
            ps.setString(3, f.getNombreCliente());
            ps.setDouble(4, f.getTotal());
            ps.setString(5, f.getFechaFormateada());
            return ps.executeUpdate() == 1;
        }
    }

    public List<Factura> listarPorCliente(String cedula) throws SQLException {
        List<Factura> out = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE cedulaCliente = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(new Factura(
                            rs.getString("cedulaCliente"),
                            rs.getString("nombreCliente"),
                            rs.getDouble("total")
                    ));
                }
            }
        }
        return out;
    }

    public List<Factura> listarTodas() throws SQLException {
        List<Factura> out = new ArrayList<>();
        String sql = "SELECT * FROM facturas";
        try (Connection conn = ConexionSQLite.conectar(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                out.add(new Factura(
                        rs.getString("cedulaCliente"),
                        rs.getString("nombreCliente"),
                        rs.getDouble("total")
                ));
            }
        }
        return out;
    }
}

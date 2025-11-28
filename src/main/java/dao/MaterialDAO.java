package dao;

import models.*;
import models.Material;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    public Material create(Material m) throws SQLException {
        String sql = "INSERT INTO materials(name, supplier_id, price) VALUES(?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getName());
            if (m.getSupplierId() != null) ps.setInt(2, m.getSupplierId());
            else ps.setNull(2, Types.INTEGER);
            ps.setDouble(3, m.getPrice());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) m.setId(rs.getInt(1));
            }
        }
        return m;
    }

    public Material findById(int id) throws SQLException {
        String sql = "SELECT id, name, supplier_id, price FROM materials WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                else return null;
            }
        }
    }

    public List<Material> findAll(String nameLike) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT id, name, supplier_id, price FROM materials WHERE 1=1");
        if (nameLike != null && !nameLike.isEmpty()) {
            sb.append(" AND name LIKE ?");
        }
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            if (nameLike != null && !nameLike.isEmpty()) {
                ps.setString(1, "%" + nameLike + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Material> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    public boolean update(Material m) throws SQLException {
        String sql = "UPDATE materials SET name=?, supplier_id=?, price=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            if (m.getSupplierId() != null) ps.setInt(2, m.getSupplierId());
            else ps.setNull(2, Types.INTEGER);
            ps.setDouble(3, m.getPrice());
            ps.setInt(4, m.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM materials WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Material mapRow(ResultSet rs) throws SQLException {
        Material m = new Material();
        m.setId(rs.getInt("id"));
        m.setName(rs.getString("name"));
        int sid = rs.getInt("supplier_id");
        if (!rs.wasNull()) m.setSupplierId(sid);
        m.setPrice(rs.getDouble("price"));
        return m;
    }
}

package dao;

import models.*;
import models.Project;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    public Project create(Project p) throws SQLException {
        String sql = "INSERT INTO projects(name, client, start_date, end_date, status) VALUES(?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getClient());
            ps.setString(3, p.getStartDate());
            ps.setString(4, p.getEndDate());
            ps.setString(5, p.getStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.setId(rs.getInt(1));
            }
        }
        return p;
    }

    public Project findById(int id) throws SQLException {
        String sql = "SELECT id, name, client, start_date, end_date, status FROM projects WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                else return null;
            }
        }
    }

    public List<Project> findByClient(String client) throws SQLException {
        String sql = "SELECT id, name, client, start_date, end_date, status FROM projects WHERE client LIKE ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + client + "%");
            try (ResultSet rs = ps.executeQuery()) {
                List<Project> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    public List<Project> findAll(String statusFilter, String sortBy, boolean asc) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT id, name, client, start_date, end_date, status FROM projects WHERE 1=1");
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sb.append(" AND status = ?");
        }
        if ("end_date".equalsIgnoreCase(sortBy)) {
            sb.append(" ORDER BY end_date ").append(asc ? "ASC" : "DESC");
        }
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            if (statusFilter != null && !statusFilter.isEmpty()) {
                ps.setString(1, statusFilter);
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Project> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    public boolean update(Project p) throws SQLException {
        String sql = "UPDATE projects SET name=?, client=?, start_date=?, end_date=?, status=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getClient());
            ps.setString(3, p.getStartDate());
            ps.setString(4, p.getEndDate());
            ps.setString(5, p.getStatus());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Project mapRow(ResultSet rs) throws SQLException {
        Project p = new Project();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setClient(rs.getString("client"));
        p.setStartDate(rs.getString("start_date"));
        p.setEndDate(rs.getString("end_date"));
        p.setStatus(rs.getString("status"));
        return p;
    }
}


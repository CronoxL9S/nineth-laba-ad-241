package dao;

import models.*;
import models.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public Task create(Task t) throws SQLException {
        String sql = "INSERT INTO tasks(project_id, name, employee_id, material_id, material_qty, cost, status, start_date, end_date) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getProjectId());
            ps.setString(2, t.getName());
            if (t.getEmployeeId() != null) ps.setInt(3, t.getEmployeeId());
            else ps.setNull(3, Types.INTEGER);
            if (t.getMaterialId() != null) ps.setInt(4, t.getMaterialId());
            else ps.setNull(4, Types.INTEGER);
            ps.setDouble(5, t.getMaterialQty());
            ps.setDouble(6, t.getCost());
            ps.setString(7, t.getStatus());
            ps.setString(8, t.getStartDate());
            ps.setString(9, t.getEndDate());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) t.setId(rs.getInt(1));
            }
        }
        return t;
    }

    public Task findById(int id) throws SQLException {
        String sql = "SELECT id, project_id, name, employee_id, material_id, material_qty, cost, status, start_date, end_date FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
                else return null;
            }
        }
    }

    public List<Task> findByProject(int projectId, String statusFilter) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT id, project_id, name, employee_id, material_id, material_qty, cost, status, start_date, end_date FROM tasks WHERE project_id = ?");
        if (statusFilter != null && !statusFilter.isEmpty()) {
            sb.append(" AND status = ?");
        }
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            ps.setInt(1, projectId);
            if (statusFilter != null && !statusFilter.isEmpty()) {
                ps.setString(2, statusFilter);
            }
            try (ResultSet rs = ps.executeQuery()) {
                List<Task> list = new ArrayList<>();
                while (rs.next()) list.add(mapRow(rs));
                return list;
            }
        }
    }

    public double sumCostsForProject(int projectId) throws SQLException {
        String sql = "SELECT SUM(cost) as total FROM tasks WHERE project_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
                else return 0.0;
            }
        }
    }

    public boolean update(Task t) throws SQLException {
        String sql = "UPDATE tasks SET project_id=?, name=?, employee_id=?, material_id=?, material_qty=?, cost=?, status=?, start_date=?, end_date=? WHERE id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getProjectId());
            ps.setString(2, t.getName());
            if (t.getEmployeeId() != null) ps.setInt(3, t.getEmployeeId());
            else ps.setNull(3, Types.INTEGER);
            if (t.getMaterialId() != null) ps.setInt(4, t.getMaterialId());
            else ps.setNull(4, Types.INTEGER);
            ps.setDouble(5, t.getMaterialQty());
            ps.setDouble(6, t.getCost());
            ps.setString(7, t.getStatus());
            ps.setString(8, t.getStartDate());
            ps.setString(9, t.getEndDate());
            ps.setInt(10, t.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        Task t = new Task();
        t.setId(rs.getInt("id"));
        t.setProjectId(rs.getInt("project_id"));
        t.setName(rs.getString("name"));
        int eid = rs.getInt("employee_id");
        if (!rs.wasNull()) t.setEmployeeId(eid);
        int mid = rs.getInt("material_id");
        if (!rs.wasNull()) t.setMaterialId(mid);
        t.setMaterialQty(rs.getDouble("material_qty"));
        t.setCost(rs.getDouble("cost"));
        t.setStatus(rs.getString("status"));
        t.setStartDate(rs.getString("start_date"));
        t.setEndDate(rs.getString("end_date"));
        return t;
    }
}


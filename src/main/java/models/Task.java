package models;

public class Task {
    private Integer id;
    private Integer projectId;
    private String name;
    private Integer employeeId;
    private Integer materialId;
    private double materialQty;
    private double cost;
    private String status;
    private String startDate;
    private String endDate;

    public Task() {}

    public Task(Integer id, Integer projectId, String name, Integer employeeId, Integer materialId,
                double materialQty, double cost, String status, String startDate, String endDate) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
        this.employeeId = employeeId;
        this.materialId = materialId;
        this.materialQty = materialQty;
        this.cost = cost;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getProjectId() { return projectId; }
    public void setProjectId(Integer projectId) { this.projectId = projectId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }
    public Integer getMaterialId() { return materialId; }
    public void setMaterialId(Integer materialId) { this.materialId = materialId; }
    public double getMaterialQty() { return materialQty; }
    public void setMaterialQty(double materialQty) { this.materialQty = materialQty; }
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Вартість: %.2f грн | Статус: %s | Працівник ID: %s | Матеріал ID: %s (к-ть: %.2f)",
                id, name, cost, status, employeeId, materialId, materialQty);
    }
}


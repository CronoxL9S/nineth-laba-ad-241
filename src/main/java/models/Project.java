package models;

public class Project {
    private Integer id;
    private String name;
    private String client;
    private String startDate;
    private String endDate;
    private String status;

    public Project() {}

    public Project(Integer id, String name, String client, String startDate, String endDate, String status) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("[%d] %s | Клієнт: %s | Статус: %s | Початок: %s | Кінець: %s",
                id, name, client, status, startDate, endDate != null ? endDate : "не завершено");
    }
}


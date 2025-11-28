package models;

public class Material {
    private Integer id;
    private String name;
    private Integer supplierId;
    private double price;

    public Material() {}

    public Material(Integer id, String name, Integer supplierId, double price) {
        this.id = id;
        this.name = name;
        this.supplierId = supplierId;
        this.price = price;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getSupplierId() { return supplierId; }
    public void setSupplierId(Integer supplierId) { this.supplierId = supplierId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %.2f грн/од (Постачальник ID: %s)",
                id, name, price, supplierId != null ? supplierId : "немає");
    }
}


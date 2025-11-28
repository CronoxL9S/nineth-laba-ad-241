package models;

public class Supplier {
    private Integer id;
    private String name;
    private String contact;

    public Supplier() {}

    public Supplier(Integer id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return String.format("[%d] %s (Контакт: %s)", id, name, contact);
    }
}


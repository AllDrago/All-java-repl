package models;

public class Contact {
    int id;
    int customerId;
    String name;
    String phone;
    String email;

    public Contact(int customerId, int id, String name, String phone, String email) {
        this.customerId = customerId;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

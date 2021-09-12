package Model;

import java.time.LocalDate;

public class Animal {
    private int id;
    private String name;
    private String type;
    private double weight;
    private String sterilized;
    private LocalDate dateOfReceipt;

    public Animal(int id, String name, String type, double weight, String sterilized, LocalDate dateOfReceipt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.sterilized = sterilized;
        this.dateOfReceipt = dateOfReceipt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String isSterilized() {
        return sterilized;
    }

    public void setSterilized(String sterilized) {
        this.sterilized = sterilized;
    }

    public LocalDate getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(LocalDate dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }
}

package ch.ss.entity;

import javax.validation.constraints.DecimalMin;

public class Food extends Product {

    @DecimalMin("10.00")
    private double weight;

    // Json B needs empty constructor
    public Food(){
    }

    public Food(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

package ch.ss.entity;

import javax.validation.constraints.DecimalMin;

public class Drink extends Product {

    //Filling quantity in centiliter
    @DecimalMin("50.00")
    private int fillingQuantityCL;

    // Json B needs empty constructor
    public Drink(){
    }

    public Drink(String name, double price, int quantity, int fillingQuantityCL) {
        super(name, price, quantity);
        this.fillingQuantityCL = fillingQuantityCL;
    }

    public int getFillingQuantityCL() {
        return fillingQuantityCL;
    }

    public void setFillingQuantityCL(int fillingQuantityCL) {
        this.fillingQuantityCL = fillingQuantityCL;
    }
}

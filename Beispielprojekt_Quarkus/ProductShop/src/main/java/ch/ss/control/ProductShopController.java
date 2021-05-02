package ch.ss.control;

import ch.ss.entity.Drink;
import ch.ss.entity.Food;
import ch.ss.entity.Product;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class ProductShopController {

    private List<Product> productList;

    @PostConstruct
    void init() {
        this.productList = new ArrayList<>();
        this.productList.add(new Drink("Ice Tea", 1.20, 1, 250));
        this.productList.add(new Drink("Coca Cola", 1.30, 1, 250));
        this.productList.add(new Drink("Fanta", 1.20, 1, 250));
        this.productList.add(new Food("Twix", 1.50, 1, 50));
        this.productList.add(new Food("Sugar", 1.0, 1, 500));
        this.productList.add(new Food("Flour", 1.20, 1, 500));
        this.productList.add(new Food("Toast", 3.25, 1, 500));
    }

    public List<Product> getAllProducts() {
        return this.productList;
    }

    public Food getFoodByName(String foodName) {
        for (Product product : this.productList) {
            if (product instanceof Food && product.getName().equals(foodName)) {
                return (Food) product;
            }
        }
        throw new WebApplicationException("This food does not exist.", Response.Status.BAD_REQUEST);
    }

    public Drink getDrinkByName(String drinkName) {
        for (Product product : this.productList) {
            if (product instanceof Drink && product.getName().equals(drinkName)) {
                return (Drink) product;
            }
        }
        throw new WebApplicationException("This drink does not exist.", Response.Status.BAD_REQUEST);
    }

    public String addFood(Food food) {
        if (!this.checkIfProductExists(food.getName())) {
            this.productList.add(food);
            return "Food successfully added";
        } else {
            throw new WebApplicationException("This food already exists.", Response.Status.BAD_REQUEST);
        }
    }

    public String addDrink(Drink drink) {
        if (!this.checkIfProductExists(drink.getName())) {
            this.productList.add(drink);
            return "Drink successfully added";
        } else {
            throw new WebApplicationException("This drink already exists.", Response.Status.BAD_REQUEST);
        }
    }

    public String deleteFood(Food food) {
        if (this.checkIfProductExists(food.getName())) {
            this.productList.remove(food);
            return "Food successfully removed";
        } else {
            throw new WebApplicationException("This food does not exist.", Response.Status.BAD_REQUEST);
        }
    }

    public String deleteDrink(Drink drink) {
        if (this.checkIfProductExists(drink.getName())) {
            this.productList.remove(drink);
            return "Drink successfully removed";
        } else {
            throw new WebApplicationException("This drink does not exist.", Response.Status.BAD_REQUEST);
        }
    }

    private boolean checkIfProductExists(String productName) {
        boolean result = false;
        for (Product product : this.productList) {
            if (product.getName().equals(productName)) {
                result = true;
            }
        }
        return result;
    }
}

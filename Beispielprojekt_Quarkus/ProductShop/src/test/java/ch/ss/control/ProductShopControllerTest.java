package ch.ss.control;

import ch.ss.entity.Drink;
import ch.ss.entity.Food;
import ch.ss.entity.Product;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class ProductShopControllerTest {

    private ProductShopController productShopController;
    private List<Product> productList;
    private Drink drink;
    private Food food;

    @BeforeEach
    private void init() {
        this.productShopController = mock(ProductShopController.class);

        this.productList = new ArrayList<>();
        this.productList.add(new Drink("Ice Tea", 1.20, 1, 250));
        this.productList.add(new Drink("Coca Cola", 1.30, 1, 250));
        this.productList.add(new Drink("Fanta", 1.20, 1, 250));
        this.productList.add(new Food("Twix", 1.50, 1, 50));
        this.productList.add(new Food("Sugar", 1.0, 1, 500));
        this.productList.add(new Food("Flour", 1.20, 1, 500));
        this.productList.add(new Food("Toast", 3.25, 1, 500));

        this.drink = new Drink("Red Bull", 1.20, 1, 250);

        this.food = new Food("Mars", 1.50, 1, 50);
    }

    @Test
    @DisplayName("Get all products")
    void getAllProducts() {
        when(this.productShopController.getAllProducts()).thenReturn(productList);
        List<Product> products = this.productShopController.getAllProducts();
        assertThat(products.size(), is(7));
        assertThat(products.get(1).getName(), is("Coca Cola"));
        assertThat(products.get(4).getName(), is("Sugar"));
    }

    @Test
    @DisplayName("Get food by name")
    void getProductByName() {
        when(this.productShopController.getFoodByName("Twix")).thenReturn((Food) this.productList.get(3));
        Food food = (Food) this.productShopController.getFoodByName("Twix");
        assertThat(food.getName(), is("Twix"));
        assertThat(food.getPrice(), is(1.50));
        assertThat(food.getQuantity(), is(1));
        assertThat(food.getWeight(), is(50.0));
    }

    @Test
    @DisplayName("Get drink by name")
    void getDrinkByName() {
        when(this.productShopController.getDrinkByName("Coca Cola")).thenReturn((Drink) this.productList.get(1));
        Drink drink = (Drink) this.productShopController.getDrinkByName("Coca Cola");
        assertThat(drink.getName(), is("Coca Cola"));
        assertThat(drink.getPrice(), is(1.30));
        assertThat(drink.getQuantity(), is(1));
        assertThat(drink.getFillingQuantityCL(), is(250));
    }

    @Test
    @DisplayName("Get drink by name that does not exist")
    void getDrinkByNameThatDoesNotExist() {
        when(this.productShopController.getDrinkByName("Pepsi")).thenThrow(new ProductShopException("This drink does not exist.", Response.Status.BAD_REQUEST));
        try {
            Drink drink = (Drink) this.productShopController.getDrinkByName("Pepsi");
        } catch (ProductShopException productShopException) {
            assertThat(productShopException.getResponse().getHeaders().get("Reason").toString(), is("[This drink does not exist.]"));
        }
    }

    @Test
    @DisplayName("Get food by name that does not exist")
    void getFoodByNameThatDoesNotExist() {
        when(this.productShopController.getFoodByName("Mentos")).thenThrow(new ProductShopException("This food does not exist.", Response.Status.BAD_REQUEST));
        try {
            Food food = (Food) this.productShopController.getFoodByName("Mentos");
        } catch (ProductShopException productShopException) {
            assertThat(productShopException.getResponse().getHeaders().get("Reason").toString(), is("[This food does not exist.]"));
        }
    }

    @Test
    @DisplayName("Add food, successful")
    void addFoodSuccessful() {
        when(this.productShopController.addFood(this.food)).thenReturn("Food successfully added");
        String resultMessage = this.productShopController.addFood(this.food);
        assertThat(resultMessage, is("Food successfully added"));
    }

    @Test
    @DisplayName("Add drink, successful")
    void addProductSuccessful() {
        when(this.productShopController.addDrink(this.drink)).thenReturn("Drink successfully added");
        String resultMessage = this.productShopController.addDrink(this.drink);
        assertThat(resultMessage, is("Drink successfully added"));
    }

    @Test
    @DisplayName("Add food, unsuccessful")
    void addFoodUnsuccessful() {
        when(this.productShopController.addFood(this.food)).thenThrow(new ProductShopException("This food already exists.", Response.Status.BAD_REQUEST));
        try {
            this.productShopController.addFood(this.food);
        } catch (ProductShopException productShopException) {
            assertThat(productShopException.getResponse().getHeaders().get("Reason").toString(), is("[This food already exists.]"));
        }
    }

    @Test
    @DisplayName("Add drink, unsuccessful")
    void addDrinkUnsuccessful() {
        when(this.productShopController.addDrink(this.drink)).thenThrow(new ProductShopException("This drink already exists.", Response.Status.BAD_REQUEST));
        try {
            this.productShopController.addDrink(this.drink);
        } catch (ProductShopException productShopException) {
            assertThat(productShopException.getResponse().getHeaders().get("Reason").toString(), is("[This drink already exists.]"));
        }
    }

    @Test
    @DisplayName("Delete food, successful")
    void deleteFoodSuccessful() {
        when(this.productShopController.deleteFood(this.food)).thenReturn("Food successfully removed");
        String resultMessage = this.productShopController.deleteFood(this.food);
        assertThat(resultMessage, is("Food successfully removed"));
    }

    @Test
    @DisplayName("Delete drink, successful")
    void deleteProductSuccessful() {
        when(this.productShopController.deleteDrink(this.drink)).thenReturn("Drink successfully removed");
        String resultMessage = this.productShopController.deleteDrink(this.drink);
        assertThat(resultMessage, is("Drink successfully removed"));
    }

    @Test
    @DisplayName("Delete food, unsuccessful")
    void deleteFoodUnsuccessful() {
        when(this.productShopController.deleteFood(this.food)).thenThrow(new ProductShopException("This food does not exist.", Response.Status.BAD_REQUEST));
        try {
            this.productShopController.deleteFood(this.food);
        } catch (ProductShopException productShopException) {
            assertThat(productShopException.getResponse().getHeaders().get("Reason").toString(), is("[This food does not exist.]"));
        }
    }

    @Test
    @DisplayName("Delete drink, unsuccessful")
    void deleteDrinkUnsuccessful() {
        when(this.productShopController.deleteDrink(this.drink)).thenThrow(new ProductShopException("This drink does not exist.", Response.Status.BAD_REQUEST));
        try {
            this.productShopController.deleteDrink(this.drink);
        } catch (ProductShopException productShopException) {
            assertThat(productShopException.getResponse().getHeaders().get("Reason").toString(), is("[This drink does not exist.]"));
        }
    }
}
package ch.ss.boundary;

import boundary.ProductShopRestClient;
import ch.ss.entity.Drink;
import ch.ss.entity.Food;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import javax.inject.Inject;

import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductShopResourceTest {

    @Inject
    @RestClient
    ProductShopRestClient productShopRestClient;

    @Test
    @DisplayName("Ping productshop")
    void pingProductShop() {
        String result = this.productShopRestClient.ping();
        assertThat(result, is("Hello from the product shop!"));
    }

    @Test
    @DisplayName("Get drink by name")
    void getDrinkByName() {
        Drink drink = this.productShopRestClient.getDrinkByName("Coca Cola");
        assertThat(drink.getName(), is("Coca Cola"));
        assertThat(drink.getPrice(), is(1.30));
        assertThat(drink.getQuantity(), is(1));
        assertThat(drink.getFillingQuantityCL(), is(250));
    }

    @Test
    @DisplayName("Get food by name")
    void getFoodByName() {
        Food food = this.productShopRestClient.getFoodByName("Twix");
        assertThat(food.getName(), is("Twix"));
        assertThat(food.getPrice(), is(1.50));
        assertThat(food.getQuantity(), is(1));
        assertThat(food.getWeight(), is(50.0));
    }

    @Test
    @DisplayName("Add food")
    void addFood() {
        Food mentos = new Food("Mentos", 1.10, 1, 50);
        String result = this.productShopRestClient.addFood(mentos);
        assertThat(result, is("Food successfully added"));
    }

    @Test
    @DisplayName("Add drink")
    void addDrink() {
        Drink pepsi = new Drink("Pepsi", 1.10, 1, 50);
        String result = this.productShopRestClient.addDrink(pepsi);
        assertThat(result, is("Drink successfully added"));
    }

    @Test
    @DisplayName("Delete food")
    void deleteFood() {
        Food twix = new Food("Twix", 1.50, 1, 50);
        String result = this.productShopRestClient.deleteFood(twix);
        assertThat(result, is("Food successfully removed"));
    }

    @Test
    @DisplayName("Delete drink")
    void deleteDrink() {
        Drink cocaCola = new Drink("Coca Cola", 1.30, 1, 250);
        String result = this.productShopRestClient.deleteDrink(cocaCola);
        assertThat(result, is("Drink successfully removed"));
    }
}

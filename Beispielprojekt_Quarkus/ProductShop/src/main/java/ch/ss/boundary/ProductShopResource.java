package ch.ss.boundary;

import ch.ss.control.ProductShopController;
import ch.ss.entity.Drink;
import ch.ss.entity.Food;
import ch.ss.entity.Product;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.RegistryType;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/productShop")
public class ProductShopResource {

    @Inject
    ProductShopController productShopController;

    @Inject
    @RegistryType(type = MetricRegistry.Type.APPLICATION)
    MetricRegistry registry;

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        return "Hello from the product shop!";
    }

    @GET
    @Path("getAllProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        registry.counter("getAllProductsCount").inc();
        return this.productShopController.getAllProducts();
    }

    @POST
    @Path("getFoodByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Food getFoodByName(@QueryParam("foodName") @NotBlank @NotEmpty String foodName) {
        registry.counter("getFoodByNameCount").inc();
        return this.productShopController.getFoodByName(foodName);
    }

    @POST
    @Path("getDrinkByName")
    @Produces(MediaType.APPLICATION_JSON)
    public Drink getDrinkByName(@QueryParam("drinkName") @NotBlank @NotEmpty String drinkName) {
        registry.counter("getDrinkByNameCount").inc();
        return this.productShopController.getDrinkByName(drinkName);
    }

    @POST
    @Path("addFood")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addFood(@Valid Food food) {
        registry.counter("addFoodCount").inc();
        return this.productShopController.addFood(food);
    }

    @POST
    @Path("addDrink")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String addDrink(@Valid Drink drink) {
        registry.counter("addDrinkCount").inc();
        return this.productShopController.addDrink(drink);
    }

    @DELETE
    @Path("deleteFood")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteFood(@Valid Food food) {
        registry.counter("deleteFoodCount").inc();
        return this.productShopController.deleteFood(food);
    }

    @DELETE
    @Path("deleteDrink")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteDrink(@Valid Drink drink) {
        registry.counter("deleteDrinkCount").inc();
        return this.productShopController.deleteDrink(drink);
    }
}
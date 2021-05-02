package boundary;

import ch.ss.entity.Drink;
import ch.ss.entity.Food;
import ch.ss.entity.Product;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient(baseUri = "http://localhost:8080")
@Path("/productShop")
public interface ProductShopRestClient {

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_PLAIN)
    String ping();

    @GET
    @Path("getAllProducts")
    @Produces(MediaType.APPLICATION_JSON)
    List<Product> getAllProducts();

    @POST
    @Path("getFoodByName")
    @Produces(MediaType.APPLICATION_JSON)
    Food getFoodByName(@QueryParam("foodName") @NotBlank @NotEmpty String foodName);

    @POST
    @Path("getDrinkByName")
    @Produces(MediaType.APPLICATION_JSON)
    Drink getDrinkByName(@QueryParam("drinkName") @NotBlank @NotEmpty String drinkName);

    @POST
    @Path("addFood")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    String addFood(@Valid Food food);

    @POST
    @Path("addDrink")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    String addDrink(@Valid Drink drink);

    @DELETE
    @Path("deleteFood")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    String deleteFood(@Valid Food food);

    @DELETE
    @Path("deleteDrink")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
   String deleteDrink(@Valid Drink drink);
}

package Zoo.Shop;

import Zoo.Animal.Leopard;
import Zoo.Animal.RedPanda;
import Zoo.Animal.WildBoar;
import Zoo.Habitat.Climate;
import Zoo.Habitat.Habitat;
import Zoo.Rng;


import java.util.ArrayList;

/**
 * This is a singleton class that exposes the functionality of a shop.
 * This is where the zoo can buy new animals and habitats.
 */
public class Shop {
    /**
     * Represents a product in the shop.
     *
     * @param price  the price of the object to buy.
     * @param object the object to buy.
     */
    public record Product(int price, Object object) {
    }


    /**
     * The number of items in the shop.
     */
    public static int shopSize = 10;

    /**
     * @return a new randomly generated habitat product.
     */
    private static Product randomHabitatProduct() {
        // Generate a random number between 0 and 99.
        int random = Rng.getRng().randomNumber(0, 99);
        if (random < 10) {
            return new Product(300, new Habitat("Tropical Climate Habitat", Climate.TROPICAL));
        } else if (random < 30) {
            return new Product(200, new Habitat("Dry Climate Habitat", Climate.TROPICAL));
        } else if (random < 50) {
            return new Product(150, new Habitat("Temperate Climate Habitat", Climate.TEMPERATE));
        } else if (random < 95) {
            return new Product(300, new Habitat("Continental Climate Habitat", Climate.CONTINENTAL));
        } else {
            return new Product(600, new Habitat("Polar Climate Habitat", Climate.POLAR));
        }
    }

    /**
     * @return a new randomly generated animal product.
     */
    private static Product randomAnimalProduct() {
        // Generate a random number between 0 and 99.
        int random = Rng.getRng().randomNumber(0, 99);
        if (random < 5) {
            return new Product(100, Leopard.randomLeopard());
        } else if (random < 15) {
            return new Product(90, RedPanda.randomRedPanda());
        } else {
            return new Product(30, WildBoar.randomWildBoar());
        }
    }


    /**
     * @return a new random product.
     */
    private static Product randomProduct() {
        // Generate a random number between 0 and 9.
        int random = Rng.getRng().randomNumber(0, 9);

        if (random <= 1) { // 20% chance of the product being a habitat.
            return randomHabitatProduct();
        }
        return randomAnimalProduct();
    }

    /**
     * The list of products in the shop.
     */
    private final ArrayList<Product> products;

    /**
     * The instance of the shop.
     */
    private static Shop shop = null;

    /**
     * @return the instance of the shop.
     */
    public static Shop getShop() {
        if (shop == null) {
            shop = new Shop();
        }
        return shop;
    }

    private Shop() {
        products = new ArrayList<>();
    }

    /**
     * Refill the shop with new items (animals or habitats).
     * Each item is randomly generated.
     */
    public void refill() {
        products.clear();
        for (int i = 0; i < shopSize; i++) {
            products.add(randomProduct());
        }
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

}

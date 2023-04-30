package Zoo.Shop;

import Zoo.Animal.Leopard;
import Zoo.Animal.RedPanda;
import Zoo.Animal.WildBoar;
import Zoo.Habitat.Climate;
import Zoo.Habitat.Habitat;
import Zoo.Purchesable;
import Zoo.Rng;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a singleton class that exposes the functionality of a shop.
 * This is where the zoo can buy new animals and habitats.
 */
public class Shop {

    /**
     * The number of items in the shop.
     */
    public static int shopSize = 10;

    /**
     * @return a new randomly generated habitat product.
     */
    private static Purchesable randomHabitatProduct() {
        // Generate a random number between 0 and 99.
        int random = Rng.getRng().randomNumber(0, 99);
        if (random < 10) {
            return new Habitat("Tropical Climate Habitat", Climate.TROPICAL);
        } else if (random < 30) {
            return new Habitat("Dry Climate Habitat", Climate.DRY);
        } else if (random < 50) {
            return new Habitat("Temperate Climate Habitat", Climate.TEMPERATE);
        } else if (random < 95) {
            return new Habitat("Continental Climate Habitat", Climate.CONTINENTAL);
        } else {
            return new Habitat("Polar Climate Habitat", Climate.POLAR);
        }
    }

    /**
     * @return a new randomly generated animal product.
     */
    private static Purchesable randomAnimalProduct() {
        // Generate a random number between 0 and 99.
        int random = Rng.getRng().randomNumber(0, 99);
        if (random < 5) {
            return Leopard.randomLeopard();
        } else if (random < 15) {
            return RedPanda.randomRedPanda();
        } else {
            return WildBoar.randomWildBoar();
        }
    }


    /**
     * @return a new random product.
     */
    private static Purchesable randomProduct() {
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
    private final ArrayList<Purchesable> products;

    /**
     * The instance of the shop.
     */
    private static Shop shop = null;

    /**
     * @return the instance of the shop.
     */
    public static Shop getInstance() {
        if (shop == null) {
            shop = new Shop();
            shop.refill();
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

    /**
     * Get an unmodifiable list of products.
     *
     * @return the list of products.
     */
    public List<Purchesable> getProducts() {
        return Collections.unmodifiableList(products);
    }


    /**
     * Removes the given product from the list of products.
     * This method should be called when a product is purchased.
     *
     * @param product the product to remove.
     */
    public void removeProduct(Purchesable product) {
        products.remove(product);
    }

}

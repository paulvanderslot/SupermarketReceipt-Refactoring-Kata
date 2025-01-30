package dojo.supermarket.model;

import dojo.supermarket.model.offers.Offer;

import java.util.*;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return Collections.unmodifiableList(items);
    }

    void addItem(Product product) {
        addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return Collections.unmodifiableMap(productQuantities);
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product product : productQuantities().keySet()) {
            double quantity = productQuantities.get(product);
            if (thereIsAOfferForThisProduct(offers, product)) {
                Discount discount = determineDiscount(offers, catalog, product, quantity);
                if (discount != null) receipt.addDiscount(discount);
            }
        }
    }

    private static boolean thereIsAOfferForThisProduct(Map<Product, Offer> offers, Product product) {
        return offers.containsKey(product);
    }

    private static Discount determineDiscount(Map<Product, Offer> offers, SupermarketCatalog catalog, Product product, double quantity) {
        Offer offer = offers.get(product);
        return offer.determineDiscount(product, quantity, catalog.getUnitPrice(product));

    }

}

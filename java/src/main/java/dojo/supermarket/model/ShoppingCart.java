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

    void handleOffers(Receipt receipt, Collection<Offer> offers, SupermarketCatalog catalog) {
        for (Product product : productQuantities().keySet()) {
            double quantity = productQuantities.get(product);
            Optional<Offer> offer = findOffer(offers, product, quantity);
            offer.ifPresent(value -> receipt.addDiscount(value.determineDiscount(product, quantity, catalog.getUnitPrice(product))));
        }
    }

    private static Optional<Offer> findOffer(Collection<Offer> offers, Product product, double quantity) {
        return offers.stream().filter(it -> it.doesApplyFor(product, quantity)).findAny();
    }


}

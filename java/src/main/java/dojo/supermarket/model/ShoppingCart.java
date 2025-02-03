package dojo.supermarket.model;

import dojo.supermarket.model.offers.Offer;
import dojo.supermarket.model.offers.Offers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ShoppingCart {

    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return productQuantities.keySet().stream().map(it -> new ProductQuantity(it, productQuantities.get(it))).collect(Collectors.toList());
    }


    public void addItemQuantity(Product product, double quantity) {
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Offers offers, SupermarketCatalog catalog) {
        for (ProductQuantity productQuantity : getItems()) {
            Optional<Offer> offer = offers.findMatchingOffer(productQuantity);
            offer.ifPresent(value -> receipt.addDiscount(value.determineDiscount(productQuantity, catalog.getUnitPrice(productQuantity.getProduct()))));
        }

    }


}

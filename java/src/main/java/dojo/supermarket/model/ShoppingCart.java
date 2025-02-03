package dojo.supermarket.model;

import dojo.supermarket.model.offers.Offers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

public class ShoppingCart {

    private final Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return productQuantities.keySet().stream()
                                .map(it -> new ProductQuantity(it, productQuantities.get(it)))
                                .collect(Collectors.toList());
    }

    public void addItemQuantity(Product product, double quantity) {
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    List<Discount> determineDiscounts(Offers offers, SupermarketCatalog catalog) {
        return getItems().stream()
                         .map(productQuantity -> toDiscount(productQuantity, offers, catalog.getUnitPrice(productQuantity.getProduct())))
                         .filter(Optional::isPresent)
                         .map(Optional::get)
                         .collect(toUnmodifiableList());
    }

    private Optional<Discount> toDiscount(ProductQuantity productQuantity, Offers offers, double unitPrice) {
        return offers.findMatchingOffer(productQuantity)
                     .map(value -> value.determineDiscount(productQuantity, unitPrice));
    }


}

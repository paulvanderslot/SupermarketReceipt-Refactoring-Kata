package dojo.supermarket.model;

import java.util.*;

public class ShoppingCart {

    private static final double HUNDRED_PERCENT = 100.0;
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
        double unitPrice = catalog.getUnitPrice(product);
        double originalPrice = unitPrice * quantity;
        int quantityRoundedOffToBelow = (int) quantity;

        if (offer.offerType == SpecialOfferType.TWO_FOR_AMOUNT && quantityRoundedOffToBelow >= 2) {
            double offerPrice = offer.argument;
            double priceForAllDiscountedProducts = offerPrice * (quantityRoundedOffToBelow / 2);
            double priceForAllNonDiscountedProducts = quantityRoundedOffToBelow % 2 * unitPrice;
            double totalPriceWithDiscount = priceForAllDiscountedProducts + priceForAllNonDiscountedProducts;
            double amountOfDiscount = originalPrice - totalPriceWithDiscount;
            return new Discount(product, "2 for " + offerPrice, -amountOfDiscount);
        }
        if (offer.offerType == SpecialOfferType.THREE_FOR_TWO && quantityRoundedOffToBelow > 2) {
            int timesTheOfferApplies = quantityRoundedOffToBelow / 3;
            double offerPrice = 2 * unitPrice;
            double discountAmount = originalPrice - ((timesTheOfferApplies * offerPrice) + quantityRoundedOffToBelow % 3 * unitPrice);
            return new Discount(product, "3 for 2", -discountAmount);
        }
        if (offer.offerType == SpecialOfferType.PERCENTAGE_DISCOUNT) {
            double percentageOff = offer.argument;
            double discountAmount = -quantity * unitPrice * percentageOff / HUNDRED_PERCENT;
            return new Discount(product, percentageOff + "% off", discountAmount);
        }
        if (offer.offerType == SpecialOfferType.FIVE_FOR_AMOUNT && quantityRoundedOffToBelow >= 5) {
            double discountTotal = originalPrice - (offer.argument + quantityRoundedOffToBelow % 5 * unitPrice);
            return new Discount(product, "5 for " + offer.argument, -discountTotal);
        }
        return null;
    }

}

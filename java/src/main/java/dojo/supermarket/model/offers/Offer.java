package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public interface Offer {

    Product getProduct();

    Discount determineDiscount(Product product, double quantity, double unitPrice);

    boolean doesApplyFor(Product product, double quantity);
}


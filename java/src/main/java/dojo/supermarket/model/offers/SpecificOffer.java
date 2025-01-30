package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public interface SpecificOffer {

    Product getProduct();

    Discount determineDiscount(Product product, double quantity, double unitPrice);
}


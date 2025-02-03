package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ProductQuantity;

public interface Offer {

    Product getProduct();

    Discount determineDiscount(ProductQuantity productQuantity, double unitPrice);

    boolean doesApplyFor(ProductQuantity productQuantity);
}


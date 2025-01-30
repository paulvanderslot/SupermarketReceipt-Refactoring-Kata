package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class FiveForAmountOffer implements Offer {

    private final double offerPrice;
    private final Product product;

    public FiveForAmountOffer(Product product, double offerPrice) {
        this.offerPrice = offerPrice;
        this.product = product;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public Discount determineDiscount(Product product, double quantity, double unitPrice) {
        if (!doesApplyFor(product, quantity)) return null;

        double originalPrice = unitPrice * quantity;
        int quantityRoundedOffToBelow = (int) quantity;
        double priceForAllDiscountedProducts = offerPrice * (quantityRoundedOffToBelow / 5);
        double priceForAllNonDiscountedProducts = quantityRoundedOffToBelow % 5 * unitPrice;
        double totalPriceWithDiscount = priceForAllDiscountedProducts + priceForAllNonDiscountedProducts;
        double discountTotal = originalPrice - totalPriceWithDiscount;
        return new Discount(product, getOfferDescription(), -discountTotal);
    }

    @Override
    public boolean doesApplyFor(Product product, double quantity) {
        return product.equals(this.product) && quantity >= 5;
    }

    private String getOfferDescription() {
        return "5 for " + offerPrice;
    }
}

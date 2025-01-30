package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class PercentageDiscountOffer implements Offer {

    private static final String OFFER_DESCRIPTION = "3 for 2";
    private final Product product;
    private final int percentageDiscount;

    public PercentageDiscountOffer(Product product, int percentageDiscount) {
        this.product = product;
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public Discount determineDiscount(Product product, double quantity, double unitPrice) {
        double originalPrice = unitPrice * quantity;
        double discountAmount = originalPrice * percentageDiscount / 100;
        return new Discount(product, getOfferDescription(), -discountAmount);
    }

    private String getOfferDescription() {
        return percentageDiscount + "% off";
    }

}

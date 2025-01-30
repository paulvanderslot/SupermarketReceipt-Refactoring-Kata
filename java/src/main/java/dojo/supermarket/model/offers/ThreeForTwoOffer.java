package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class ThreeForTwoOffer implements SpecificOffer {

    private static final String OFFER_DESCRIPTION = "3 for 2";
    private final Product product;

    public ThreeForTwoOffer(Product product) {
        this.product = product;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public Discount determineDiscount(Product product, double quantity, double unitPrice) {
        double originalPrice = unitPrice * quantity;
        int timesTheOfferApplies = (int) quantity / 3;

        double offerPrice = 2 * unitPrice;
        double discountAmount = originalPrice - ((timesTheOfferApplies * offerPrice) + (int) quantity % 3 * unitPrice);
        return new Discount(product, OFFER_DESCRIPTION, -discountAmount);
    }

}

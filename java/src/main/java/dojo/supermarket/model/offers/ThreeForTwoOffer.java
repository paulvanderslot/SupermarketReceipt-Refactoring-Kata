package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ProductQuantity;

public class ThreeForTwoOffer implements Offer {

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
    public Discount determineDiscount(ProductQuantity productQuantity, double unitPrice) {
        if (!doesApplyFor(productQuantity)) return null;

        double originalPrice = unitPrice * productQuantity.getQuantity();
        int timesTheOfferApplies = (int) productQuantity.getQuantity() / 3;

        double offerPrice = 2 * unitPrice;
        double discountAmount = originalPrice - ((timesTheOfferApplies * offerPrice) + (int) productQuantity.getQuantity() % 3 * unitPrice);
        return new Discount(product, OFFER_DESCRIPTION, -discountAmount);
    }

    @Override
    public boolean doesApplyFor(ProductQuantity productQuantity) {
        return productQuantity.getProduct().equals(this.product) && productQuantity.getQuantity() >= 3;
    }

}

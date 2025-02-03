package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ProductQuantity;

public class TwoForAmountOffer implements Offer {

    private final double offerPrice;
    private final Product product;

    public TwoForAmountOffer(Product product, double offerPrice) {
        this.offerPrice = offerPrice;
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
        int quantityRoundedOffToBelow = (int) productQuantity.getQuantity();
        double priceForAllDiscountedProducts = offerPrice * (quantityRoundedOffToBelow / 2);
        double priceForAllNonDiscountedProducts = quantityRoundedOffToBelow % 2 * unitPrice;
        double totalPriceWithDiscount = priceForAllDiscountedProducts + priceForAllNonDiscountedProducts;
        double amountOfDiscount = originalPrice - totalPriceWithDiscount;
        return new Discount(product, getOfferDescription(), -amountOfDiscount);
    }

    private String getOfferDescription() {
        return "2 for " + offerPrice;
    }

    @Override
    public boolean doesApplyFor(ProductQuantity productQuantity) {
        return productQuantity.getProduct().equals(this.product) && productQuantity.getQuantity() >= 2;
    }

}

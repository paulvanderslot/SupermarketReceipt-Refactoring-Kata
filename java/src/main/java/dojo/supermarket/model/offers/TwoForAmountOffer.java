package dojo.supermarket.model.offers;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;

public class TwoForAmountOffer implements SpecificOffer {

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
    public Discount determineDiscount(Product product, double quantity, double unitPrice) {
        double originalPrice = unitPrice * quantity;
        int quantityRoundedOffToBelow = (int) quantity;
        double priceForAllDiscountedProducts = offerPrice * (quantityRoundedOffToBelow / 2);
        double priceForAllNonDiscountedProducts = quantityRoundedOffToBelow % 2 * unitPrice;
        double totalPriceWithDiscount = priceForAllDiscountedProducts + priceForAllNonDiscountedProducts;
        double amountOfDiscount = originalPrice - totalPriceWithDiscount;
        return new Discount(product, getOfferDescription(), -amountOfDiscount);
    }

    private String getOfferDescription() {
        return "2 for" + offerPrice;
    }
}

package dojo.supermarket.model;

import dojo.supermarket.model.offers.Offer;
import dojo.supermarket.model.offers.Offers;

import java.util.List;

public class Teller {

    private final SupermarketCatalog catalog;
    private final Offers offers = new Offers();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Offer offer) {
        offers.add(offer);
    }


    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq : productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }
        theCart.handleOffers(receipt, offers, catalog);

        return receipt;
    }
}

package dojo.supermarket.model;

import dojo.supermarket.model.offers.Offer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Teller {

    private final SupermarketCatalog catalog;
    private final Collection<Offer> offers = new ArrayList<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(Offer offer) {
        Optional<Offer> offerForSameProduct = offers.stream().filter(it -> it.getProduct() == offer.getProduct()).findAny();
        if (offerForSameProduct.isPresent()) {
            offers.remove(offerForSameProduct.get());
        }
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

package dojo.supermarket.model.offers;

import dojo.supermarket.model.ProductQuantity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Offers {

    private final Collection<Offer> offers = new ArrayList<>();

    public void add(Offer offer) {
        Optional<Offer> offerForSameProduct = offers.stream().filter(offerForSameProduct(offer)).findAny();
        offerForSameProduct.ifPresent(offers::remove);
        offers.add(offer);
    }

    private static Predicate<Offer> offerForSameProduct(Offer offer) {
        return it -> it.getProduct().equals(offer.getProduct());
    }

    public Stream<Offer> stream() {
        return offers.stream();
    }

    public Optional<Offer> findMatchingOffer(ProductQuantity productQuantity) {
        return offers.stream().filter(it -> it.doesApplyFor(productQuantity)).findAny();

    }
}

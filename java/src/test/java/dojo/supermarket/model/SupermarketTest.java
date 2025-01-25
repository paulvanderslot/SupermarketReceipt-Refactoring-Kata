package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dojo.supermarket.model.SpecialOfferType.PERCENTAGE_DISCOUNT;
import static dojo.supermarket.model.SpecialOfferType.TWO_FOR_AMOUNT;

class SupermarketTest {
    private SupermarketCatalog catalog;
    private final Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
    private final Product apples = new Product("apples", ProductUnit.KILO);
    private final double applePrice = 1.99;
    private final double toothbrushPrice = 0.99;

    @BeforeEach
    void beforeEach() {
        catalog = new FakeCatalog();
        catalog.addProduct(toothbrush, toothbrushPrice);
        catalog.addProduct(apples, applePrice);
    }

    @Test
    void shouldGiveNoDiscountsWhenNoneApply() {
        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(PERCENTAGE_DISCOUNT, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        double appleQuantity = 2.5;
        cart.addItemQuantity(apples, appleQuantity);

        Approvals.verify(new ReceiptPrinter().printReceipt(teller.checksOutArticlesFrom(cart)));

    }

    @Test
    void shouldApply10percentDiscount() {
        Teller teller = new Teller(catalog);
        double discountPercentage = 10.0;
        teller.addSpecialOffer(PERCENTAGE_DISCOUNT, toothbrush, discountPercentage);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 3;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        Approvals.verify(new ReceiptPrinter().printReceipt(teller.checksOutArticlesFrom(cart)));
    }

    @Test
    void shouldApply20percentDiscount() {
        Teller teller = new Teller(catalog);
        double discountPercentage = 20.0;
        teller.addSpecialOffer(PERCENTAGE_DISCOUNT, toothbrush, discountPercentage);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 3;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        Approvals.verify(new ReceiptPrinter().printReceipt(teller.checksOutArticlesFrom(cart)));
    }

    @Test
    void shouldApply2ForAmountWhenTwoAreBought() {
        Teller teller = new Teller(catalog);
        double twoForAmountPrice = 1.50;
        teller.addSpecialOffer(TWO_FOR_AMOUNT, toothbrush, twoForAmountPrice);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 2;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        Approvals.verify(new ReceiptPrinter().printReceipt(teller.checksOutArticlesFrom(cart)));

    }

    @Test
    void shouldApply2ForAmountWhen3AreBought() {
        Teller teller = new Teller(catalog);
        double twoForAmountPrice = 1.50;
        teller.addSpecialOffer(TWO_FOR_AMOUNT, toothbrush, twoForAmountPrice);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 3;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        Approvals.verify(new ReceiptPrinter().printReceipt(teller.checksOutArticlesFrom(cart)));

    }

    @Test
    void shouldApply2ForAmountWhen4AreBought() {
        Teller teller = new Teller(catalog);
        double twoForAmountPrice = 1.50;
        teller.addSpecialOffer(TWO_FOR_AMOUNT, toothbrush, twoForAmountPrice);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 4;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        Approvals.verify(new ReceiptPrinter().printReceipt(teller.checksOutArticlesFrom(cart)));

    }

}

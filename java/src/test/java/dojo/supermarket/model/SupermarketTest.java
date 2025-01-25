package dojo.supermarket.model;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dojo.supermarket.model.SpecialOfferType.PERCENTAGE_DISCOUNT;
import static dojo.supermarket.model.SpecialOfferType.TWO_FOR_AMOUNT;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertThat(receipt.getTotalPrice()).isCloseTo(appleQuantity * applePrice, Offset.offset(0.01));
        assertThat(receipt.getDiscounts()).isEqualTo(emptyList());
        assertThat(receipt.getItems().size()).isEqualTo(1);
        verifyReceiptItem(receipt.getItems().getFirst(), apples, applePrice, appleQuantity);
    }

    @Test
    void shouldApply10percentDiscount() {
        Teller teller = new Teller(catalog);
        double discountPercentage = 10.0;
        teller.addSpecialOffer(PERCENTAGE_DISCOUNT, toothbrush, discountPercentage);
        double discountPrice = toothbrushPrice * (1 - discountPercentage / 100);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 3;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertThat(receipt.getTotalPrice()).isCloseTo(toothbrushQuantity * discountPrice, Offset.offset(0.01));
        assertThat(receipt.getDiscounts().size()).isEqualTo(1);
        Discount tenPercentDiscount = receipt.getDiscounts().getFirst();
        assertThat(tenPercentDiscount.getDiscountAmount()).isCloseTo(-toothbrushQuantity * toothbrushPrice * discountPercentage / 100, Offset.offset(0.01));

        assertThat(receipt.getItems().size()).isEqualTo(1);
        verifyReceiptItem(receipt.getItems().getFirst(), toothbrush, toothbrushPrice, toothbrushQuantity);
    }

    @Test
    void shouldApply20percentDiscount() {
        Teller teller = new Teller(catalog);
        double discountPercentage = 20.0;
        teller.addSpecialOffer(PERCENTAGE_DISCOUNT, toothbrush, discountPercentage);
        double discountPrice = toothbrushPrice * (1 - discountPercentage / 100);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 3;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertThat(receipt.getTotalPrice()).isCloseTo(toothbrushQuantity * discountPrice, Offset.offset(0.01));
        assertThat(receipt.getDiscounts().size()).isEqualTo(1);
        Discount tenPercentDiscount = receipt.getDiscounts().getFirst();
        assertThat(tenPercentDiscount.getDiscountAmount()).isCloseTo(-toothbrushQuantity * toothbrushPrice * discountPercentage / 100, Offset.offset(0.01));

        assertThat(receipt.getItems().size()).isEqualTo(1);
        verifyReceiptItem(receipt.getItems().getFirst(), toothbrush, toothbrushPrice, toothbrushQuantity);
    }

    @Test
    void shouldApply2ForAmountWhenTwoAreBought() {
        Teller teller = new Teller(catalog);
        double twoForAmountPrice = 1.50;
        teller.addSpecialOffer(TWO_FOR_AMOUNT, toothbrush, twoForAmountPrice);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 2;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertThat(receipt.getTotalPrice()).isCloseTo(twoForAmountPrice, Offset.offset(0.01));
        assertThat(receipt.getDiscounts().size()).isEqualTo(1);
        Discount discount = receipt.getDiscounts().getFirst();
        assertThat(discount.getDiscountAmount()).isCloseTo(-(2 * toothbrushPrice - twoForAmountPrice), Offset.offset(0.01));

        assertThat(receipt.getItems().size()).isEqualTo(1);
        verifyReceiptItem(receipt.getItems().getFirst(), toothbrush, toothbrushPrice, toothbrushQuantity);
    }

    @Test
    void shouldApply2ForAmountWhen3AreBought() {
        Teller teller = new Teller(catalog);
        double twoForAmountPrice = 1.50;
        teller.addSpecialOffer(TWO_FOR_AMOUNT, toothbrush, twoForAmountPrice);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 3;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertThat(receipt.getTotalPrice()).isCloseTo(toothbrushPrice + twoForAmountPrice, Offset.offset(0.01));
        assertThat(receipt.getDiscounts().size()).isEqualTo(1);
        Discount discount = receipt.getDiscounts().getFirst();
        assertThat(discount.getDiscountAmount()).isCloseTo(-(2 * toothbrushPrice - twoForAmountPrice), Offset.offset(0.01));

        assertThat(receipt.getItems().size()).isEqualTo(1);
        verifyReceiptItem(receipt.getItems().getFirst(), toothbrush, toothbrushPrice, toothbrushQuantity);
    }

    @Test
    void shouldApply2ForAmountWhen4AreBought() {
        Teller teller = new Teller(catalog);
        double twoForAmountPrice = 1.50;
        teller.addSpecialOffer(TWO_FOR_AMOUNT, toothbrush, twoForAmountPrice);

        ShoppingCart cart = new ShoppingCart();
        double toothbrushQuantity = 4;
        cart.addItemQuantity(toothbrush, toothbrushQuantity);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertThat(receipt.getTotalPrice()).isCloseTo(2 * twoForAmountPrice, Offset.offset(0.01));
        assertThat(receipt.getDiscounts().size()).isEqualTo(1);
        Discount discount = receipt.getDiscounts().getFirst();
        assertThat(discount.getDiscountAmount()).isCloseTo(-2 * (2 * toothbrushPrice - twoForAmountPrice), Offset.offset(0.01));

        assertThat(receipt.getItems().size()).isEqualTo(1);
        verifyReceiptItem(receipt.getItems().getFirst(), toothbrush, toothbrushPrice, toothbrushQuantity);
    }


    private void verifyReceiptItem(ReceiptItem receiptItem, Product product, double productPrice, double productQuantity) {
        assertEquals(product, receiptItem.getProduct());
        assertEquals(productPrice, receiptItem.getPrice());
        assertEquals(productQuantity * productPrice, receiptItem.getTotalPrice());
        assertEquals(productQuantity, receiptItem.getQuantity());
    }

}

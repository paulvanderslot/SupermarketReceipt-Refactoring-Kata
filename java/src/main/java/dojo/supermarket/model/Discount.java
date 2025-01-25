package dojo.supermarket.model;

public class Discount implements Comparable<Discount> {

    private final String description;
    private final double discountAmount;
    private final Product product;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public int compareTo(Discount o) {
        return product.getName().compareTo(o.product.getName());
    }
}

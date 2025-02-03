package dojo.supermarket.model;

import java.util.HashMap;
import java.util.Map;

public class FakeCatalog implements SupermarketCatalog {
    private Map<String, Product> products = new HashMap<>();
    private Map<String, Double> prices = new HashMap<>();

    @Override
    public void addProduct(Product product, double unitPrice) {
        this.products.put(product.getName(), product);
        this.prices.put(product.getName(), unitPrice);
    }

    @Override
    public double getUnitPrice(Product p) {
        return this.prices.get(p.getName());
    }
}

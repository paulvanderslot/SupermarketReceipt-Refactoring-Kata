package dojo.supermarket.model;

public interface SupermarketCatalog {

    void addProduct(Product product, double unitPrice);

    double getUnitPrice(Product product);
}

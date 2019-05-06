package fr.catalog.business;

public class Product {
    public String ean;
    public String name;
    public String description;
    public byte[] picture;
    public String pictureUrl;

    private Product(String ean, String name, String description, byte[] picture) {
        this.ean = ean;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public static Product newProduct(String ean, String name, String description, byte[] picture) {
        return new Product(ean, name, description, picture);
    }
}

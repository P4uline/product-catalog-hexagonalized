package fr.catalog.business;

public class Product {
    private String ean;
    private String name;
    private String description;
    private byte[] picture;
    private String pictureUrl;

    private Product(String ean, String name, String description, byte[] picture) {
        this.ean = ean;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public static Product newProduct(String ean, String name, String description, byte[] picture) {
        return new Product(ean, name, description, picture);
    }

    public String getEan() {
        return ean;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}

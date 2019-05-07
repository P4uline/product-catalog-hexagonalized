package fr.catalog.infra.web;

import fr.catalog.business.Product;
import play.data.validation.Constraints;

public class Item {

    @Constraints.Required
    public String number;

    @Constraints.Required
    public String name;
    
    public String description;
    public byte[] image;
    public String imageUrl;
    
    public Item(String number, String name, String description, byte[] image, String imageUrl) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.image = image;
        this.imageUrl = imageUrl;
    }

    public static Product toProduct(Item item) {
        return Product.newProduct(item.number, item.name, item.description, item.image);
    }

    public static Item toItem(Product product) {
        return new Item(product.ean, product.name, product.description, product.picture, product.pictureUrl);
    }
}

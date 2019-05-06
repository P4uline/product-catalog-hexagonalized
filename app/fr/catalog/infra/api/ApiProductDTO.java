package fr.catalog.infra.api;

public class ApiProductDTO {

    public String ean;
    public String name;
    public String description;
    public byte[] picture;
    public String pictureUrl;

    private ApiProductDTO(String ean, String name, String description, byte[] picture) {
        this.ean = ean;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }

    public static ApiProductDTO newApiProductDTO(String ean, String name, String description, byte[] picture) {
        return new ApiProductDTO(ean, name, description, picture);
    }
}

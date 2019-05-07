package fr.catalog.infra.web;

import com.google.common.io.Files;
import fr.catalog.business.PortProductCatalogService;
import fr.catalog.business.PortEventService;
import play.api.Play;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CatalogController extends Controller {

    private final FormFactory playForm;

    @Inject
    private PortEventService eventService;

    @Inject
    private PortProductCatalogService portProductcatalogService;
    
    @Inject
    public CatalogController(FormFactory formFactory) {
        this.playForm = formFactory;
    }

    public Result findAllItems() {
        return ok(fr.catalog.infra.web.views.html.list.render(portProductcatalogService.allProducts().stream().map(p -> Item.toItem(p)).collect(Collectors.toList())));
    }

    public Result newItem() {
        return ok(fr.catalog.infra.web.views.html.newItem.render(playForm.form(Item.class)));
    }

    public Result detail(String ean) {
        List<Action> finalActions = eventService.findEvents().stream().map(e -> Action.toAction(e)).collect(Collectors.toList());

        Optional<Item> optionalItem =  portProductcatalogService.findProductByEan(ean).map(p -> Item.toItem(p));
        return optionalItem //
                .map( item -> ok(fr.catalog.infra.web.views.html.detail.render(item, finalActions))) //
                .orElseGet(() -> notFound("Le produit '" + ean + "' n'a pas été trouvé")); //
    }

    public Result editItem(String ean) {
        return portProductcatalogService.findProductByEan(ean) //
                .map( product -> ok(fr.catalog.infra.web.views.html.editItem.render(playForm.form(Item.class).fill(Item.toItem(product))))) //
                .orElseGet(() -> notFound("Le produit '" + ean + "' n'a pas été trouvé")); //
    }

    public Result submitEditItem(String ean) {
        Form<Item> itemForm = playForm.form(Item.class).bindFromRequest();
        if (itemForm.hasErrors()) {
            return badRequest(fr.catalog.infra.web.views.html.editItem.render(itemForm));
        }
        Item item = itemForm.get();
        Http.MultipartFormData.FilePart<File> picture = request().body().<File>asMultipartFormData().getFile("picture");
        if (picture == null) {
            flash("error", "Missing file");
            return badRequest();
        }
        item.image = getBytesFromFile(picture);
        portProductcatalogService.updateProduct(Item.toProduct(item));
        flash("success", "New product created");
        return ok(fr.catalog.infra.web.views.html.list.render(portProductcatalogService.allProducts().stream().map(p -> Item.toItem(p)).collect(Collectors.toList())));
    }

    public Result submitNewItem() {
        Form<Item> itemForm = playForm.form(Item.class).bindFromRequest();
        if (itemForm.hasErrors()) {
            return badRequest(fr.catalog.infra.web.views.html.newItem.render(itemForm));
        }
        Item item = itemForm.get();
        Http.MultipartFormData.FilePart<File> picture = request().body().<File>asMultipartFormData().getFile("image");
        if (picture == null) {
            flash("error", "Missing file");
            return badRequest();
        }
        item.image = getBytesFromFile(picture);
        portProductcatalogService.registerNewProduct(Item.toProduct(item));
        flash("success", "New item created");
        return ok(fr.catalog.infra.web.views.html.list.render(portProductcatalogService.allProducts().stream().map(p -> Item.toItem(p)).collect(Collectors.toList())));
    }

    private byte[] getBytesFromFile(Http.MultipartFormData.FilePart<File> picture) {
        File file = picture.getFile();
        byte[] arrays = new byte[0];
        try {
            arrays = Files.toByteArray(file);
        } catch (IOException e) {
            return null;
        }
        if (arrays.length == 0) {
            return null;
        }
        return arrays;
    }

    public Result picture(String ean) {
        return portProductcatalogService.findProductByEan(ean) //
                .map(product -> ok(product.picture)) //
                .orElseGet(() -> ok(Play.current().getFile("public/images/favicon.png"))); //
    }

    public Result delete(String ean) {
        portProductcatalogService.deleteProduct(ean);
        flash("success", "Product deleted");
        return redirect(fr.catalog.infra.web.routes.CatalogController.findAllItems());
    }

    public Result prepareLoadSamples() {
        return ok(fr.catalog.infra.web.views.html.prepareLoadSamples.render());
    }

    public Result loadSamples() {
        String filename = "public/hikea-names.csv";
        portProductcatalogService.loadFromFile(filename);
        
        flash("success", "Loaded a CSV file");
        return redirect(fr.catalog.infra.web.routes.CatalogController.findAllItems());
    }

}
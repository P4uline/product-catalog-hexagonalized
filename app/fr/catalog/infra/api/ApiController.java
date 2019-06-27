package fr.catalog.infra.api;

import fr.catalog.business.PortProductCatalogService;
import play.api.mvc.AbstractController;
import play.api.mvc.ControllerComponents;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;

import static play.mvc.Results.notFound;

public class ApiController extends AbstractController {

    @Inject
    private PortProductCatalogService portProductCatalogService;

    public ApiController(ControllerComponents controllerComponents) {
        super(controllerComponents);
    }

    public Result consulterDossier(String ean) {
        return portProductCatalogService.findProductByEan(ean) //
                .map( p -> Results.ok(Json.toJson(ApiProductDTO.newApiProductDTO(p.getEan(), p.getName(), p.getDescription(), p.getPicture())))) //
                .orElseGet(() -> notFound("Le produit '" + ean + "' n'a pas été trouvé")); //
    }

}

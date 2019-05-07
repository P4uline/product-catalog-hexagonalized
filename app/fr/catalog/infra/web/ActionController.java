package fr.catalog.infra.web;

import fr.catalog.business.PortEventService;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class ActionController extends Controller {

    @Inject
    private PortEventService eventService;

    public Result listActions() {
        return ok(fr.catalog.infra.web.views.html.listAction.render(eventService.findEvents().stream().map(e -> Action.toAction(e)).collect(Collectors.toList())));
    }
}

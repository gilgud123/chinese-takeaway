package be.kdv.takeaway.controller;

import be.kdv.takeaway.bootstrap.Bootstrap;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.service.MealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class MealController {

    private final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping(path = "/meals/search/allergies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getAllergyFreeMeals(@RequestBody String... allergies) {

        List<Meal> allergyFreeMeals = mealService.excludeAllergy();

        Resources<Meal> resources = new Resources<Meal>(allergyFreeMeals);

        resources.add(linkTo(methodOn(MealController.class).getAllergyFreeMeals()).withSelfRel());

        return ResponseEntity.ok(resources);
    }

}


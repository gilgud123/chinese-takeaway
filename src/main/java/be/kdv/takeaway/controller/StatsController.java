package be.kdv.takeaway.controller;

import be.kdv.takeaway.model.MealStats;
import be.kdv.takeaway.service.MealStatsService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class StatsController {

    private final MealStatsService mealStatsService;

    public StatsController(MealStatsService mealStatsService) {
        this.mealStatsService = mealStatsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> showStats(){

        MealStats stats = mealStatsService.showStats();

        Resource<MealStats> resource = new Resource<>(stats);

        resource.add(linkTo(methodOn(StatsController.class).showStats()).withSelfRel());

        return ResponseEntity.ok(resource);
    }
}

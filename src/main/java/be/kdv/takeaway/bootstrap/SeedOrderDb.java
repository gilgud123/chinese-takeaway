package be.kdv.takeaway.bootstrap;

import be.kdv.takeaway.model.*;
import be.kdv.takeaway.repository.MealRepository;
import be.kdv.takeaway.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Component
@Order(2)
public class SeedOrderDb implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeedOrderDb.class);

    private final MealRepository mealRepository;
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public SeedOrderDb(
            MealRepository mealRepository,
            OrderRepository orderRepository,
            MongoTemplate mongoTemplate
    ) {
        this.mealRepository = mealRepository;
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
    	
    	//mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
    	//LOGGER.info("Cleared old data");

        if ((mealRepository.findAll().isEmpty() || orderRepository.findAll().isEmpty())) {

            //creating dummy meals
            Meal meal1 = Meal.builder()
                             .menuNumber(1)
                             .name("basic")
                             .description("Basic set")
                             .allergies(Arrays.asList(Allergy.NUTS, Allergy.LACTOSE))
                             .build();

            Meal meal2 = Meal.builder()
                             .menuNumber(2)
                             .name("medium")
                             .description("Peking duck")
                             .allergies(Arrays.asList(Allergy.GLUTEN, Allergy.NUTS))
                             .build();
         
            Meal meal3 = Meal.builder()
                             .menuNumber(2)
                             .name("Lux")
                             .description("Chef's speciality")
                             .allergies(Arrays.asList(Allergy.GLUTEN, Allergy.SHELLFISH))
                             .build();

            mealRepository.save(meal1);
            mealRepository.save(meal2);
            mealRepository.save(meal3);
            LOGGER.info("3 meals created");

            //creating dummy orders
            MealOrder order1 = MealOrder.builder()
                                .customerName("Peter Pan")
                                .meals(Arrays.asList(meal1, meal2))
                                .status(Status.PREPARING)
                                .createdAt(Instant.now())
                                .readyAt(Instant.now()
                                                .plus(30, ChronoUnit.MINUTES))
                                .build();
            MealOrder order2 = MealOrder.builder()
                                .customerName("Caption Hook")
                                .meals(Arrays.asList(meal2, meal3))
                                .status(Status.PREPARING)
                                .createdAt(Instant.now())
                                .readyAt(Instant.now()
                                                .plus(30, ChronoUnit.MINUTES))
                                .build();
            MealOrder order3 = MealOrder.builder()
                                .customerName("Wendy")
                                .meals(Arrays.asList(meal3, meal1))
                                .status(Status.REQUESTED)
                                .createdAt(Instant.now())
                                .readyAt(Instant.now()
                                                .plus(30, ChronoUnit.MINUTES))
                                .build();

            orderRepository.save(order1);
            orderRepository.save(order2);
            orderRepository.save(order3);
            LOGGER.info("3 orders created");
        }
    }
}

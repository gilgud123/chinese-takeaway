package be.kdv.takeaway.bootstrap;

import be.kdv.takeaway.model.Allergy;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import be.kdv.takeaway.repository.MealRepository;
import be.kdv.takeaway.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class SeedMongoDb implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeedMongoDb.class);

    private final MealRepository mealRepository;
    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public SeedMongoDb(
            MealRepository mealRepository,
            OrderRepository orderRepository,
            MongoTemplate mongoTemplate) {
        this.mealRepository = mealRepository;
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    //twee command runners aanmaken
    @Override
    public void run(String... args) throws Exception {

        if ((mealRepository.findAll().isEmpty() || orderRepository.findAll().isEmpty())) {

            //clear old broken data
            mongoTemplate.getCollectionNames().forEach(mongoTemplate::dropCollection);
            LOGGER.info("Cleared old data");

                    //creating dummy meals
                    Meal meal1 = Meal.builder()
                            .menuNumber(1)
                            .name("basic")
                            .description("Basic set")
                            .allergies(new ArrayList<Allergy>(Arrays.asList(Allergy.NUTS, Allergy.LACTOSE)))
                            .build();
                    Meal meal2 = Meal.builder()
                            .menuNumber(2)
                            .name("medium")
                            .description("Peking duck")
                            .allergies(new ArrayList<Allergy>(Arrays.asList(Allergy.GLUTEN, Allergy.NUTS)))
                            .build();

                    Meal meal3 = Meal.builder()
                            .menuNumber(2)
                            .name("Lux")
                            .description("Chef's speciality")
                            .allergies(new ArrayList<Allergy>(Arrays.asList(Allergy.GLUTEN, Allergy.SHELLFISH)))
                            .build();

                    mealRepository.save(meal1);
                    mealRepository.save(meal2);
                    LOGGER.info("2 meals created");

                    Order order1 = Order.builder()
                                .customerName("Peter Pan")
                                .meals(new ArrayList<Meal>(Arrays.asList(meal1, meal2)))
                                .status(Status.REQUESTED)
                                .createdAt(Instant.now())
                                .readyAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                                .build();
                    Order order2 = Order.builder()
                                .customerName("Caption Hook")
                                .meals(new ArrayList<Meal>(Arrays.asList(meal2, meal3)))
                                .status(Status.PREPARING)
                                .createdAt(Instant.now())
                                .readyAt(Instant.now()
                                        .plus(30, ChronoUnit.MINUTES))
                                .build();

                        orderRepository.save(order1);
                        orderRepository.save(order2);
                        LOGGER.info("2 orders created");
        }
    }
}
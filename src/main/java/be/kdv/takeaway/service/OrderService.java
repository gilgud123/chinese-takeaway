package be.kdv.takeaway.service;

import be.kdv.takeaway.bootstrap.SeedMongoDb;
import be.kdv.takeaway.command.OrderCommand;
import be.kdv.takeaway.exception.EntityNotFoundException;
import be.kdv.takeaway.exception.InputNotValidException;
import be.kdv.takeaway.exception.MealNotFoundException;
import be.kdv.takeaway.exception.OrderNotFoundException;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import be.kdv.takeaway.repository.MealRepository;
import be.kdv.takeaway.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.kdv.takeaway.model.Status.REQUESTED;

// TODO: format the class properly

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeedMongoDb.class);

    private final MealRepository mealRepository;
    private final OrderRepository orderRepository;
    private final MealStatsService mealStatsService;

    public OrderService(
            MealRepository mealRepository,
            OrderRepository orderRepository,
            MealStatsService mealStatsService
    ) {
        this.mealRepository = mealRepository;
        this.orderRepository = orderRepository;
        this.mealStatsService = mealStatsService;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(String id) {
        // TODO Very bad practice. When a resource is not found, throw an exception and a 404 response code
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getAllOrdersNotDone() {
        Optional<List<Order>> optionalOrders = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.PREPARING, REQUESTED);
        // TODO: nice usage of the lamda notation! Kudos!!
        return optionalOrders.orElseThrow(OrderNotFoundException::new);
    }

    // TODO: correct the typo in the method name
    public Order firstFirstRequestedOrder() {
        return orderRepository.findByStatusInOrderByCreatedAtAsc(REQUESTED).orElseThrow(OrderNotFoundException::new).get(0);
    }

    public Order takeOrder(OrderCommand orderCommand) {
        // TODO: Due to the validation at controller level, the orderCommand parameter can never be null

        // TODO: Make the variable final
        final Instant createdAt = Instant.now();
        List<Exception> exceptions = new ArrayList<>();

        Order order = Order.builder()
                .customerName(orderCommand.getCustomerName())
                .meals(new ArrayList<>())
                .status(REQUESTED)
                .createdAt(createdAt)
                .readyAt(createdAt.plus(30, ChronoUnit.MINUTES))
                .build();

        // TODO: use proper camelcase for mealnr
        // TODO: what will happen when they enter 20 meals and only 1 is wrong? Wouldn't it be better if the other 19
        // meals would be stored and only the incorrect one will be reported?
        orderCommand.getMeals().forEach(mealNr -> {
            Meal meal = Meal.builder().menuNumber(mealNr).build();
            Example<Meal> example = Example.of(meal);
            if(mealRepository.findOne(example).isPresent()) {
                meal = mealRepository.findOne(example).get();
                // TODO: make a method that adds a meal to the order's meal list in the Order class itself
                order.addMealToOrder(meal);
                LOGGER.info("Meal name is {}", meal.getName());
                mealStatsService.addStats(meal.getMenuNumber());
            }else {
                exceptions.add(new MealNotFoundException(mealNr));
            }
        });
        if(!exceptions.isEmpty()) {
            exceptions.forEach(e -> LOGGER.info("The following exception was thrown {}", e.getLocalizedMessage()));
        }
        return orderRepository.save(order);
    }

    public Order changeStatus(String id, Status status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> findByCustormerName(String name) {
        return orderRepository.findByCustomerName(name).orElseThrow(() -> new EntityNotFoundException(Order.class));
    }

}

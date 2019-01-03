package be.kdv.takeaway.service;

import be.kdv.takeaway.bootstrap.SeedMongoDb;
import be.kdv.takeaway.command.OrderCommand;
import be.kdv.takeaway.exception.EntityNotFoundException;
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
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.kdv.takeaway.model.Status.REQUESTED;

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
        return orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
    }

    public List<Order> getAllOrdersNotDone() {
        Optional<List<Order>> optionalOrders = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.PREPARING, REQUESTED);
        return optionalOrders.orElseThrow(OrderNotFoundException::new);
    }

    public Order findFirstRequestedOrder() {
        return orderRepository.findByStatusInOrderByCreatedAtAsc(REQUESTED).orElseThrow(OrderNotFoundException::new).get(0);
    }

    public Order takeOrder(OrderCommand orderCommand) {
        final Instant createdAt = Instant.now();
        Order order = Order.builder()
                .customerName(orderCommand.getCustomerName())
                .meals(new ArrayList<>())
                .status(REQUESTED)
                .createdAt(createdAt)
                .readyAt(createdAt.plus(30, ChronoUnit.MINUTES))
                .build();

        List<Exception> exceptions = new ArrayList<>();

        orderCommand.getMeals().forEach(mealNr -> {
            Meal meal = Meal.builder().menuNumber(mealNr).build();
            Example<Meal> example = Example.of(meal);
            if(mealRepository.findOne(example).isPresent()) {
                meal = mealRepository.findOne(example).get();
                order.addMealToOrder(meal);
                LOGGER.info("Meal name is {}", meal.getName());
                mealStatsService.addStats(meal.getMenuNumber());
            }else {
                exceptions.add(new MealNotFoundException(mealNr));
            }
        });
        if(!exceptions.isEmpty()) {
            exceptions.forEach(e -> LOGGER.info(e.getLocalizedMessage()));
        }
        return orderRepository.save(order);
    }

    public Order changeStatus(String id, Status status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public List<Order> findByCustormerName(String name, String surname) {
        Order order = Order.builder().customerName(name).customerSurname(surname).build();
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id", "meals")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<Order> example = Example.of(order, matcher);
        List<Order> orders = orderRepository.findAll(example);
        LOGGER.info("The following orders match the parameters: {}", orders);
        return orders;
    }

}

package be.kdv.takeaway.service;

import be.kdv.takeaway.bootstrap.SeedMongoDb;
import be.kdv.takeaway.command.OrderCommand;
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

    // TODO: in Java there is the convention that constants should be preceded by "static final"
    private final static Logger LOGGER = LoggerFactory.getLogger(SeedMongoDb.class);

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

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Order getById(String id){
        // TODO Very bad practice. When a resource is not found, throw an exception and a 404 response code
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrdersNotDone(){
        Optional<List<Order>> optionalOrders = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.PREPARING, REQUESTED);
        // TODO: nice usage of the lamda notation! Kudos!!
        return optionalOrders.orElseThrow(OrderNotFoundException::new);
    }

    // TODO: correct the typo in the method name
    public Order firdFirstRequestedOrder(){
        return orderRepository.findByStatusInOrderByCreatedAtAsc(REQUESTED).orElseThrow(OrderNotFoundException::new).get(0);
    }

    public Order takeOrder(OrderCommand orderCommand){
        // TODO: Due to the validation at controller level, the orderCommand parameter can never be null
        if(orderCommand == null){ throw new InputNotValidException(); }

        // TODO: Make the variable final
        Instant createdAt = Instant.now();

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
        orderCommand.getMeals().forEach(mealnr -> {
            Meal meal = Meal.builder().menuNumber(mealnr).build();
            Example<Meal> example = Example.of(meal);
            meal = mealRepository.findOne(example).orElseThrow(MealNotFoundException::new);
            // TODO: make a method that adds a meal to the order's meal list in the Order class itself
            order.getMeals().add(meal);
            LOGGER.info("Meal name is {}", meal.getName());
            mealStatsService.addStats(meal.getMenuNumber());
                });

        return orderRepository.save(order);
    }

    public void changeStatus(Order order, Status status){
        if(order == null || status == null){ throw new InputNotValidException(); }
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order findByCustormerName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InputNotValidException();}
            return orderRepository.findByCustomerName(name).orElseThrow(OrderNotFoundException::new);
    }

}

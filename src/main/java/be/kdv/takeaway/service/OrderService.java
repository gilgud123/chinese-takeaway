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

@Service
public class OrderService {

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
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrdersNotDone(){
        Optional<List<Order>> optionalOrders = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.PREPARING, REQUESTED);
        return optionalOrders.orElseThrow(OrderNotFoundException::new);
    }

    public Order firdFirstRequestedOrder(){
        return orderRepository.findByStatusInOrderByCreatedAtAsc(REQUESTED).orElseThrow(OrderNotFoundException::new).get(0);
    }

    public Order takeOrder(OrderCommand orderCommand){

        if(orderCommand == null){ throw new InputNotValidException(); }

        Instant createdAt = Instant.now();

        Order order = Order.builder()
                .customerName(orderCommand.getCustomerName())
                .meals(new ArrayList<>())
                .status(REQUESTED)
                .createdAt(createdAt)
                .readyAt(createdAt.plus(30, ChronoUnit.MINUTES))
                .build();

        orderCommand.getMeals().forEach(mealnr -> {
            Meal meal = Meal.builder().menuNumber(mealnr).build();
            Example<Meal> example = Example.of(meal);
            meal = mealRepository.findOne(example).orElseThrow(MealNotFoundException::new);
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

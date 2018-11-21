package be.kdv.takeaway.service;

import be.kdv.takeaway.bootstrap.Bootstrap;
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
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.kdv.takeaway.model.Status.REQUESTED;

@Service
@Slf4j
public class OrderService {

    private final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    private MealRepository mealRepository;
    private OrderRepository orderRepository;
    private MealStatsService mealStatsService;

    public OrderService(
            MealRepository mealRepository,
            OrderRepository orderRepository,
            MealStatsService mealStatsService
    ) {
        this.mealRepository = mealRepository;
        this.orderRepository = orderRepository;
        this.mealStatsService = mealStatsService;
    }

    public Order getById(String id){
        return orderRepository.findById(id).orElseThrow(InputNotValidException::new);
    }

    public List<Order> getAllOrdersNotDone(){
        Optional<List<Order>> optionalOrders = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.PREPARING, REQUESTED);
        return optionalOrders.orElseThrow(() -> new EntityNotFoundException(Order.class));
    }

    public Order firdFirstRequestedOrder(){
        return orderRepository.findByStatusInOrderByCreatedAtAsc(REQUESTED).orElseThrow(() -> new EntityNotFoundException(Order.class)).get(0);
    }

    public Order takeOrder(OrderCommand orderCommand){

        if(orderCommand == null){ throw new InputNotValidException(); }

        Instant createdAt = Instant.now();

        Order order = Order.builder()
                .customerName(orderCommand.getCustomerName())
                .meals(new ArrayList<Meal>())
                .status(REQUESTED)
                .createdAt(createdAt)
                .readyAt(createdAt.plus(30, ChronoUnit.MINUTES))
                .build();

        orderCommand.getMeals().forEach(mealnr -> {
            Meal meal = mealRepository.getByMenuNumber(mealnr).orElseThrow((() -> new EntityNotFoundException(mealnr, Meal.class)));
            order.getMeals().add(meal);
            mealStatsService.addStats(mealnr);
                });

        return orderRepository.save(order);
    }

    public void changeStatus(Order order, Status status){
        if(order == null || status == null){ throw new InputNotValidException(); }
        order.setStatus(status);
        orderRepository.save(order);
    }

}

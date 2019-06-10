package be.kdv.takeaway.service;

import be.kdv.takeaway.command.OrderCommand;
import be.kdv.takeaway.exception.EntityNotFoundException;
import be.kdv.takeaway.exception.InputNotValidException;
import be.kdv.takeaway.model.Meal;
import be.kdv.takeaway.model.MealOrder;
import be.kdv.takeaway.model.Status;
import be.kdv.takeaway.repository.MealRepository;
import be.kdv.takeaway.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static be.kdv.takeaway.model.Status.REQUESTED;

@Service
public class OrderService {

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

    public MealOrder getById(String id){
        return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, MealOrder.class));
    }

    public List<MealOrder> getAllOrdersNotDone(){
        Optional<List<MealOrder>> optionalOrders = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.PREPARING, Status.REQUESTED);
        return optionalOrders.orElseThrow(() -> new EntityNotFoundException(MealOrder.class));
    }

    public MealOrder findFirstRequestedOrder() {
        List<MealOrder> sortedList = orderRepository.findByStatusInOrderByCreatedAtAsc(Status.REQUESTED).orElseThrow(() -> new EntityNotFoundException(MealOrder.class));
       if(sortedList.size() > 0){
           return sortedList.get(0);
       }else {
           throw new EntityNotFoundException(MealOrder.class);
       }
    }

    public MealOrder takeOrder(OrderCommand orderCommand){

        Instant createdAt = Instant.now();

        MealOrder order = MealOrder.builder()
                .customerName(orderCommand.getCustomerName())
                .meals(new ArrayList<>())
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

    public MealOrder changeStatus(MealOrder order, Status status){
        if(order == null){ throw new EntityNotFoundException(MealOrder.class); }
        if(status == null){ throw new InputNotValidException(Status.class); }
        order.setStatus(status);
        return orderRepository.save(order);
    }

}

package be.kdv.takeaway.controller;

import be.kdv.takeaway.command.OrderCommand;
import be.kdv.takeaway.exception.EntityNotFoundException;
import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import be.kdv.takeaway.service.OrderService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(path = "/orders/cook")
    public @ResponseBody ResponseEntity<Resources<Order>> getAllOrderNotDone() {
        Order firstRequestedOrder;
        if(orderService.findFirstRequestedOrder() == null) {
            throw new EntityNotFoundException(Order.class);
        } else {
            firstRequestedOrder = orderService.findFirstRequestedOrder();
        }
        orderService.changeStatus(firstRequestedOrder, Status.PREPARING);
        List<Order> sortedOrders = orderService.getAllOrdersNotDone();
        Resources<Order> resources = new Resources<>(sortedOrders);
        resources.add(linkTo(methodOn(OrderController.class).getAllOrderNotDone()).withSelfRel());
        return ResponseEntity.ok(resources);
    }

    @PostMapping(path = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> takeOrder(@RequestBody @Validated OrderCommand orderCommand) {
        Order order = orderService.takeOrder(orderCommand);
        Resource<Order> resource = new Resource<>(order);
        resource.add(linkTo(methodOn(OrderController.class).takeOrder(orderCommand)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @PatchMapping("/orders/{id}/{status}")
    public @ResponseBody ResponseEntity<?> changeOrderStatus(@PathVariable @Valid String id, @PathVariable @Valid String status) {
        Order changedOrder = orderService.changeStatus(
                orderService.getById(id),
                Status.valueOf(status.toUpperCase())
            );
        return ResponseEntity.ok(changedOrder);
    }
}


package be.kdv.takeaway.controller;

import be.kdv.takeaway.command.OrderCommand;
import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import be.kdv.takeaway.service.OrderService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/cook")
    public ResponseEntity<?> getAllOrderNotDone() {
        try {
            List<Order> sortedOrder = orderService.getAllOrdersNotDone();
            Order firstRequestedOrder = orderService.firdFirstRequestedOrder();
            orderService.changeStatus(firstRequestedOrder, Status.PREPARING);
            return new ResponseEntity<>(orderService.getAllOrdersNotDone(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> takeOrder(@RequestBody @Validated OrderCommand orderCommand){

        Order order = orderService.takeOrder(orderCommand);

        Resource<Order> resource = new Resource<Order>(order);

        resource.add(linkTo(methodOn(OrderController.class).takeOrder(orderCommand)).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    /*@PatchMapping("")
    public ResponseEntity<?> changeOrderStatus(@PathVariable String id, @PathVariable String status){
        try{
            orderService.changeStatus(
                    orderService.getById(id),
                    Status.valueOf(status.toUpperCase())
            );
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }*/

}


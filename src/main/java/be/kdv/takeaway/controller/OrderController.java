package be.kdv.takeaway.controller;

import be.kdv.takeaway.command.OrderCommand;
import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import be.kdv.takeaway.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        try {
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cook")
    // TODO: not a proper way to use REST. A GetMapping expects the result to be the same for each call
    // this is not the case here

    // TODO: The GetMapping should contain the id of the resource your are about to cook
    // TODO: change name of the method as it does not describe the logic inside of it
    // TODO: remove the diamond sign as it is not needed
    public ResponseEntity update() {
        try {
            String id = orderService.firstFirstRequestedOrder().getId();
            orderService.changeStatus(id, Status.PREPARING);
            return new ResponseEntity<>(orderService.getAllOrdersNotDone(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: no correct implementation of REST. To create an resource of type order, a POST to /orders is enough
    @PostMapping
    // TODO: remove the diamond sign as it is not needed
    public ResponseEntity takeOrder(@RequestBody @Validated OrderCommand orderCommand) {
        try {
            return new ResponseEntity<>(orderService.takeOrder(orderCommand), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/filter")
    // TODO: incorrect usage of REST. Use /filter?name=xxx to filter resources on their name
    public ResponseEntity getOrderStatus(@RequestParam String name) {
        try {
            return new ResponseEntity<>(orderService.findByCustormerName(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }

    }

    @PatchMapping("/{id}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable String id, @PathVariable String status) {
        try {
            // TODO: the logic to obtain the Order should be inside the changeStatus method
            Order order = orderService.changeStatus(id, Status.valueOf(status.toUpperCase()));
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

}

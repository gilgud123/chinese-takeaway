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

    @GetMapping("/all")
    public ResponseEntity getAllOrders() {
        try {
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    // Get all orders that are to be prepared yet, sorted op date
    @GetMapping("/cook")
    public ResponseEntity getAllOrdersToBePrepared() {
        try {
            return new ResponseEntity<>(orderService.getAllOrdersNotDone(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity takeOrder(@RequestBody @Validated OrderCommand orderCommand) {
        try {
            return new ResponseEntity<>(orderService.takeOrder(orderCommand), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/filter")
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
            Order order = orderService.changeStatus(id, Status.valueOf(status.toUpperCase()));
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

}

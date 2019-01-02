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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        try{
            return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cook")
    public ResponseEntity<?> getAllOrderNotDone() {
        try {
            orderService.changeStatus(orderService.firdFirstRequestedOrder(), Status.PREPARING);
            return new ResponseEntity<>(orderService.getAllOrdersNotDone(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/order")
    public ResponseEntity<?> takeOrder(@RequestBody @Validated OrderCommand orderCommand){
        try{
            return new ResponseEntity<>(orderService.takeOrder(orderCommand), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getOrderStatus(@PathVariable String name){
        try{
            Order order = orderService.findByCustormerName(name);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/{id}/{status}")
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

    }

}

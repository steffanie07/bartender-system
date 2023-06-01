package com.bartender;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final BarmanService bartenderService;

    public OrderController(BarmanService bartenderService) {
        this.bartenderService = bartenderService;
    }

    @PostMapping("/order")
    public ResponseEntity<String> orderDrink(@RequestBody Order order) {
        if (bartenderService.canAcceptOrder(order)) {
            return new ResponseEntity<>("Your order is accepted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Your order cannot be accepted at this moment.", HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    @GetMapping("/orders")
    public List<Order> listAllOrders() {
        return bartenderService.getAllOrders();
    }
}

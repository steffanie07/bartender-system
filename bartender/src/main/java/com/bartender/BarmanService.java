package com.bartender;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BarmanService {

    private static final Logger logger = LoggerFactory.getLogger(BarmanService.class);

    @Value("${drink.preparation.time}")
    private int preparationTime;

    private final ConcurrentLinkedQueue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private final List<Order> allOrders = new CopyOnWriteArrayList<>();

    public boolean canAcceptOrder(Order order) {
        if (canAcceptOrderBasedOnDrinkType(order)) {
            orderQueue.offer(order);
            allOrders.add(order);
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(preparationTime * 1000);
                    orderQueue.poll();
                    logger.info("Order for customer {} is ready.", order.getCustomerNumber());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.error("Error during drink preparation: ", e);
                }
            });
            return true;
        }
        return false;
    }

    private boolean canAcceptOrderBasedOnDrinkType(Order order) {
        long beerCount = orderQueue.stream().filter(o -> o.getDrinkType() == DrinkType.BEER).count();
        long otherDrinksCount = orderQueue.size() - beerCount;

        if (order.getDrinkType() == DrinkType.BEER) {
            return beerCount < 2 && otherDrinksCount == 0;
        } else {
            return orderQueue.isEmpty();
        }
    }

    public List<Order> getAllOrders() {
        return allOrders;
    }
}



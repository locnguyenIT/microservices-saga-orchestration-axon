package com.ntloc.order.saga.event.handler;

import com.ntloc.coreapi.order.event.OrderCancelledEvent;
import com.ntloc.coreapi.order.event.OrderCompletedEvent;
import com.ntloc.coreapi.order.event.OrderCreatedEvent;
import com.ntloc.coreapi.order.event.OrderRefundedEvent;
import com.ntloc.order.Order;
import com.ntloc.order.OrderLineItem;
import com.ntloc.order.OrderRepository;
import com.ntloc.order.exception.ResourceNotFoundException;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.ntloc.order.OrderConstant.MessagesConstant.ORDER_WAS_NOT_FOUND;

@Component
public class OrderEventHandler {

    private final OrderRepository orderRepository;

    public OrderEventHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        List<OrderLineItem> lineItems = new ArrayList<>();

        BeanUtils.copyProperties(event.orderDetails().lineItems(),
                lineItems);
        Order order = new Order(event.orderId(), event.orderDetails().customerId(), lineItems, event.orderDetails().totalMoney());
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent orderCancelledEvent) {
        Order order = orderRepository.findById(orderCancelledEvent.orderId()).orElseThrow(() ->
                new ResourceNotFoundException(ORDER_WAS_NOT_FOUND));
        order.cancel();
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderRefundedEvent orderRefundedEvent) {
        Order order = orderRepository.findById(orderRefundedEvent.orderId()).orElseThrow(() ->
                new ResourceNotFoundException(ORDER_WAS_NOT_FOUND));
        order.refund();
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order = orderRepository.findById(event.orderId()).orElseThrow(() ->
                new ResourceNotFoundException(ORDER_WAS_NOT_FOUND));
        order.completed();
        orderRepository.save(order);
    }

}

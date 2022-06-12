package eus.ecrop.api.service.impl;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eus.ecrop.api.domain.Payment;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.SubscriptionDto;
import eus.ecrop.api.exception.UserNotFoundException;
import eus.ecrop.api.repository.PaymentRepository;
import eus.ecrop.api.service.SubscriptionService;
import eus.ecrop.api.service.UserService;

/*
* @author Mikel Orobengoa
* @version 08/06/2022
*/

/**
 * The SubscriptionServiceImpl class is a service class that implements the
 * SubscriptionService
 * interface
 */
@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${subscription.price}")
    private Double subscriptionPrice;

    @Override
    public Payment makePayment(Long userId, Double amount) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        if (user.getActiveSubscription()) {
            return null;
        }

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setUser(user);
        payment.setDate(new Timestamp(System.currentTimeMillis()));
        payment = paymentRepository.save(payment);

        return payment;
    }

    @Override
    public User activateSubscription(Long userId, SubscriptionDto subscriptionDto) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        if (user.getActiveSubscription()) {
            return null;
        }

        Payment payment = makePayment(userId, subscriptionPrice);
        if (payment == null) {
            return null;
        }
        user.setActiveSubscription(true);
        user = userService.save(user);

        return user;
    }

    @Override
    public User cancelSubscription(Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        user.setActiveSubscription(false);
        user = userService.save(user);
        return user;
    }

}
package eus.ecrop.api.service;

import eus.ecrop.api.domain.Payment;
import eus.ecrop.api.domain.User;
import eus.ecrop.api.dto.SubscriptionDto;

/*
* @author Mikel Orobengoa
* @version 06/06/2022
*/

/*
* The SubscriptionService interface is a service interface that defines the methods that are used to manage User subscriptions and payments
*/
public interface SubscriptionService {
    
    public Payment makePayment(Long userId, Double amount);

    public User activateSubscription(Long userId, SubscriptionDto subscriptionDto);

    public User cancelSubscription(Long userId);

}

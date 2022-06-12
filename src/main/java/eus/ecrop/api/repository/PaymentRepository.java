package eus.ecrop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eus.ecrop.api.domain.Payment;

/*
* @author Mikel Orobengoa
* @version 10/05/2022
*/

/*
* A repository interface for the User entity.
*/
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

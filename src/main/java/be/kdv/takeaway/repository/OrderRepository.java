package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<List<Order>> findByStatusInOrderByCreatedAtAsc(Status... status);

    Optional<Order> findByCustomerName(String name);
}

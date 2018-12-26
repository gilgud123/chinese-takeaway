package be.kdv.takeaway.repository;

import be.kdv.takeaway.model.Order;
import be.kdv.takeaway.model.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends MongoRepository<Order, String> {

    Optional<List<Order>> findByStatusInOrderByCreatedAtAsc(@Param("status")Status... status);

    @RestResource(path = "name")
    Optional<List<Order>> findByCustomerName(@Param("name") String customerName);

    @RestResource(path = "similar")
    Optional<List<Order>> findByCustomerNameLike(@Param("name") String customerName);

    // both delete overloaded methods are not exposed to the end user
    @Override
    @RestResource(exported = false)
    void delete(Order order);

    @Override
    @RestResource(exported = false)
    void deleteById(String orderId);
}

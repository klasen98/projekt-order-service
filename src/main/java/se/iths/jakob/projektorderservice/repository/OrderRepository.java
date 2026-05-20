package se.iths.jakob.projektorderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.iths.jakob.projektorderservice.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}

package se.iths.jakob.projektorderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 * Entitets klass för orderrader. Sparar en snapshot av produkts namn och pris vid beställning.
 */
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
    private double price;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}

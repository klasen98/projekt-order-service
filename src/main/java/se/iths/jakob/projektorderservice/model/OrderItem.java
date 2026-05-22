package se.iths.jakob.projektorderservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


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

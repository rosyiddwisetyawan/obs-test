package com.example.obs.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
@NoArgsConstructor()
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_no", nullable = false)
    private Long id;
    @Min(value = 0, message = "item_id must be a positive number.")
    @NotNull(message = "item_id is required")
    @Column(name = "item_id", nullable = false)
    private Long itemId;
    @Min(value = 0, message = "qty must be a positive number.")
    @NotNull(message = "qty is required")
    @Column(name = "qty", nullable = false)
    private Long qty;

}

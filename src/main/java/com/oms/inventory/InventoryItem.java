package com.oms.inventory;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "inventory_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long productId;
    public Integer quantityAvailable;
    public Integer quantityReserved;
}

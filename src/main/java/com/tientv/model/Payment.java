package com.tientv.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Nationalized
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @ColumnDefault("getdate()")
    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    @Column(name = "amount", precision = 18, scale = 2)
    private BigDecimal amount;

    @Nationalized
    @ColumnDefault("'Pending'")
    @Column(name = "payment_status", length = 50)
    private String paymentStatus;

    @Column(name = "transaction_id")
    private Long transactionId;

    @ColumnDefault("getdate()")
    @Column(name = "created_date")
    private OffsetDateTime createdDate;

    @ColumnDefault("getdate()")
    @Column(name = "update_date")
    private OffsetDateTime updateDate;

}
package com.financialtargets.outflow.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Entity
@Data
@Table(name = "essential_outflows")
public class EssentialOutflowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountsEntity account;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Column(name = "value", nullable = false)
    private BigDecimal value;

    @Column(name = "paid_value", nullable = false)
    private BigDecimal paidValue;

    @Column(name = "is_fully_paid", updatable = false, insertable = false)
    private Boolean isFullyPaid;

    @Column(name = "notes")
    private String notes;

    @Column(name = "recurrence")
    private String recurrence;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}

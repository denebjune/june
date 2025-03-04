package com.june.transaction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Setter
    @Column(unique = true)
    private String serialNumber;

    @Setter
    @Column
    private String title;

    @Setter
    @Column
    private String description;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column
    private Timestamp updatedAt;

    @Setter
    @Getter
    @Column
    private Double amount;

    @Override
    public String toString() {
        return "Transaction [id=" + id + ", serialNumber=" + serialNumber + ", title=" + title +", amount=" + amount+ ", description=" + description + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }

    public Transaction(String serialNumber, String title, String description) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.description = description;
    }

    public Boolean isNull(){
        return serialNumber == null && title == null && description == null;
    }
}

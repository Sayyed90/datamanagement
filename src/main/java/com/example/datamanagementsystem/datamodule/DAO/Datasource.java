package com.example.datamanagementsystem.datamodule.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@Table(name = "data_table")
@AllArgsConstructor
@NoArgsConstructor
public class Datasource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String accountNumber;
    private double trxAmmount;
    private String description;
    private String trxDate;
    private String time;
    private String customerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Datasource that = (Datasource) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(trxAmmount, that.trxAmmount) &&
                Objects.equals(description, that.description) &&
                Objects.equals(trxDate, that.trxDate) &&
                Objects.equals(time, that.time) &&
                Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, trxAmmount,description,trxDate,time,customerId);
    }
}

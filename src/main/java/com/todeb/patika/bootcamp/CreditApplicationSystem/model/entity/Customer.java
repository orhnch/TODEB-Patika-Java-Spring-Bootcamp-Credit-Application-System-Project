package com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "[1-9][0-9]{9}[02468]")
    @Column(length = 11,updatable = false,nullable = false,unique = true)
    private String nationalNumberId;

    @NotBlank(message = "Firstname cannot be blank")
    private String firstName;
    @NotBlank(message = "Lastname cannot be blank")
    private String lastName;

    @Min(18)
    private int age;

    @NotNull(message = "Salary cannot be null")
    @Min(1)
    private int salary;

    @Pattern(regexp = "(05)([0-9]{2})\\s?([0-9]{3})\\s?([0-9]{2})\\s?([0-9]{2})")
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    private int creditScore;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Credit> credits;
}

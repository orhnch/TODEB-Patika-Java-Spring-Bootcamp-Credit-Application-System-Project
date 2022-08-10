package com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.SmsStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sms")
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SmsStatus smsStatus;


}

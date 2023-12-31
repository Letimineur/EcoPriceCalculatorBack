package com.letifactory.gaming.eco.pricecalculator.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class EcoUser {
    @Id
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String login;
    @Column(columnDefinition = "nvarchar(250)",nullable = false)
    private String password;
    @Column(columnDefinition= "bit default 0",nullable = false)
    private boolean isAdmin;
    @Column(columnDefinition = "nvarchar(50)",nullable = false)
    private String resetPWDPhrase;
}

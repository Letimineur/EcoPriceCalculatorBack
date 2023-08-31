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
    @Column(columnDefinition = "nvarchar(50)")
    private String login;
    @NotNull
    @Column(columnDefinition = "nvarchar(250)")
    private String password;
    @NotNull
    @Column(columnDefinition= "bit default 0")
    private boolean isAdmin;
    @NotNull
    @Column(columnDefinition = "nvarchar(50)")
    private String resetPWDPhrase;
}

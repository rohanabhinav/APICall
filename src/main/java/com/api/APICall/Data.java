package com.api.APICall;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@lombok.Data
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    @Column(length = 1000)
    private String content;


}

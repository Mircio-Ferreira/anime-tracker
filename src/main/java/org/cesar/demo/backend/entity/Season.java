package org.cesar.demo.backend.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.cesar.demo.backend.enums.Seasonal;

import java.time.Year;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private Year year;
    @Enumerated(EnumType.STRING)
    private Seasonal seasonal;

    public Season(Year year, Seasonal seasonal){
        this.year = year;
        this.seasonal = seasonal;
    }


}

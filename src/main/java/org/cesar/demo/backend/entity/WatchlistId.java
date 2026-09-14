package org.cesar.demo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class WatchlistId implements Serializable {
    private  Long user;
    private Long anime;

}

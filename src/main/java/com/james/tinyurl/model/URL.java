package com.james.tinyurl.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by haozhexu on 1/30/17.
 */
@Entity
@Data
@Accessors(chain = true)
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    // TODO: If tons of folks starts to use my tiny url service, I really should use Long as my id
    // or even BigInteger
    private Integer id;

    @Getter
    @Setter
    private String longURL;
}

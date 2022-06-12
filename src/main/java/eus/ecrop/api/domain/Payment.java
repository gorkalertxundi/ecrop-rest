package eus.ecrop.api.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
* @author Mikel Orobengoa
* @version 06/06/2022
*/

/**
 * Payment is a class that represents a Payment made by a user
 */
@Entity
@Table(name = "payment")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Generated
public class Payment implements Serializable {

    private static final long serialVersionUID = -212057172087235317L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Timestamp date;

    @Column
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    @Version
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer version;

}
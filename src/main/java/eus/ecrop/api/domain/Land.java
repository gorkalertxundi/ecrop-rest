package eus.ecrop.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
* @author Mikel Orobengoa
* @version 19/05/2022
*/

@Entity
@Table(name = "land")
@NoArgsConstructor
@Data
@Generated
public class Land {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Column(name = "nitrogen", nullable = true)
    private Double nitrogen;

    @Column(name = "phosphorus", nullable = true)
    private Double phosphorus;

    @Column(name = "copper", nullable = true)
    private Double copper;

    @Version
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer version;

}
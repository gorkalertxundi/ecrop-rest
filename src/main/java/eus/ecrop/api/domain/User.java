package eus.ecrop.api.domain;

import java.io.Serializable;

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
* @version 06/05/2022
*/

@Entity
@Table(name = "`user`")
@NoArgsConstructor
@Data
@Generated
public class User implements Serializable {

    private static final long serialVersionUID = -8446982019565427240L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idToken;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    private Role role;

    @Version
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer version;

}
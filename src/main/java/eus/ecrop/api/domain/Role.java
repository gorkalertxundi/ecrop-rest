package eus.ecrop.api.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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

/**
 * Role represents a User role in the system
 */
@Entity
@Table(name = "role")
@NoArgsConstructor
@Data
@Generated
public class Role implements Serializable {

    private static final long serialVersionUID = -812059079087230117L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Privilege> privileges;

    @Version
    @EqualsAndHashCode.Exclude
    private Integer version;

}
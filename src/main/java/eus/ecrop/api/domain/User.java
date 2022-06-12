package eus.ecrop.api.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
* @version 07/06/2022
*/

/**
 * User represents a user in the system
 */
@Entity
@Table(name = "`user`")
@NoArgsConstructor
@Data
@Generated
public class User implements Serializable {

    private static final long serialVersionUID = -812079079087230107L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String idToken;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "api_key")
    private String apiKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Role role;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean activeSubscription;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Payment> payments;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Land> lands;

    @Version
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer version;
}
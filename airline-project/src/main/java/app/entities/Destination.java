package app.entities;

import app.enums.Airport;
import lombok.*;
import org.hibernate.annotations.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Пункт назначения.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "destination")
@SQLDelete(sql = "update destination set is_deleted=true where id=?")
@Where(clause = "is_deleted = false")
//@SoftDelete(columnName = "is_deleted", strategy = SoftDeleteType.DELETED) -- новый вариант для spring boot 3.x
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_destination")
    @SequenceGenerator(name = "seq_destination", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column(name = "airport_code")
    @Enumerated(EnumType.STRING)
    private Airport airportCode;

    @Column(name = "airport_name")
    private String airportName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
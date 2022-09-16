package az.babazade.pharmacy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "drug")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DRUG_SEQ")
    @SequenceGenerator(name = "DRUG_SEQ", allocationSize = 1, sequenceName = "DRUG_SEQ")
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "barkod",nullable = false,unique = true)
    private String barkod;
    private String productCountry;
    @Column(name = "price",nullable = false)
    private Double price;
    private Date expDate;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;

}

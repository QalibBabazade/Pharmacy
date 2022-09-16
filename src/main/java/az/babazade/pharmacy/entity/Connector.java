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
@Table(name = "connector")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Connector {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONNECTOR_SEQ")
    @SequenceGenerator(name = "CONNECTOR_SEQ", allocationSize = 1, sequenceName = "CONNECTOR_SEQ")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "drug_id")
    private Drug drug;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDate;
    @ColumnDefault(value = "1")
    private Integer active;
}

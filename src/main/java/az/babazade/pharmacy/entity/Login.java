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
@Table(name = "login")
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Login {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGIN_SEQ")
        @SequenceGenerator(name = "LOGIN_SEQ", allocationSize = 1, sequenceName = "LOGIN_SEQ")
        private Long id;
        private String username;
        private String password;
        private String token;
        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "role_id")
        private Role role;
        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        private Date dataDate;
        @ColumnDefault(value = "1")
        private Integer active;
}

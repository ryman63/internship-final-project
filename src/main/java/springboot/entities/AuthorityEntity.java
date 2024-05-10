package springboot.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "authority"})})
public class AuthorityEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
    private UserEntity user;
    private String authority;
}

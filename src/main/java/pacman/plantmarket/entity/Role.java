package pacman.plantmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name",unique = true,nullable = false)
    private String roleName;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "role")
    private List<User> users;
}

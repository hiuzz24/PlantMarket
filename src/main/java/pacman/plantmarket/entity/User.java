package pacman.plantmarket.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", columnDefinition = "char(36)")
    private String userId;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "full_name", length = 255)
    private String fullName;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "role_id")
    private Integer roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id",updatable = false,insertable = false)
    private Role role;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "user",
                cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "user")
    private List<UserToken> userTokens;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = LocalDateTime.now();
    }
}

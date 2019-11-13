package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mwiski.dieticianoffice.entity.enums.RoleType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    @Column
    private String password;

    @NotNull
    @Column
    private RoleType role;

    public Login(String login, String password, RoleType role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}

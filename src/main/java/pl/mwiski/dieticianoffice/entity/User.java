package pl.mwiski.dieticianoffice.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

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
    private String name;

    @NotNull
    @Column
    private String lastName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Column
    private String phoneNumber;

    @NotNull
    @Column(unique = true)
    private String mail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default private List<Visit> visits = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default private List<Opinion> opinions = new ArrayList<>();
}

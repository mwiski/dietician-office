package pl.mwiski.dieticianoffice.entity;

import lombok.*;
import pl.mwiski.dieticianoffice.entity.enums.SexType;
import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Login login;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String lastName;

    @NotNull
    @Column
    private int age;

    @NotNull
    @Column
    private SexType sex;

    @NotNull
    @Embedded
    private Address address;

    @Column
    private String phoneNumber;

    @Email
    @NotNull
    @Column(unique = true)
    private String mail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default private List<Visit> visits = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default private List<Opinion> opinions = new ArrayList<>();
}

package pl.mwiski.dieticianoffice.entity;

import lombok.*;
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
public class Dietician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIETICIAN_ID")
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
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String mail;

    @OneToMany(mappedBy = "dietician", cascade = CascadeType.ALL)
    @Builder.Default private List<Visit> visits = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "JOIN_DIETICIAN_QUESTION",
            joinColumns = {@JoinColumn(name = "DIETICIAN_ID", referencedColumnName = "DIETICIAN_ID")},
            inverseJoinColumns = {@JoinColumn(name = "QUESTION_ID", referencedColumnName = "QUESTION_ID")}
    )
    @Builder.Default private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "dietician")
    @Builder.Default private List<Answer> answers = new ArrayList<>();
}

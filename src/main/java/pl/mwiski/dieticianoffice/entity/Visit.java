package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TERM_ID")
    private Term term;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "DIETICIAN_ID")
    private Dietician dietician;

    @Column
    private boolean happened;

    public Visit(Term term, User user, Dietician dietician, boolean happened) {
        this.term = term;
        this.user = user;
        this.dietician = dietician;
        this.happened = happened;
    }
}

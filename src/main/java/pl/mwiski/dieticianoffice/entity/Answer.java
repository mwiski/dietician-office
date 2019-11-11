package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @Column
    private String answer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @NotNull
    @Column
    private Instant addedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "DIETICIAN_ID")
    private Dietician dietician;

    public Answer(String answer, Question question) {
        this.answer = answer;
        this.question = question;
    }
}

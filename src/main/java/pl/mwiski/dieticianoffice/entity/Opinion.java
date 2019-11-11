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
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @NotNull
    @Column
    private String opinion;

    @NotNull
    @Column
    private Instant addedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Opinion(String opinion, User user) {
        this.opinion = opinion;
        this.user = user;
    }
}

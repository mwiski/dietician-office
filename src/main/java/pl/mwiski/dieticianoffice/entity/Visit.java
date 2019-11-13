package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @Column(unique = true)
    private LocalDateTime dateAndTime;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "DIETICIAN_ID")
    private Dietician dietician;

    @NotNull
    @Column
    private boolean available;
    
    @Column
    private boolean completed;

    public Visit(@NotNull LocalDateTime dateAndTime, User user, @NotNull Dietician dietician, @NotNull boolean available, boolean completed) {
        this.dateAndTime = dateAndTime;
        this.user = user;
        this.dietician = dietician;
        this.available = available;
        this.completed = completed;
    }
}

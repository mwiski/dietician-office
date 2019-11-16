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
    @Column
    private LocalDateTime dateTime;

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

    public Visit(LocalDateTime dateTime, User user, Dietician dietician, boolean available, boolean completed) {
        this.dateTime = dateTime;
        this.user = user;
        this.dietician = dietician;
        this.available = available;
        this.completed = completed;
    }
}

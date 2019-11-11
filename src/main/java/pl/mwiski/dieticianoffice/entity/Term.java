package pl.mwiski.dieticianoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TERM_ID")
    private long id;

    @NotNull
    @Column
    private LocalDateTime dateAndTime;

    @ManyToMany(mappedBy = "terms")
    private List<Dietician> dieticians = new ArrayList<>();

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    @NotNull
    @Column
    private boolean available;

    public Term(LocalDateTime dateAndTime, boolean available) {
        this.dateAndTime = dateAndTime;
        this.available = available;
    }
}

package cours.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@AttributeOverride(name = "id", column = @Column(name = "`tr_id`"))
@Getter @Setter @ToString
public class Transaction extends AbstractEntity {
    @Column(name = "tr_type", unique = true)
    private String transactionType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "`ac_id`", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
    @Column(name = "tr_amount")
    private Double amount;
    @Column(name = "tr_date")
    private LocalDate transactionDate;
}

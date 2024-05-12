package cours.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "accounts")
@AttributeOverride(name = "id", column = @Column(name = "`ac_id`"))
@Getter @Setter @ToString
public class Account extends AbstractEntity {
    @Column(name = "ac_type", unique = true)
    private String accountType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "`cl_id`", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Transaction> transactions;
    @Column(name = "ac_number")
    private String accountNumber;
    @Column(name = "ac_balance")
    private Double balance;
}

package cours.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "clients")
@AttributeOverride(name = "id", column = @Column(name = "`cl_id`"))
@Getter @Setter @ToString
public class Client extends AbstractEntity {
    @Column(name = "cl_name", unique = true)
    private String name;
    @Column(name = "cl_surname")
    private String surname;
    @Column(name = "cl_email")
    private String email;
    @Column(name = "cl_phone")
    private String phone;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Account> accounts;
}

package cours.entity;


import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Entity
@Table(name = "clients")
@AttributeOverride(name = "id", column = @Column(name = "`cl_id`"))
public class Client extends AbstractEntity {
    @Column(name = "cl_name", unique = true)
    private String name;
    @Column(name = "cl_surname")
    private String surname;
    @Column(name = "cl_email")
    private String email;
    @Column(name = "cl_phone")
    private String phone;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "client", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Account> accounts;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", accounts=" + accounts +
                ", id=" + id +
                '}';
    }
}

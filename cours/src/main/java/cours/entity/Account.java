package cours.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accounts")
@AttributeOverride(name = "id", column = @Column(name = "`ac_id`"))
public class Account extends AbstractEntity {
    @Column(name = "ac_type", unique = true)
    private String accountType;
    @Column(unique = true)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "`cl_id`", nullable = false)
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Transaction> transactions;
    @Column(name = "cl_name")
    private String accountNumber;
    @Column(name = "ac_balance")
    private Double balance;

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public Client getClient() {
        return client;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Account{" +
                "client=" + client +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", id=" + id +
                '}';
    }
}

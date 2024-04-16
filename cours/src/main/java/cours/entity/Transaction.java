package cours.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
@AttributeOverride(name = "id", column = @Column(name = "`tr_id`"))
public class Transaction extends AbstractEntity {
    @Column(name = "ac_type", unique = true)
    private String transactionType;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "`ac_id`", nullable = false)
    private Account account;
    @Column(name = "tr_amount")
    private Double amount;
    @Column(name = "tr_date")
    private Date transactionDate;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", account=" + account +
                ", id=" + id +
                '}';
    }
}

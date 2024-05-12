package cours.runner;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import cours.entity.Client;
import cours.entity.Account;
import cours.entity.Transaction;
import cours.service.ClientService;
import cours.service.AccountService;
import cours.service.TransactionService;

@SpringBootApplication
@EnableJpaRepositories("cours.repository")
@ComponentScan("cours.service")
@EntityScan("cours.entity")
public class Main {
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Main.class);
        TransactionService transactionService = ctx.getBean(TransactionService.class);
        ClientService clientService = ctx.getBean(ClientService.class);
        AccountService accountService = ctx.getBean(AccountService.class);

        Client client = new Client();
        client.setName("ffff");
        client.setPhone("122212221");
        clientService.save(client);

        //Создание факультета с группой со студентом
        Client cl = new Client();
        cl.setName("Some Client");
        cl.setPhone("344434443");
        clientService.save(cl);

        Account ac = new Account();
        ac.setAccountType("aaaaa");
        ac.setClient(cl);
        accountService.save(ac);

        Transaction transaction = new Transaction();
        transaction.setTransactionType("New type");
        transaction.setTransactionDate(new java.util.Date());
        transaction.setAmount(113.);
        transaction.setAccount(ac);
        transactionService.save(transaction);

        Thread.sleep(50);
        // Прочитать всё
        System.err.println("READ_ALL");
        List<Client> clients = clientService.read();
        clients.forEach(System.out::println);

        List<Account> accounts = accountService.read();
        accounts.forEach(System.out::println);

        Thread.sleep(50);
        // Прочитать по id и изменить
        System.err.println("READ_BY_ID_AND_SET");
        client = clientService.read(1L);
        System.out.println(client);
        client.setName("New Client Name");
        clientService.edit(client);
        client = clientService.read(1L);
        System.out.println(client);

        ac = accountService.read(1L);
        System.out.println(ac);
        ac.setAccountType("New Account Type");
        accountService.edit(ac);
        ac = accountService.read(1L);
        System.out.println(ac);

        Thread.sleep(50);
        // Удалить по id
        System.err.println("DELETE_BY_ID");
        clientService.delete(1L);
        clients = clientService.read();
        clients.forEach(System.out::println);

        accountService.delete(1L);
        accounts = accountService.read();
        accounts.forEach(System.out::println);

        Thread.sleep(50);
        // Прочитать по имени
        System.err.println("READ_BY_NAME");
        client = clientService.readByName("G");
        System.out.println(client);

        ac = accountService.readByAccountType("S");
        System.out.println(ac);

        Thread.sleep(50);
        System.err.println("DELETE_STUDENT_AND_GROUP_BY_ID");
        accountService.delete(3L);
        List<Account> aaccounts = accountService.read();
        aaccounts.forEach(System.out::println);

        transactionService.delete(3L);
        List<Transaction> transactions = transactionService.read();
        transactions.forEach(System.out::println);

        ctx.close();
    }
}

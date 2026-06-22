package com.wellsfargo;

import com.wellsfargo.model.Client;
import com.wellsfargo.model.FinancialAdvisor;
import com.wellsfargo.model.Portfolio;
import com.wellsfargo.model.Security;
import com.wellsfargo.repository.ClientRepository;
import com.wellsfargo.repository.FinancialAdvisorRepository;
import com.wellsfargo.repository.PortfolioRepository;
import com.wellsfargo.repository.SecurityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demoRunner(
            FinancialAdvisorRepository advisorRepository,
            ClientRepository clientRepository,
            PortfolioRepository portfolioRepository,
            SecurityRepository securityRepository) {
        return args -> {
            System.out.println("==================================================");
            System.out.println("STARTING JPA RELATIONSHIP DEMONSTRATION RUNNER...");
            System.out.println("==================================================");

            // 1. Create a Financial Advisor
            FinancialAdvisor advisor = new FinancialAdvisor();
            advisor.setFirstName("Jane");
            advisor.setLastName("Doe");
            advisor.setEmail("jane.doe@wellsfargo.com");
            advisor.setPhone("1-800-555-0199");
            advisor.setClients(new ArrayList<>());
            advisor = advisorRepository.save(advisor);
            System.out.println("Created Advisor: " + advisor.getFirstName() + " " + advisor.getLastName());

            // 2. Create a Client under this Financial Advisor
            Client client = new Client();
            client.setFirstName("John");
            client.setLastName("Smith");
            client.setEmail("john.smith@gmail.com");
            client.setPhone("555-0123");
            client.setFinancialAdvisor(advisor);
            client = clientRepository.save(client);
            System.out.println("Created Client: " + client.getFirstName() + " " + client.getLastName());

            // 3. Create a Portfolio for the Client
            Portfolio portfolio = new Portfolio();
            portfolio.setClient(client);
            portfolio.setSecurities(new ArrayList<>());
            portfolio = portfolioRepository.save(portfolio);
            System.out.println("Created Portfolio with ID: " + portfolio.getPortfolioId() + " linked to Client: " + portfolio.getClient().getFirstName());

            // 4. Create Securities and add to the Portfolio
            Security appleStock = new Security();
            appleStock.setName("Apple Inc. (AAPL)");
            appleStock.setCategory("Equity");
            appleStock.setPurchaseDate(LocalDate.of(2026, 1, 15));
            appleStock.setPurchasePrice(new BigDecimal("182.50"));
            appleStock.setQuantity(50);
            appleStock.setPortfolio(portfolio);

            Security wellsfargoStock = new Security();
            wellsfargoStock.setName("Wells Fargo & Co (WFC)");
            wellsfargoStock.setCategory("Equity");
            wellsfargoStock.setPurchaseDate(LocalDate.of(2026, 2, 10));
            wellsfargoStock.setPurchasePrice(new BigDecimal("57.20"));
            wellsfargoStock.setQuantity(150);
            wellsfargoStock.setPortfolio(portfolio);

            securityRepository.save(appleStock);
            securityRepository.save(wellsfargoStock);
            System.out.println("Added Securities to the Portfolio.");

            // 5. Verify the full hierarchy
            System.out.println("\n--- Fetching saved Advisor to verify cascading / relations ---");
            FinancialAdvisor retrievedAdvisor = advisorRepository.findById(advisor.getAdvisorId()).orElseThrow();
            System.out.println("Advisor: " + retrievedAdvisor.getFirstName() + " " + retrievedAdvisor.getLastName());
            
            // Client relationship
            List<Client> advisorClients = clientRepository.findAll(); // Simple fetch
            for (Client c : advisorClients) {
                System.out.println("  -> Client: " + c.getFirstName() + " " + c.getLastName());
                
                // Portfolio relationship
                Portfolio p = portfolioRepository.findById(portfolio.getPortfolioId()).orElseThrow();
                System.out.println("     -> Portfolio ID: " + p.getPortfolioId());
                
                // Securities relationship
                List<Security> securities = securityRepository.findAll();
                for (Security s : securities) {
                    System.out.println("        -> Security: " + s.getName() + " | Qty: " + s.getQuantity() + " | Price: $" + s.getPurchasePrice());
                }
            }
            System.out.println("==================================================");
        };
    }
}

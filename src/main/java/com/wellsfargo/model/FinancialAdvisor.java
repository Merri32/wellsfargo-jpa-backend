package com.wellsfargo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "financial_advisor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialAdvisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advisor_id")
    private Long advisorId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "financialAdvisor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients;
}

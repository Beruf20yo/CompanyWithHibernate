package employee;

import company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="employee_type")
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int idEmployee;
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company;
    @Column(name = "month_salary")
    int monthSalary;
    @Column(name = "income_for_company")
    int incomeForCompany;
    @Column(name = "rate_per_mounth")
    int ratePerMounth;
//    @Column(name = "employee_type")
//    String employeetype;


}

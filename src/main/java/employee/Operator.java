package employee;

import company.Company;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@DiscriminatorValue("Operator")
@Entity
@Table
public class Operator extends Employee {
    public Operator(Company company) {
        ratePerMounth = 40000;
        monthSalary = ratePerMounth;
        incomeForCompany = 0;
        this.company = company;
    }

    public Operator() {

    }
}
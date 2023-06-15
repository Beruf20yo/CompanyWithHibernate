package employee;

import company.Company;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TopManager")
public class TopManager extends Employee {
    protected int ratePerMounth = 80000;

    public TopManager(Company company) {
        this.company = company;
        incomeForCompany = 0;
        if (company.getIncomeCompany() > 10000000) {
            this.monthSalary = ratePerMounth * 5 / 2;
        } else {
            this.monthSalary = ratePerMounth;
        }
    }

    public TopManager() {

    }

    @Override
    public int getMonthSalary() {
        if (company.getIncomeCompany() > 10000000) {
            this.monthSalary = ratePerMounth * 5 / 2;
        }
        return monthSalary;
    }
}
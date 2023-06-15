package employee;

import company.Company;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("Manager")
public class Manager extends Employee {
    protected int ratePerMounth = 60000;

    public Manager(Company company) {
        this.company = company;
        this.incomeForCompany = setCompanyIncome();
        this.monthSalary = (setCompanyIncome() * 5 / 100) + ratePerMounth;
    }

    public Manager() {

    }

    public int setCompanyIncome() {
        int startRange = 115000;
        int endRange = 140000;
        int incomePart = startRange + (int) (Math.random() * endRange);
        return incomePart;
    }
}
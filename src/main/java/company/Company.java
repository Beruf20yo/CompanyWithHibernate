package company;


import employee.Employee;
import hibersession.HibernateWork;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int idCompany;
    @Column(name = "name_company")
    protected String nameCompany;
    @Column(name = "income_company")
    protected int incomeCompany;
    @OneToMany(mappedBy = "company")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Employee> employees;
    @Transient
    private HibernateWork hibernateWork;

    public Company() {

    }

    public Company(String nameCompany, HibernateWork hibernateWork) {
        this.hibernateWork = hibernateWork;
        this.nameCompany = nameCompany;
    }

    public void hire(Employee employee) {
        hibernateWork.addEmployee(employee);
        hibernateWork.updateCompanyIncome(idCompany, employee.getIncomeForCompany());
    }

    public void hireAll(List<Employee> lisdToAdd) {
        for (Employee employee : lisdToAdd) {
            hire(employee);
        }
    }

    public void fire(int id) {
        try {
            hibernateWork.deleteEmployee(hibernateWork.getEmployeeById(id));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Вы ввели неверное чилсо");
        }
    }

    public void fire() {
        hibernateWork.simpleDelete(idCompany);
    }

    public int getIncomeCompany() {
        return hibernateWork.getCompanyIncome(idCompany);
    }

    public void setIncomeCompany(int incomeCompany) {
        this.incomeCompany += incomeCompany;
    }

    @Override
    public String toString() {
        return idCompany + ". " + nameCompany;
    }
}

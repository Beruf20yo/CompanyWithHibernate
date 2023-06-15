package hibersession;

import company.Company;
import employee.Employee;
import employee.Manager;
import employee.Operator;
import employee.TopManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HibernateWork {
    SessionFactory factory;
    Session session = null;

    public void connect() {
        this.factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(Operator.class)
                .addAnnotatedClass(Manager.class)
                .addAnnotatedClass(TopManager.class)
                .buildSessionFactory();
    }

    public void createSelectCompanies() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Company> companies = session.createQuery("from Company").getResultList();
        for (Company cmp : companies) {
            System.out.println(cmp);
        }
        session.getTransaction().commit();
    }

    public void createCompany(Company company) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(company);
        session.getTransaction().commit();
    }

    public void addEmployee(Employee employee) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();

    }

    public void simpleDelete(int idcompany) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Employee> onlyOne = session.createQuery("from Employee e where e.company = :idcompany").setParameter("idcompany", idcompany).getResultList();
        session.remove(onlyOne.get(0));
        session.getTransaction().commit();
    }

    public List<Integer> getSalaryStaff(String topOrLow, Company company) {
        session = factory.getCurrentSession();
        List<Integer> nums;
        session.beginTransaction();
        if (topOrLow.equals("Top")) {
            nums = session.createQuery("select distinct monthSalary from Employee e where e.company = :company order by monthSalary desc")
                    .setParameter("company", company).getResultList();
        } else {
            nums = session.createQuery("select distinct monthSalary from Employee e where e.company = :company order by monthSalary")
                    .setParameter("company", company).getResultList();
        }
        session.getTransaction().commit();
        return nums;
    }

    public int selectCountOfEmployee(int idcompany) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Integer> nums = session.createQuery("select count(*) from Employee e where e.company = :company").setParameter("company", idcompany).getResultList();
        session.getTransaction().commit();
        return nums.get(0);

    }

    public int getCompanyIncome(int idCompany) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Integer> nums = session.createQuery("select incomeCompany from Company com where com.idCompany = :idCompany ").setParameter("idCompany", idCompany).getResultList();
        session.getTransaction().commit();
        return nums.get(0);
    }

    public void updateCompanyIncome(int idcompany, int incomeForCompany) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Company> companies = session.createQuery("from Company where idCompany = :idcompany").setParameter("idcompany", idcompany).getResultList();
        companies.get(0).setIncomeCompany(incomeForCompany);
        session.update(companies.get(0));

        session.getTransaction().commit();
    }

    public void deleteEmployee(Employee employee) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.remove(employee);
        session.getTransaction().commit();
    }

    public Employee getEmployeeById(int id) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();
        return employee;
    }

    public Company choseCompany(int chose) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        Company company = session.get(Company.class, chose);
        session.getTransaction().commit();
        return company;
    }
}

import company.Company;
import employee.Employee;
import employee.Manager;
import employee.Operator;
import employee.TopManager;
import hibersession.HibernateWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ConsoleApp {
    private final HibernateWork hibernateWork = new HibernateWork();
    private final Scanner scanner = new Scanner(System.in);
    private Company company;

    public void main() {
        hibernateWork.connect();
        System.out.println("""  
                Что вы хотите сделать?
                1. Создать компанию
                2. Покинуть программу""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        switch (chose) {
            case 1 -> newCompany();
            case 2 -> System.out.println("Всего доброго");
        }
    }

    public void choseOrNewCompany() {
        System.out.println("""  
                Что вы хотите сделать?
                1. Создать компанию
                2. Выбрать компанию
                3. Покинуть программу""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        switch (chose) {
            case 1 -> newCompany();
            case 2 -> choseCompany();
            case 3 -> System.out.println("Всего доброго");
        }
    }

    public void newCompany() {
        System.out.println("Введите название компании: ");
        String input = scanner.nextLine();
        company = new Company(input, hibernateWork);
        hibernateWork.createCompany(company);
        System.out.println("Имя компании установлено!" +
                "\nТеперь вы работает с этой компанием");
        mainInterface();
    }

    public void choseCompany() {
        System.out.println("На данный момент у вас есть компании: " +
                "\nВыберите номер компании: ");
        hibernateWork.createSelectCompanies();
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        company = hibernateWork.choseCompany(chose);
        company.setHibernateWork(hibernateWork);
        mainInterface();
    }

    public void mainInterface() {
        while (true) {
            System.out.println("""
                    Основная программа
                    Что вы хотите сделать?
                    1. Добавить сотрудника
                    2. Удалить сотрудника
                    3. Узнать доход компании
                    4. Узнать информацию о зарплатах компании
                    5. Создать ещё одну компанию/Выбрать другую
                    6. Добавить сотрудников списком""");// Список заранее прописан в программе
            String input = scanner.nextLine();
            if (input.replace(" ", "").equalsIgnoreCase("exit")) {
                System.out.println("Всего доброго");
                System.exit(0);
            }
            int chose = Integer.parseInt(input);
            switch (chose) {
                case 1 -> addEmployee();
                case 2 -> deleteEmployee();
                case 3 -> System.out.println("Доход вашей компании: " + "\n" + company.getIncomeCompany());
                case 4 -> getSalaryInfo();
                case 5 -> choseOrNewCompany();
                case 6 -> {
                    List<Employee> employeeToAdd = List.of(new Manager(company),
                            new TopManager(company), new Operator(company),
                            new Manager(company), new Manager(company));
                    company.hireAll(employeeToAdd);
                    System.out.println("Сотрудники добавлены");
                }
            }
        }
    }

    public void addEmployee() {
        System.out.println("""
                Какого сотрудника вы хотите добавить?
                1. Manager
                2. TopManager
                3. Operator""");
        String input = scanner.nextLine();
        int choseId = Integer.parseInt(input);
        int count;
        System.out.println("Сколько сотрудников нужно добавить?");
        input = scanner.nextLine();
        count = Integer.parseInt(input);
        while (count != 0) {
            Employee employee = addOneEmployee(choseId);
            if (employee == null) {
                return;
            } else {
                company.hire(employee);
                count--;
            }
        }
        System.out.println("Сотрудник(и) добавлен(ы)");
        mainInterface();
    }

    public Employee addOneEmployee(int idEmployeeToAdd) {
        return switch (idEmployeeToAdd) {
            case 1 -> new Manager(company);
            case 2 -> new TopManager(company);
            case 3 -> new Operator(company);
            default -> throw new IllegalStateException("Unexpected value: " + idEmployeeToAdd);
        };
    }

    public void deleteEmployee() {
        System.out.println("""
                Как вы хотите удалить сотрудника?
                1. Удалить нескольких
                2. Удалить по ID
                3. Удалить любого""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        switch (chose) {
            case 1 -> deleteSomeEmployee();
            case 2 -> {
                System.out.println("Введите ID сотрудника в списке: ");
                input = scanner.nextLine();
                int id = Integer.parseInt(input);
                company.fire(id);
                mainInterface();
            }
            case 3 -> {
                company.fire();
                System.out.println("Сотрудник удалён");
                mainInterface();
            }
        }

    }

    public void deleteSomeEmployee() {
        System.out.println("""
                Сколько сотрудников нужно удалить?
                1. По количеству
                2. По процентам""");
        String input = scanner.nextLine();
        int count = Integer.parseInt(input);
        switch (count) {
            case 1 -> {
                System.out.println("Введите кол-во сотрудников для удаления");
                input = scanner.nextLine();
                count = Integer.parseInt(input);
                while (count != 0 || hibernateWork.selectCountOfEmployee(company.getIdCompany()) != 0) {
                    company.fire();
                    count--;
                }
                System.out.println("Сотрудники удалены");
                mainInterface();
            }
            case 2 -> {
                System.out.println("Введите процент сотрудников для удаления");
                input = scanner.nextLine();
                count = Integer.parseInt(input);
                int i = hibernateWork.selectCountOfEmployee(company.getIdCompany()) * count / 100;
                while (i != 0) {
                    company.fire();
                    i--;
                }
                System.out.println("Сотрудники удалены");
                mainInterface();
            }
        }
    }

    public void getSalaryInfo() {
        System.out.println("""
                Что вы хотите сделать?
                1. Узнать все уникальные зарплаты
                2. Узнать самые высокие зарплаты
                3. Узнать самые низкие зарплаты""");
        String input = scanner.nextLine();
        int chose = Integer.parseInt(input);
        List<Integer> salaryList = new ArrayList<>();
        switch (chose) {
            case 1 -> salaryList = hibernateWork.getSalaryStaff("Top", company);
            case 2 -> salaryList = switchSalaryStaff("TopSalaryStaff");
            case 3 -> salaryList = switchSalaryStaff("LowSalaryStaff");
        }
        for (int oneSalary : salaryList) {
            System.out.println(oneSalary);
        }
        System.out.println("Зарплаты выведены");
        mainInterface();
    }

    public List<Integer> switchSalaryStaff(String chose) {
        System.out.println("Введите количество зарплат: ");
        String input = scanner.nextLine();
        List<Integer> salaryList = new ArrayList<>();
        List<Integer> salaryHelpList = new ArrayList<>();
        int count = Integer.parseInt(input);
        switch (chose) {
            case "TopSalaryStaff" -> salaryHelpList = hibernateWork.getSalaryStaff("Top", company);
            case "LowSalaryStaff" -> salaryHelpList = hibernateWork.getSalaryStaff("Low", company);
            default -> throw new IllegalStateException("Unexpected value: " + chose);
        }
        while (count != 0 || salaryHelpList.isEmpty()) {
            for (int salary : salaryHelpList) {
                salaryList.add(salary);
                count--;
            }
        }
        return salaryList;
    }
}
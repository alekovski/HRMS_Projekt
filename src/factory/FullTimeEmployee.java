package factory;

import model.Department;
import model.Employee;
import model.EmployeeRole;

public class FullTimeEmployee extends Employee {
    private static final double MONTHLY_BONUS_RATE = 0.1; // 10% бонус

    public FullTimeEmployee(String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager, double baseSalary) {
        super(id, firstName, lastName, department, role, manager);
        this.baseSalary = baseSalary;
    }

    @Override
    public double calculateSalary() {
        return baseSalary * (1 + MONTHLY_BONUS_RATE);
    }

    @Override
    public String toString() {
        return "FullTime" + super.toString();
    }
}

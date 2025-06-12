package factory;

import model.Department;
import model.Employee;
import model.EmployeeRole;

public class PartTimeEmployee extends Employee {
    private final double hoursWorked;
    private final double hourlyRate;

    public PartTimeEmployee(String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager, double hourlyRate, double hoursWorked) {
        super(id, firstName, lastName, department, role, manager);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.baseSalary = hourlyRate * hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public String toString() {
        return "PartTime" + super.toString() +
                ", hoursWorked=" + hoursWorked +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}

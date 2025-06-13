package model; // Changed package to 'model' for better semantics

/**
 * FullTimeEmployee class: Represents an employee working full-time.
 */
public class FullTimeEmployee extends Employee {
    // A constant for the monthly bonus rate (10%).
    private static final double MONTHLY_BONUS_RATE = 0.1;

    /**
     * Constructor for FullTimeEmployee initializes a new full-time employee with their details and base salary.
     */
    public FullTimeEmployee(String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager, double baseSalary) {
        super(id, firstName, lastName, department, role, manager); // Calls the constructor of the parent 'Employee' class.
        this.baseSalary = baseSalary; // The base salary is set in the FullTimeEmployee constructor.
    }

    /**
     * Overrides the abstract calculateSalary method from the 'Employee' class.
     */
    @Override
    public double calculateSalary() {
        return baseSalary * (1 + MONTHLY_BONUS_RATE);
    }

    @Override
    public String toString() {
        return "FullTime" + super.toString();
    }
}


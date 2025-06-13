package model;

/**
 * PartTimeEmployee class: Represents an employee working part-time and extends the 'Employee'.
 */
public class PartTimeEmployee extends Employee {
    // 'final' means their values are set once and cannot be changed.
    private final double hoursWorked;
    private final double hourlyRate;

    /**
     * Constructor for PartTimeEmployee initializes a new part-time employee with their details, hourly rate, and hours worked.
     */
    public PartTimeEmployee(String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager, double hourlyRate, double hoursWorked) {
        super(id, firstName, lastName, department, role, manager);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.baseSalary = hourlyRate * hoursWorked;
    }

    /**
     * Overrides the abstract calculateSalary method from the 'Employee' class.
     * Calculates the part-time employee's salary based on their hourly rate and hours worked.
     */
    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    /**
     * Getter method to retrieve the hours worked.
     */
    public double getHoursWorked() {
        return hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    @Override
    public String toString() {
        return "PartTime" + super.toString() + // Output in English
                ", hoursWorked=" + hoursWorked +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}

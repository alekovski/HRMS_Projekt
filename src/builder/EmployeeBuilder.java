package builder;

import model.Department;
import model.Employee;
import model.EmployeeRole;
import factory.EmployeeFactory;

/**
 * EmployeeBuilder class: This class helps to create Employee objects step-by-step.
 * It's part of the "Builder" design pattern, which makes object creation more flexible and readable.
 */
public class EmployeeBuilder {
    private String id;
    private String firstName;
    private String lastName;
    private Department department;
    private EmployeeRole role;
    private String employeeType;
    private Employee manager;
    private Double baseSalary;
    private Double hourlyRate;
    private Double hoursWorked;

    /**
     * Default constructor for EmployeeBuilder.
     * Initializes a new builder instance.
     */
    public EmployeeBuilder() {
        // Default constructor
    }

    /**
     * Sets the employee's ID.
     */
    public EmployeeBuilder setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the employee's first name.
     */
    public EmployeeBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets the employee's last name.
     */
    public EmployeeBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets the employee's department.
     */
    public EmployeeBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    /**
     * Sets the employee's role.
     */
    public EmployeeBuilder setRole(EmployeeRole role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the type of employee (e.g., "FULLTIME", "PARTTIME").
     * This helps the builder decide which type of employee to create.
     */
    public EmployeeBuilder setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    /**
     * Sets the employee's manager.
     */
    public EmployeeBuilder setManager(Employee manager) {
        this.manager = manager;
        return this;
    }

    /**
     * Sets the base salary for a FullTimeEmployee.
     * This method should only be used if the employee type is "FULLTIME".
     */
    public EmployeeBuilder setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
        return this;
    }

    /**
     * Sets the hourly rate and hours worked for a PartTimeEmployee.
     * This method should only be used if the employee type is "PARTTIME".
     */
    public EmployeeBuilder setHourlyRateAndHours(Double hourlyRate, Double hoursWorked) {
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        return this;
    }

    /**
     * Builds and returns the Employee object based on the set attributes.
     * It uses the EmployeeFactory to create the correct type of employee.
     */
    public Employee build() {
        if ("FULLTIME".equalsIgnoreCase(employeeType)) {
            if (baseSalary == null) {
                throw new IllegalStateException("For FullTimeEmployee, the base salary must be specified.");
            }
            return EmployeeFactory.createFullTimeEmployee(id, firstName, lastName, department, role, manager, baseSalary);
        } else if ("PARTTIME".equalsIgnoreCase(employeeType)) {
            if (hourlyRate == null || hoursWorked == null) {
                throw new IllegalStateException("For PartTimeEmployee, the hourly rate and hours worked must be specified.");
            }
            return EmployeeFactory.createPartTimeEmployee(id, firstName, lastName, department, role, manager, hourlyRate, hoursWorked);
        } else {
            // If the employee type is not recognized or supported
            throw new IllegalArgumentException("Unknown or unsupported employee type for the builder: " + employeeType); // English
        }
    }
}

package model;

/**
 * Employee class: An abstract class representing a generic employee.
 */
public abstract class Employee {
    private final String id;
    private String firstName;
    private String lastName;
    private Department department;
    private EmployeeRole role;
    protected double baseSalary;
    private Employee manager;

    /**
     * Primary constructor for the Employee class.
     * Initializes an employee with basic details.
     */
    public Employee(String id, String firstName, String lastName, Department department, EmployeeRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.baseSalary = 0.0; // Default base salary is 0.0.
        this.manager = null; // The Manager is initially null.
    }

    /**
     * Calls the primary constructor and then sets the manager.
     */
    public Employee(String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager) {
        this(id, firstName, lastName, department, role); // Call the main constructor.
        this.manager = manager;
    }

    /**
     * Sets the base salary for the employee.
     */
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    /**
     * Abstract method to calculate the employee's salary.
     */
    public abstract double calculateSalary();

    // --- Getter Methods ---
    /**
     * Gets the employee's ID.
     */
    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public Employee getManager() {
        return manager;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    // --- Setter Methods ---
    /**
     * Sets the employee's department.
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Provides a string representation of the Employee object.
     */
    @Override
    public String toString() {
        // Information about the manager, if present.
        String managerInfo = (manager != null) ? ", managerId='" + manager.getId() + "'" : "";
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + (department != null ? department.name() : "N/A") + // Check for null department and use getName()
                ", role=" + role +
                ", baseSalary=" + String.format("%.2f", baseSalary) +
                managerInfo +
                '}';
    }
}

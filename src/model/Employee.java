package model;

public abstract class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private Department department;
    private EmployeeRole role;
    protected double baseSalary;
    private Employee manager; // protected, щоб дочірні класи мали доступ

    public Employee(String id, String firstName, String lastName, Department department, EmployeeRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.role = role;
        this.baseSalary = 0.0;
        this.manager = null; // За замовчуванням менеджер відсутній
    }

    public Employee(String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager) {
        this(id, firstName, lastName, department, role); // Викликаємо основний конструктор
        this.manager = manager;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public abstract double calculateSalary();

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

    @Override
    public String toString() {
        String managerInfo = (manager != null) ? ", managerId='" + manager.getId() + "'" : "";
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department=" + department.getName() +
                ", role=" + role +
                ", baseSalary=" + String.format("%.2f", baseSalary) +
                managerInfo +
                '}';
    }
}

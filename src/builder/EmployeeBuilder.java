package builder;

import model.Department;
import model.EmployeeRole;
import model.Employee;
import factory.EmployeeFactory;

public class EmployeeBuilder {
    private String id;
    private String firstName;
    private String lastName;
    private Department department;
    private EmployeeRole role;
    private String employeeType;
    private Employee manager;
    private Double param1;
    private Double param2;

    public EmployeeBuilder() {
    }

    public EmployeeBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder setDepartment(Department department) {
        this.department = department;
        return this;
    }

    public EmployeeBuilder setRole(EmployeeRole role) {
        this.role = role;
        return this;
    }

    public EmployeeBuilder setEmployeeType(String employeeType) { // Метод для встановлення типу для Factory
        this.employeeType = employeeType;
        return this;
    }

    public EmployeeBuilder setManager(Employee manager) { // Новий сеттер для менеджера
        this.manager = manager;
        return this;
    }

    public EmployeeBuilder setParam1(Double param1) {
        this.param1 = param1;
        return this;
    }

    public EmployeeBuilder setParam2(Double param2) {
        this.param2 = param2;
        return this;
    }

    public Employee build() {
        // Перевіряємо тип співробітника та викликаємо Factory з відповідними параметрами
        if ("FULLTIME".equalsIgnoreCase(employeeType)) {
            if (param1 == null) {
                throw new IllegalStateException("FullTimeEmployee requires base salary (param1) to be set.");
            }
            return EmployeeFactory.createEmployee(employeeType, id, firstName, lastName, department, role, manager, param1);
        } else if ("PARTTIME".equalsIgnoreCase(employeeType)) {
            if (param1 == null || param2 == null) {
                throw new IllegalStateException("PartTimeEmployee requires hourly rate (param1) and hours worked (param2) to be set.");
            }
            return EmployeeFactory.createEmployee(employeeType, id, firstName, lastName, department, role, manager, param1, param2);
        } else {
            // Для інших типів або базових, якщо вони не вимагають дод. параметрів
            // Якщо Factory.createEmployee без додаткових параметрів існує, використовуйте його.
            // Наразі, наш Factory завжди очікує хоча б один додатковий параметр.
            throw new IllegalArgumentException("Unknown or unsupported employee type for builder: " + employeeType);
        }
    }
}
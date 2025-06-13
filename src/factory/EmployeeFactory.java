package factory;

import model.Department;
import model.Employee;
import model.EmployeeRole;
import model.FullTimeEmployee;
import model.PartTimeEmployee;

/**
 * "Factory" that creates different types of Employee objects.
 * It uses static methods, so there is no need to create an EmployeeFactory object to use it.
 */
public class EmployeeFactory {
    // Private constructor to prevent creating instances of the factory,
    // as all its methods are static.
    private EmployeeFactory() {
    }

    /**
     * Creates a new instance of a FullTimeEmployee.
     */
    public static Employee createFullTimeEmployee(String id, String firstName, String lastName,
                                                  Department department, EmployeeRole role, Employee manager,
                                                  double baseSalary) {
        return new FullTimeEmployee(id, firstName, lastName, department, role, manager, baseSalary);
    }

    /**
     * Creates a new instance of a PartTimeEmployee.
     */
    public static Employee createPartTimeEmployee(String id, String firstName, String lastName,
                                                  Department department, EmployeeRole role, Employee manager,
                                                  double hourlyRate, double hoursWorked) {
        return new PartTimeEmployee(id, firstName, lastName, department, role, manager, hourlyRate, hoursWorked);
    }
}

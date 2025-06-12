package factory;

import model.Department;
import model.Employee;
import model.EmployeeRole;

public class EmployeeFactory {
    public static Employee createEmployee(String type, String id, String firstName, String lastName, Department department, EmployeeRole role, Employee manager, Double... additionalParams) {
        switch (type.toUpperCase()) {
            case "FULLTIME":
                if (additionalParams != null && additionalParams.length > 0) {
                    return new FullTimeEmployee(id, firstName, lastName, department, role, manager, additionalParams[0]);
                }
                throw new IllegalArgumentException("FullTimeEmployee requires a base salary.");
            case "PARTTIME":
                if (additionalParams != null && additionalParams.length > 1) {
                    return new PartTimeEmployee(id, firstName, lastName, department, role, manager, additionalParams[0], additionalParams[1]);
                }
                throw new IllegalArgumentException("PartTimeEmployee requires hourly rate and hours worked.");
            default:
                throw new IllegalArgumentException("Unknown employee type: " + type);
        }
    }
}
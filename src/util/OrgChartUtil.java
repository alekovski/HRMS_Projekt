package util;

import model.Employee;
import service.HRManager;

/**
 * OrgChartUtil class helps visualize the reporting structure of employees.
 */
public class OrgChartUtil {

    /**
     * Recursively prints the organization chart starting from a given employee.
     * It shows who reports to whom.
     */
    public static void printOrganizationChart(Employee employee, String prefix) {
        // Stop if the employee is null (base case for recursion)
        if (employee == null) {
            return;
        }
        System.out.println(prefix + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getRole() + ")");

        // Find all employees who report to the current employee and print their sub-charts
        HRManager.getInstance().getAllEmployees().stream()
                // Filter for employees whose manager's ID matches the current employee's ID
                .filter(e -> e.getManager() != null && e.getManager().getId().equals(employee.getId()))
                // For each subordinate, recursively call printOrganizationChart with increased indentation
                .forEach(e -> printOrganizationChart(e, prefix + "  "));
    }

    /**
     * Prints the complete organization chart, starting from employees who have no manager.
     */
    public static void printFullOrganizationChart() {
        System.out.println("\n--- Organization Chart ---");
        // Get all employees and filter for those who don't have a manager (top-level employees)
        HRManager.getInstance().getAllEmployees().stream()
                .filter(e -> e.getManager() == null)
                // For each top-level employee, start printing their part of the chart with no initial prefix
                .forEach(e -> printOrganizationChart(e, ""));
        System.out.println("--------------------------");
    }
}

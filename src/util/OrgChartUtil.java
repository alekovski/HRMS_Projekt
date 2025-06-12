package util;

import model.Employee;
import service.HRManager;

public class OrgChartUtil {

    public static void printOrganizationChart(Employee employee, String prefix) {
        if (employee == null) {
            return;
        }
        System.out.println(prefix + employee.getFirstName() + " " + employee.getLastName() + " (" + employee.getRole() + ")");

        HRManager.getInstance().getAllEmployees().stream()
                .filter(e -> e.getManager() != null && e.getManager().getId().equals(employee.getId()))
                .forEach(e -> printOrganizationChart(e, prefix + "  "));
    }

    public static void printFullOrganizationChart() {
        System.out.println("\n--- Organization Chart ---");
        HRManager.getInstance().getAllEmployees().stream()
                .filter(e -> e.getManager() == null)
                .forEach(e -> printOrganizationChart(e, ""));
        System.out.println("--------------------------");
    }
}
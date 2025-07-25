package strategy;

import model.Employee;
import java.util.Comparator;
import java.util.List;

/**
 * This class sorts a list of employees by their department name.
 */
public class SortByDepartmentStrategy implements EmployeeSortingStrategy {

    @Override
    public void sort(List<Employee> employees) {
        employees.sort(Comparator.comparing(e -> e.getDepartment().name()));
    }
}

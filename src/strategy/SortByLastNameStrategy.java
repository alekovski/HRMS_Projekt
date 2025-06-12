package strategy;

import model.Employee;
import java.util.Comparator;
import java.util.List;

public class SortByLastNameStrategy implements EmployeeSortingStrategy {
    @Override
    public void sort(List<Employee> employees) {
        employees.sort(Comparator.comparing(Employee::getLastName));
    }
}
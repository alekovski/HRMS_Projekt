package strategy;

import model.Employee;
import java.util.List;

public interface EmployeeSortingStrategy {
    void sort(List<Employee> employees);
}
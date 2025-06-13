package strategy;

import model.Employee;
import java.util.List;

/**
 * This interface is part of the "Strategy" design pattern. It defines a common way (an interface) for different sorting methods to work.
 */
public interface EmployeeSortingStrategy {
    /**
     * It sorts a list of employees.
     */
    void sort(List<Employee> employees);
}
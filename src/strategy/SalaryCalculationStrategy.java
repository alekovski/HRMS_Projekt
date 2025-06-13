package strategy;

import model.Employee;

/**
 * This interface calculates an employee's salary.
 */
public interface SalaryCalculationStrategy {
    double calculate(Employee employee);
}

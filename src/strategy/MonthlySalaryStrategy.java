package strategy;

import model.Employee;
import model.FullTimeEmployee;

/**
 * This class calculates the salary of FullTime employees.
 */
public class MonthlySalaryStrategy implements SalaryCalculationStrategy {

    @Override
    public double calculate(Employee employee) {
        // Check if the employee is an instance of FullTimeEmployee
        if (employee instanceof FullTimeEmployee) {
            return ((FullTimeEmployee) employee).calculateSalary();
        }
        throw new IllegalArgumentException("This strategy is for FullTimeEmployee only.");
    }
}

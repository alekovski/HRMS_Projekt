package strategy;

import model.Employee;
import model.PartTimeEmployee;

/**
 * This class implements calculates the salary of PartTime employees.
 */
public class HourlyWageStrategy implements SalaryCalculationStrategy {

    @Override
    public double calculate(Employee employee) {
        // Check if the employee is an instance of PartTimeEmployee
        if (employee instanceof PartTimeEmployee) {
            return ((PartTimeEmployee) employee).calculateSalary();
        }
        throw new IllegalArgumentException("This strategy is for PartTimeEmployee only.");
    }
}

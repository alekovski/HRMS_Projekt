package strategy;

import model.Employee;
import factory.PartTimeEmployee;

public class HourlyWageStrategy implements SalaryCalculationStrategy {
    @Override
    public double calculate(Employee employee) {
        if (employee instanceof PartTimeEmployee) {
            return ((PartTimeEmployee) employee).calculateSalary();
        }
        throw new IllegalArgumentException("This strategy is for PartTimeEmployee only.");
    }
}
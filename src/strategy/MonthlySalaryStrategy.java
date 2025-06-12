package strategy;

import model.Employee;
import factory.FullTimeEmployee;

public class MonthlySalaryStrategy implements SalaryCalculationStrategy {
    @Override
    public double calculate(Employee employee) {
        if (employee instanceof FullTimeEmployee) {
            return ((FullTimeEmployee) employee).calculateSalary();
        }
        throw new IllegalArgumentException("This strategy is for FullTimeEmployee only.");
    }
}
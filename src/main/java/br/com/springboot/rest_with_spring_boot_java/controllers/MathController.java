package br.com.springboot.rest_with_spring_boot_java.controllers;

import br.com.springboot.rest_with_spring_boot_java.exception.UnsupportedMathOperationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

@RestController
@RequestMapping("/math")
public class MathController {
    @GetMapping("/sum/{a}/{b}")
    public Double sum(@PathVariable String a, @PathVariable String b) {
        return operate(a, b, (x, y) -> x + y);
    }

    @GetMapping("/subtract/{a}/{b}")
    public Double subtract(@PathVariable String a, @PathVariable String b) {
        return operate(a, b, (x, y) -> x - y);
    }

    @GetMapping("/multiply/{a}/{b}")
    public Double multiply(@PathVariable String a, @PathVariable String b) {
        return operate(a, b, (x, y) -> x * y);
    }

    @GetMapping("/divide/{a}/{b}")
    public Double divide(@PathVariable String a, @PathVariable String b) {
        Double divisor = convertToDouble(b);
        if (divisor == 0) throw new UnsupportedMathOperationException("Cannot divide by zero!");
        return operate(a, b, (x, y) -> x / y);
    }

    @GetMapping("/sqrt/{a}")
    public Double sqrt(@PathVariable String a) {
        Double value = convertToDouble(a);
        if (value < 0) throw new UnsupportedMathOperationException("Cannot calculate square root of negative number!");
        return Math.sqrt(value);
    }

    @GetMapping("/average/{numbers}")
    public Double average(@PathVariable String[] numbers) {
        Double[] values = Stream.of(numbers).map(this::convertToDouble).toArray(Double[]::new);
        return Stream.of(values).mapToDouble(Double::doubleValue).average()
                .orElseThrow(() -> new UnsupportedMathOperationException("No numeric values provided!"));
    }

    private Double operate(String a, String b, BinaryOperator<Double> operation) {
        if (!isNumeric(a) || !isNumeric(b)) {
            throw new UnsupportedMathOperationException("Please set numeric values!");
        }
        return operation.apply(convertToDouble(a), convertToDouble(b));
    }

    private Double convertToDouble(String str) {
        if (str == null) throw new UnsupportedMathOperationException("Null value not allowed!");
        try {
            return Double.parseDouble(str.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new UnsupportedMathOperationException("Invalid numeric value: " + str);
        }
    }

    private boolean isNumeric(String str) {
        if (str == null) return false;
        return str.replace(",", ".").matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}

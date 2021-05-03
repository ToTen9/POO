import static org.junit.jupiter.api.Assertions.assertEquals;

import musichub.business.Calculator;

import org.junit.jupiter.api.Test;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void additionTest() {
        assertEquals(2, calculator.addition(1, 1));
    }

}
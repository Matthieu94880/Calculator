package com.openclassrooms.testing;

import static org.assertj.core.api.Assertions.*;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@ExtendWith(LoggingExtension.class)
public class CalculatorTest {

    private static Instant startedAt;

    private Calculator calculatorUnderTest;

    private Logger logger;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @BeforeEach
    public void initCalculator() {
        logger.info("Appel avant chaque test");
        calculatorUnderTest = new Calculator();
    }

    @AfterEach
    public void undefCalculator() {
        logger.info("Appel après chaque test");
        calculatorUnderTest = null;
    }

    @BeforeAll
    static public void initStartingTime() {
        System.out.println("Appel avant tous les tests");
        startedAt = Instant.now();
    }

    @AfterAll
    static public void showTestDuration() {
        System.out.println("Appel après tous les tests");
        Instant endedAt = Instant.now();
        long duration = Duration.between(startedAt, endedAt).toMillis();
        System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
    }

    @Test
    @Tag("QuatreOperations")
    void testAddTwoPositiveNumbers() throws InterruptedException {
        // Arrange
        int a = 2;
        int b = 3;

        // Act
        int somme = calculatorUnderTest.add(a, b);

        // Assert
        assertThat(somme).isEqualTo(5);
    }

    @Test
    @Tag("QuatreOperations")
    void testMultiplyTwoPositiveNumbers() {
        // Arrange
        int a = 5;
        int b = 10;

        // Act
        int result = calculatorUnderTest.multiply(a, b);

        // Assert
        assertThat(result).isEqualTo(50);
    }

    @ParameterizedTest(name = "{0} x 0 doit être égal à 0")
    @ValueSource(ints = { 1, 2, 42, 1011, 5089 })
    public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
        // Arrange -- Tout est prêt !

        // Act -- Multiplier par zéro
        int actualResult = calculatorUnderTest.multiply(arg, 0);

        // Assert -- ça vaut toujours zéro !
        assertThat(actualResult).isEqualTo(0);
    }

    @ParameterizedTest(name = "{0} + {1} should equal to {2}")
    @CsvSource({ "1,1,2", "2,3,5", "42,57,99" })
    public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
        // Arrange -- Tout est prêt !

        // Act
        int actualResult = calculatorUnderTest.add(arg1, arg2);

        // Assert
        assertThat(actualResult).isEqualTo(expectResult);
    }

    @Timeout(1)
    @Test
    public void longCalcul_shouldComputeInLessThan1Second() {
        // Arrange

        // Act
        calculatorUnderTest.longCalculation();

        // Assert
        // ...
    }

    @Test
    public void listDigits_shouldReturnsTheListOfDigits_ofPositiveInteger() {
        // Arrange
        int number = 95897;

        // Act
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

        // Assert
        assertThat(actualDigits).containsExactlyInAnyOrder(9, 5, 8, 7);
    }

    @Test
    public void listDigits_shouldReturnsTheListOfDigits_ofNegativeInteger() {
        // Arrange
        int number = -124432;

        // Act
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

        // Assert
        assertThat(actualDigits).containsExactlyInAnyOrder(1, 2, 3, 4);
    }

    @Test
    public void listDigits_shouldReturnsTheListOfDigits_ofZero() {
        // Arrange
        int number = 0;

        // Act
        Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

        // Assert
        assertThat(actualDigits).containsExactlyInAnyOrder(0);
    }

    @Disabled("Stoppé car cela échoue tous les mardis")
    @Test
    public void testDate() {
        // GIVEN
        LocalDateTime dateTime = LocalDateTime.now();

        // WHEN

        // THEN
        assertThat(dateTime.getDayOfWeek()).isNotEqualTo(DayOfWeek.TUESDAY);
    }
}

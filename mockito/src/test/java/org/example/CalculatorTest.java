package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

//hw: https://github.com/netology-code/jd-homeworks/tree/master/mocks
public class CalculatorTest {
    @Test
    public void testAddMethodWithMock() {
        // Создаем обычный мок (Mock) объекта Calculator
        Calculator mockCalculator = mock(Calculator.class);
        // Мы переопределяем поведение метода add
        when(mockCalculator.add(3, 5)).thenReturn(10);
        // Вызываем метод add
        int sum = mockCalculator.add(3, 5);
        // Проверяем, что метод add вызывался с правильными аргументами
        verify(mockCalculator).add(3, 5);
        // Проверяем, что результат соответствует нашей переопределенной логике
        Assertions.assertEquals(10, sum);
        int multiply = mockCalculator.multiply(5, 5);
        Assertions.assertEquals(25, multiply);
    }

    @Test
    public void testAddMethodWithSpy() {
        // Создаем частичный мок (spy) объекта Calculator на основе реального объекта
        Calculator spyCalculator = Mockito.spy(Calculator.class);
        // Вызываем реальный метод add (поскольку это spy)
        when(spyCalculator.add(3, 5)).thenReturn(10);
        int sum = spyCalculator.add(3, 5);
        // Проверяем, что результат соответствует реальной логике метода add
        Assertions.assertEquals(10, sum);
        // Проверяем, что метод add вызывался с правильными аргументами
        verify(spyCalculator).add(3, 5);
        int multiply = spyCalculator.multiply(5, 5);
        Assertions.assertEquals(25, multiply);
    }
}

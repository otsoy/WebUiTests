package org.otsoi.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.otsoi.base.BaseTest;
import org.otsoi.entities.Result;
import org.otsoi.enums.InputFieldActions;
import org.otsoi.pageObjects.pages.MainPage;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.otsoi.constants.Constants.MAX_DEFAULT_VALUE;
import static org.otsoi.constants.Constants.MIN_DEFAULT_VALUE;

@Execution(ExecutionMode.CONCURRENT)
@Feature("Random Integer")
public class RandomIntegerTests extends BaseTest {
    private static Stream<Arguments> minMaxProvider() {
        return Stream.of(
                arguments(-500, 0),
                arguments(-11, 2),
                arguments(-230, 53102),
                arguments(3, 14),
                arguments(57, 102),
                arguments(105, 1006)
        );
    }

    @ParameterizedTest
    @MethodSource("minMaxProvider")
    public void CanGetIntegerTest(int min, int max) {
      MainPage page = openMainPage();
      page.setMinValue(String.valueOf(min));
      page.setMaxValue(String.valueOf(max));
      page.pressGenerate();

      Result result = page.getResult();
      Assertions.assertTrue(result.value() <= max && result.value() >= min);
    }

    @Test
    public void CanGetIntegerByEnterTest() {
        int min = -1;
        int max = 62;
        MainPage page = openMainPage();
        page.setMinValue(String.valueOf(min));
        page.setMaxValue(String.valueOf(max));
        page.pressKeyInMin(InputFieldActions.ENTER);

        Result result = page.getResult();
        Assertions.assertTrue(result.value() <= max && result.value() >= min);
    }

    @Test
    public void DefaultValuesTest() {

        MainPage page = openMainPage();
        Assertions.assertEquals(MIN_DEFAULT_VALUE + "", page.getInputMinValue());
        Assertions.assertEquals(MAX_DEFAULT_VALUE + "", page.getInputMaxValue());
    }

    private static Stream<Arguments> keyActionsProvider() {
        return Stream.of(
                arguments(InputFieldActions.UP, 1, 13),
                arguments(InputFieldActions.UP, 3, 15),
                arguments(InputFieldActions.DOWN,1, 11),
                arguments(InputFieldActions.DOWN, 4, 8)
        );
    }
    @ParameterizedTest
    @MethodSource("keyActionsProvider")
    public void MinCanBeChangeByArrowTest(InputFieldActions action, int number, int result) {
        int min = 12;
        MainPage page = openMainPage();
        page.setMinValue(String.valueOf(min));
        IntStream.range(0, number).forEach(i -> page.pressKeyInMin(action));

        Assertions.assertEquals(String.valueOf(result), page.getInputMinValue());

    }

    @ParameterizedTest
    @MethodSource("keyActionsProvider")
    public void MaxCanBeChangeByArrowTest(InputFieldActions action, int number, int result) {
        int min = 12;
        MainPage page = openMainPage();
        page.setMaxValue(String.valueOf(min));
        IntStream.range(0, number).forEach(i -> page.pressKeyInMax(action));

        Assertions.assertEquals(String.valueOf(result), page.getInputMaxValue());

    }

    @Test
    public void DefaultValuesUsedIfNotFilledTest() {

        MainPage page = openMainPage();
        page.setMinValue("");
        page.setMaxValue("");
        page.pressGenerate();
        Result result = page.getResult();

        Assertions.assertEquals(MIN_DEFAULT_VALUE, result.minValue());
        Assertions.assertEquals(MAX_DEFAULT_VALUE, result.maxValue());
    }

    @Test
    public void MaxIsUpdatedIfMinMoreOrEqualThanMax() {
        int min = -11;
        int max = -20;
        MainPage page = openMainPage();
        page.setMinValue(String.valueOf(min));
        page.setMaxValue(String.valueOf(max));
        page.pressGenerate();
        Result result = page.getResult();

        int updatedMax = min + 1;
        Assertions.assertEquals(min, result.minValue());
        Assertions.assertEquals(updatedMax, result.maxValue());
        Assertions.assertTrue(result.value() <= updatedMax && result.value() >= min);
    }

    @Test
    public void NonNumericValuesAreIgnored() {

        MainPage page = openMainPage();
        page.setMinValue("#$%^&*()_");
        page.setMaxValue("CVBNM<:OIUYT");

        Assertions.assertEquals("", page.getInputMinValue());
        Assertions.assertEquals("", page.getInputMaxValue());
    }

    private static Stream<Arguments> borderValuesProvider() {
        return Stream.of(
                arguments("1000000001", "Error: The maximum value must be an integer in the [-1000000000,1000000000] interval"),
                arguments("-1000000001", "Error: The minimum value must be an integer in the [-1000000000,1000000000] interval")
        );
    }
    @ParameterizedTest
    @MethodSource("borderValuesProvider")
    public void BorderValuesForMinTests(String value, String errorMsg) {

        MainPage page = openMainPage();
        page.setMinValue(value);
        page.pressGenerate();

        Assertions.assertEquals(errorMsg,page.getErrorText());
    }
    @Disabled("Unexpected behaviour")
    @ParameterizedTest
    @MethodSource("borderValuesProvider")
    public void BorderValuesForMaxTests(String value, String errorMsg) {

        MainPage page = openMainPage();
        page.setMaxValue(value);
        page.pressGenerate();

        Assertions.assertEquals(errorMsg,page.getErrorText());
    }
}

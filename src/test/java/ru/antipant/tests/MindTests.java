package ru.antipant.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import ru.antipant.domain.NavigationItem;
import ru.antipant.helpers.DriverUtils;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class MindTests extends TestBase {

    @Test
    @DisplayName("Проверка наличия заголовка у сайта")
    void titleTest() {
        step("Открыть 'https://www.comindware.com/ru/'", () ->
                open("https://www.comindware.com/ru/"));

        step("Заголовок должен содержать текст 'Цифровая трансформация бизнеса с Comindware'", () -> {
            String expectedTitle = "Цифровая трансформация бизнеса с Comindware";
            String actualTitle = title();

            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Test
    @DisplayName("Консоль не должна иметь ошибок")
    void consoleShouldNotHaveErrorsTest() {
        step("Открыть 'https://www.comindware.com/ru/'", () ->
                open("https://www.comindware.com/ru/"));

        step("В консоли не должно быть текста 'SEVERE'", () -> {
            String consoleLogs = DriverUtils.getConsoleLogs();
            String errorText = "SEVERE";

            assertThat(consoleLogs).doesNotContain(errorText);
        });
    }

    @EnumSource(NavigationItem.class)
    @ParameterizedTest(name = "Проверка текста в навигации по слову {0}")
    void navComMenuTest(NavigationItem testData) {
        step("Открыть 'https://www.comindware.com/ru/'", () ->
                open("https://www.comindware.com/ru/"));

        step("Наличие в навигации текста {0}", () ->
                $$(".nav-item")
                        .find(text(testData.rusName))
                        .shouldBe(visible));
    }

    @CsvSource(value = {
            "Разработка и исполнение бизнес-приложений | Единая среда разработки и выполнения бизнес-приложений, объединяющая встроенную СУБД, возможность разработки без кодирования, работу с данными, формами и документами.",
            "Управление бизнес-процессами (BPM система) | Комплексная Low-code система управления бизнес-процессами (BPMS): моделирование в нотации BPMN 2.0, автоматизация процессов, управление кейсами — надёжный фундамент для цифровой трансформации предприятия."
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Проверка открытия меню {0}, ожидаем {1}")
    void comProductComplexTest(String testData, String expectedResult) {
        step("Открыть 'https://www.comindware.com/ru/'", () ->
                open("https://www.comindware.com/ru/"));
        step("Открыть продукты", () ->
                $("#productsMenu").click());
        step("Открыть " + testData, () ->
                $(".menu-list").$(byText(testData)).click());
        step(("Ожидаем текст " + expectedResult), () ->
                $(".mb-sm-3.pageLead")
                        .shouldHave(text(expectedResult)));

    }
}
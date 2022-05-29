package ru.antipant.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import ru.antipant.domain.NavigationItem;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WebMindTest {
    @ValueSource(strings = {
            "Продукты",
            "Решения"
    })
    @ParameterizedTest(name = "Проверка текста в навигации по слову {0}")
    void  comMenuTest(String testData) {

        Selenide.open("https://www.comindware.com/ru/");

        $$(".nav-item")
                .find(text(testData))
                .shouldBe(visible);
    }

    @CsvSource(value = {
            "Разработка и исполнение бизнес-приложений | Единая среда разработки и выполнения бизнес-приложений, объединяющая встроенную СУБД, возможность разработки без кодирования, работу с данными, формами и документами.",
            "Управление бизнес-процессами (BPM система) | Комплексная Low-code система управления бизнес-процессами (BPMS): моделирование в нотации BPMN 2.0, автоматизация процессов, управление кейсами — надёжный фундамент для цифровой трансформации предприятия."
    },
            delimiter = '|'
    )
    @ParameterizedTest(name = "Проверка открытия меню {0}, ожидаем {1}")
    void  comProductComplexTest(String testData, String expectedResult) {
        Selenide.open("https://www.comindware.com/ru/");

        $("#productsMenu").click();

        $(".menu-list").$(byText(testData)).click();
        $(".mb-sm-3.pageLead")
                .shouldHave(text(expectedResult));

    }

    static Stream<Arguments> methodSourceExampleTest() {
        return Stream.of(
                Arguments.of("Разработка и исполнение бизнес-приложений", "Единая среда разработки"),
                Arguments.of("Управление бизнес-процессами (BPM система)", "Комплексная Low-code система")
        );
    }

    @MethodSource
    @ParameterizedTest
    void methodSourceExampleTest(String name, String description){
        Selenide.open("https://www.comindware.com/ru/");
        $("#productsMenu").click();
        $(".menu-list").$(byText(name)).click();
        $(".mb-sm-3.pageLead")
                .shouldHave(text(description));
    }


    @EnumSource(NavigationItem.class)
    @ParameterizedTest(name = "Проверка текста в навигации по слову {0}")
    void  navComMenuTest(NavigationItem testData) {

        Selenide.open("https://www.comindware.com/ru/");

        $$(".nav-item")
                .find(text(testData.rusName))
                .shouldBe(visible);
    }
    @AfterEach
    void close(){
        Selenide.closeWebDriver();
    }
}

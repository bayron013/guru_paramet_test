package guru.qa;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExamplesDataProvider {

    TestPages testData = new TestPages();

    @BeforeAll
    static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }


    @Test
    @DisplayName("Тест на проверку полноты списка хедера")
    @Tag("GENERAL")
    void chekHederMenuExistCorrectList() {
        List<String> expected = new ArrayList<>(List.of("Горячее", "Лучшее", "Свежее", "Подписки",
                "Сообщества", "Блоги", "Твои финансы", "Темы"));

        List<String> result = new ArrayList<>(testData.openPagePikabu()
                .getHeaderMenu());

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }


    @ValueSource(strings = {"Юмор", "Отношения", "Общение", "Наука", "IT"})
    @ParameterizedTest(name = "Проверка перехода из списка тем в указанную тему - {0} (ValueSource)")
    @Tag("TOPICS")
    void chooseThemesMustOpenWrightLink(String topic) {

        String result = testData.openPagePikabu()
                .goToThemesMenu()
                .openTopic(topic).getTitle();

        assertEquals(topic, result);
    }

    @CsvSource(value = {
            "Oleg, Korolev",
            "Yuri, Gagarin"
    })
    @ParameterizedTest(name =
            "На форме подтверждения регистрации должны отображаться имя {0} и фамилия {1} пользователя (CsvSource)")
    void confirmCorrectNameAndSurnameOnTable(String name, String surName) {

        testData.openPageTests();
        $("#firstName").setValue(name);
        $("#lastName").setValue(surName);
        $("#genterWrapper").$(byText("Other")).click();
        $("#userNumber").setValue("9879879887");
        $("#submit").click();

        $(".table-responsive").$(byText("Student Name"))
                .parent().shouldHave(text(name + " " + surName));

    }

    @CsvFileSource(resources = "/test_data/confirmCorrectNameAndSurnameOnTableCsv.csv")
    @ParameterizedTest(name = "На форме подтверждения регистрации должны отображаться имя {0} " +
            "и фамилия {1} пользователя (CsvFileSource)")
    void confirmCorrectNameAndSurnameOnTableCsv(String name, String surName) {

        testData.openPageTests();
        $("#firstName").setValue(name);
        $("#lastName").setValue(surName);
        $("#genterWrapper").$(byText("Other")).click();
        $("#userNumber").setValue("9879879887");
        $("#submit").click();

        $(".table-responsive").$(byText("Student Name"))
                .parent().shouldHave(text(name + " " + surName));

    }

}

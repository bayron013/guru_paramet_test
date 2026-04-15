package guru.qa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestExamplesPikabu extends TestBase {

    @Test
    @DisplayName("Тест на проверку полноты списка хедера")
    @Tag("GENERAL")
    void chekHederMenuExistCorrectList() {
        List<String> expected = new ArrayList<>(List.of("Горячее", "Лучшее", "Свежее", "Подписки",
                "Сообщества", "Блоги", "Твои финансы", "Темы"));

        List<String> result = new ArrayList<>(testData.openPage()
                .getHeaderMenu());

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }


    @ValueSource(strings = {"Юмор", "Отношения", "Общение", "Наука", "IT"})
    @ParameterizedTest(name = "Проверка перехода из списка тем в указанную тему - {0}")
    @Tag("TOPICS")
    void chooseThemesMustOpenWrightLink(String topic) {

        String result = testData.openPage()
                .goToThemesMenu()
                .openTopic(topic).getTitle();

        assertEquals(topic, result);
    }


}

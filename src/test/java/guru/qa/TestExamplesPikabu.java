package guru.qa;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TestExamplesPikabu extends TestBase {

    @Test
    @ValueSource(strings = {"Политика", "18+", "Игры", "Юмор", "Отношения", "Здоровье",
            "Путешествия", "Спорт", "Хобби", "Сервис", "Природа", "Бизнес", "Транспорт",
            "Общение", "Юриспруденция", "Наука", "IT", "Животные", "Кино и сериалы",
            "Экономика", "Кулинария", "История"
    })
    @ParameterizedTest(name = "Проверка перехода из списка тем в указанную тему - {0}")
    void chouseThemesMustOpenWrightLink(String topic) {

        testData.openPage()
                .openTopic(topic);

        $(".story__header").$(".story__topic").shouldHave(text(topic));



    }
}

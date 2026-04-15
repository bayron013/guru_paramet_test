package guru.qa;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestData {


    public TestData openPage() {
        open("");

        return this;
    }

    public TestData goToTemesMenu() {
        $(".header-menu__extra").click();
        $(".popup__wrapper").$("a[href='/themes']").click();

//        Альтернативный способ решения (для обучения)
//        Selenide.executeJavaScript("document.querySelector('a[href=\"/themes\"]').click()");

        return this;
    }

    public TestData openTopic(String themes) {
        $(".page-topics__topics").$(byAttribute("alt", themes)).click();

        return this;
    }

}

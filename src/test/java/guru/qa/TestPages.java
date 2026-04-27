package guru.qa;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;

public class TestPages {


    public TestPages openPagePikabu() {
        open("https://pikabu.ru/");

        return this;
    }

    public TestPages openPageTests() {
        open("https://demoqa.com/automation-practice-form");
        executeJavaScript("""
            document.getElementById('fixedban')?.remove();
            document.querySelector('footer')?.remove();
            """);

        return this;
    }

    public TestPages goToThemesMenu() {
        $(".header-menu__extra").click();
        $(".popup__wrapper").$("a[href='/themes']").click();

//        Альтернативный способ решения (для обучения)
//        Selenide.executeJavaScript("document.querySelector('a[href=\"/themes\"]').click()");

        return this;
    }

    public TestPages openTopic(String themes) {
        $(".page-topics__topics").$(byAttribute("alt", themes)).click();

        return this;
    }

    public String getTitle() {
        String val = $(".story__main").$(".story__topic span").getText();
        return val;
    }

    public List<String> getHeaderMenu() {
        List<String> menuList = new ArrayList<>($$(".header-menu a").texts());

        return menuList;
    }


}



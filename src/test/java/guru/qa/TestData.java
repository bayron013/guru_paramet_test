package guru.qa;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TestData {


    public TestData openPage() {
        open("themes");

        return this;
    }

    public TestData goToTemesMenu() {
        $(".header-menu").findElement(By.xpath("//span[text()='Темы']")).submit();

        return this;
    }

    public TestData openTopic(String themes) {
        $$(".page-topics").find(text(themes)).click();

        return this;
    }

}

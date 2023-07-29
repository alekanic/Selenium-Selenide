import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.ClientConfig;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class test {

    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    void happyTest() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("[data-test-id=phone] input").setValue("+79219008080");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."))
        ;
    }

    @Test

    // клик по кнопке Продолжить, не заполняя ни одно поле
    void emptyTest() {

        open("http://localhost:9999/");
        $("button").click();

        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"))
        ;
    }

    @Test

    // заполнено только поле Фамилия и имя + клик на Продолжить
    void testWithTheNameOnly() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("button").click();

        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"))
        ;
    }

    @Test

    // не поставлена галочка в чек-боксе
    void testWithoutCheckbox() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("[data-test-id=phone] input").setValue("+79219008080");
        $("button").click();

        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
        ;
    }

    @Test
    void testWithNameInEnglish() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Vasilisa Ilinichna");
        $("[data-test-id=phone] input").setValue("+79219008080");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"))
        ;
    }

    @Test
    void testWithWrongTelephoneNumber() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("[data-test-id=phone] input").setValue("+7921");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"))
        ;
    }

}


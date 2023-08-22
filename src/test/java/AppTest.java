import com.codeborne.selenide.Condition;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

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

        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"))
        ;

    }

    @Test
    void testWithoutName() {

        open("http://localhost:9999/");
        $("[data-test-id=phone] input").setValue("+79219008080");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"))
        ;
    }

    @Test

    // не заполнен телефон
    void testWithoutThePhone() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"))
        ;
    }

    @Test

    // пустой чек-бокс
    void testWithoutCheckbox() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("[data-test-id=phone] input").setValue("+79219008080");
        $("button").click();

        $("[data-test-id='agreement'].input_invalid").shouldBe(visible)
        ;
    }

    @Test
    // невалидное имя
    void testWithNameInEnglish() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Vasilisa Ilinichna");
        $("[data-test-id=phone] input").setValue("+79219008080");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
        ;
    }

    @Test
    void testWithWrongTelephoneNumber() {

        open("http://localhost:9999/");
        $("[data-test-id=name] input").setValue("Василиса Ильинична");
        $("[data-test-id=phone] input").setValue("+7921");
        $("[data-test-id=agreement]").click();
        $("button").click();

        $("[data-test-id='phone'].input_invalid").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
        ;
    }

}


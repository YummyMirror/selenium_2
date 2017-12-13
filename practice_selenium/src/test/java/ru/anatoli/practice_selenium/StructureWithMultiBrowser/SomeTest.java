package ru.anatoli.practice_selenium.StructureWithMultiBrowser;

import org.testng.annotations.Test;

public class SomeTest extends TestBaseOfTests {
    @Test
    public void test() {
        app.getAppBase().openUrl("https://google.by");
    }
}
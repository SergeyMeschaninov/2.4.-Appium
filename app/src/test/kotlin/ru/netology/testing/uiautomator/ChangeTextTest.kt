package ru.netology.testing.appium

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.net.URL
import java.time.Duration

const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

class ChangeTextAppiumTest {

    private lateinit var driver: AndroidDriver
    private lateinit var wait: WebDriverWait

    private val textToSet = "Netology"
    private val emptyText = ""

    @Before
    fun setUp() {
        val options = UiAutomator2Options()
            .setPlatformName("Android")
            .setDeviceName("Android Device")
            .setAutomationName("UiAutomator2")
            .setApp("D:/sample/app/build/outputs/apk/debug/app-debug.apk")
            .setNoReset(false)

        driver = AndroidDriver(URL("http://localhost:4723/"), options)
        wait = WebDriverWait(driver, Duration.ofSeconds(5))
    }

    @After
    fun tearDown() {
        if (::driver.isInitialized) {
            driver.quit()
        }
    }

    @Test
    fun testEmptyStringInput() {
        val inputField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("$MODEL_PACKAGE:id/userInput"))
        )

        val originalText = driver.findElement(AppiumBy.id("$MODEL_PACKAGE:id/textToBeChanged")).text

        inputField.sendKeys(emptyText)
        driver.findElement(AppiumBy.id("$MODEL_PACKAGE:id/buttonChange")).click()

        val resultText = driver.findElement(AppiumBy.id("$MODEL_PACKAGE:id/textToBeChanged")).text
        assertEquals(originalText, resultText)
    }

    @Test
    fun testOpenTextInNewActivity() {
        val inputField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("$MODEL_PACKAGE:id/userInput"))
        )
        inputField.sendKeys(textToSet)

        val openActivityButton = driver.findElement(AppiumBy.id("$MODEL_PACKAGE:id/buttonActivity"))
        openActivityButton.click()

        val resultActivityView = wait.until(
            ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("$MODEL_PACKAGE:id/text"))
        )

        assertEquals(textToSet, resultActivityView.text)
    }
}

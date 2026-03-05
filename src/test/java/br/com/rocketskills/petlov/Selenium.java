package br.com.rocketskills.petlov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

class Selenium {

	WebDriver driver;

	@BeforeEach
	void start() {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	@AfterEach
	void finish() {
		driver.close();
	}


	@Test
	@DisplayName("Deve poder cadastrar um ponto de doação")
	void createPoint() {
		

		driver.get("https://petlov.vercel.app/signup");

		WebElement title = driver.findElement(By.cssSelector("h1"));

		Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(d -> title.isDisplayed());

		assertEquals("Cadastro de ponto de doação", title.getText(), "Verificando o título da página de cadastro");

		WebElement name = driver.findElement(By.cssSelector("input[placeholder='Nome do ponto de doação']"));
		name.sendKeys("Ponto de Doação Teste");

		WebElement email = driver.findElement(By.cssSelector("input[name=email]"));
		email.sendKeys("pontoddoacao@teste.com");

		WebElement cep = driver.findElement(By.cssSelector("input[name=cep]"));
		cep.sendKeys("04534011");

		WebElement cepbutton = driver.findElement(By.cssSelector("input[value='Buscar CEP']"));
		cepbutton.click();

		WebElement numberCep = driver.findElement(By.cssSelector("input[name=addressNumber]"));
		numberCep.sendKeys("1000");

		WebElement detailsCep = driver.findElement(By.cssSelector("input[name=addressDetails]"));
		detailsCep.sendKeys("Ao lado do supermercado");

		driver.findElement(By.xpath("//span[text()='Cachorros']/..")).click();

		driver.findElement(By.className("button-register")).click();

		WebElement result = driver.findElement(By.cssSelector("#success-page p"));

		Wait<WebDriver> waitResult = new WebDriverWait(driver, Duration.ofSeconds(2));
		waitResult.until(d -> result.isDisplayed());

		String target = "Seu ponto de doação foi adicionado com sucesso. Juntos, podemos criar um mundo onde todos os animais recebam o amor e cuidado que merecem.";

		assertEquals(target, result.getText(), "Verificando a mensagem de sucesso"); 

		
	}
}

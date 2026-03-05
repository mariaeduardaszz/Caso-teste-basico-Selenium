package br.com.rocketskills.petlov;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

class PontoDoacao {
	String nome;
	String email;
	String cep;
	String numero;
	String complemento;
	String pets;

	public PontoDoacao(String nome, String email, String cep, String numero, String complemento, String pets) {
		this.nome = nome;
		this.email = email;
		this.cep = cep;
		this.numero = numero;
		this.complemento = complemento;
		this.pets = pets;
	}

}

class Cadastro {
	private void submeteFormulario(PontoDoacao ponto) {
		$("input[placeholder='Nome do ponto de doação']").setValue(ponto.nome);
		$("input[name=email]").setValue(ponto.email);
		$("input[name=cep]").setValue(ponto.cep);
		$("input[value='Buscar CEP']").click();
		$("input[name=addressNumber]").setValue(ponto.numero);
		$("input[name=addressDetails]").setValue(ponto.complemento);
		$(By.xpath("//span[text()='" + ponto.pets + "']/..")).click();
		$(".button-register").click();

	}

	private void acessarformulario() {
		open("https://petlov.vercel.app/signup");
		$("h1").shouldHave(text("Cadastro de ponto de doação"));
	}

	@Test
	@DisplayName("Deve poder cadastrar um ponto de doação")
	void createPoint() {

		PontoDoacao ponto = new PontoDoacao(
				"Estação pet",
				"pontodoacao@teste.com",
				"04534011",
				"1000",
				"Ao lado do supermercado",
				"Cachorros");

		acessarformulario();

		submeteFormulario(ponto);

		String target = "Seu ponto de doação foi adicionado com sucesso. Juntos, podemos criar um mundo onde todos os animais recebam o amor e cuidado que merecem.";
		$("#success-page p").shouldHave(text(target));

	}

	@Test
	@DisplayName("Não deve cadastrar com email invalido")
	void emailInvalido() {

		PontoDoacao ponto = new PontoDoacao(
				"Caes e gatos",
				"atendimento&caesegatos.com",
				"04534011",
				"1000",
				"Ao lado do supermercado",
				"Gatos");

		acessarformulario();

		submeteFormulario(ponto);

		String target = "Informe um email válido";
		$(".alert-error").shouldHave(text(target));

	}
}

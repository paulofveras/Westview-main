package br.unitins.comics.resources;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.MediaType;
import br.unitins.comics.dto.EnderecoDTO;
import br.unitins.comics.dto.FornecedorDTO;
import br.unitins.comics.dto.TelefoneDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
public class FornecedorResourceTest {
    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void createTest(){
        EnderecoDTO endereco = new EnderecoDTO(55555555, "Avenida dos Fornecedores", 500);
        TelefoneDTO telefone = new TelefoneDTO("11", "955554444");

        FornecedorDTO dto = new FornecedorDTO(
            "Amazon Livros Ltda",
            "Amazon", // Nome Fantasia
            "11222333000144", // CNPJ
            "jeffbezos@amazon.com",
            telefone,
            endereco
        );

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .post("/fornecedores")
            .then()
            .statusCode(201)
            .body("nome", is("Amazon Livros Ltda"))
            .body("cnpj", is("11222333000144"));
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void findByIdTest(){
        given()
            .when()
            .get("/fornecedores/1") // Panini, conforme import.sql
            .then()
            .statusCode(200)
            .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void findByNomeTest(){
        given()
        .when()
        .get("/fornecedores/1")
        .then()
        .statusCode(200)
        .body("nome", is("Panini"));
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void updateTest(){
        EnderecoDTO endereco = new EnderecoDTO(55555555, "Avenida dos Fornecedores", 500);
        TelefoneDTO telefone = new TelefoneDTO("11", "955554444");

        FornecedorDTO dto = new FornecedorDTO(
            "Amazon Livros Ltda",
            "Mercado Livre", // Nome Fantasia
            "11222333000144", // CNPJ
            "jeffbezos@amazon.com",
            telefone,
            endereco
        );

        given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(dto)
        .when()
        .put("/fornecedores/2")
        .then()
        .statusCode(204);
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void findAllTest(){
        given()
            .when()
            .get("/fornecedores")
            .then()
            .statusCode(200)
            .body("nome", hasItem(is("Panini")));
    }


    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void deleteTest(){
        given()
        .when()
        .pathParam("id", 3)
        .delete("/fornecedores/{id}")
        .then()
        .statusCode(204);
    }
    
}

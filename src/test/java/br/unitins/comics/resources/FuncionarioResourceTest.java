package br.unitins.comics.resources;

import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.MediaType;
import br.unitins.comics.dto.EnderecoDTO;
import br.unitins.comics.dto.FuncionarioDTO;
import br.unitins.comics.dto.TelefoneDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;

@QuarkusTest
public class FuncionarioResourceTest {
@Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void createTest(){
        EnderecoDTO endereco = new EnderecoDTO(77777777, "Rua do Funcionario", 10);
        TelefoneDTO telefone = new TelefoneDTO("63", "977778888");
        
        FuncionarioDTO dto = new FuncionarioDTO(
            "Leandra",
            "99988877766", // CPF
            "Vendedora",
            "leandra@gmail.com",
            "lean",
            "111",
            telefone,
            endereco
        );

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .post("/funcionarios")
            .then()
            .statusCode(201)
            .body("nome", is("Leandra"));
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void findByIdTest(){
        given()
            .when()
            .get("/funcionarios/1") // João, conforme import.sql
            .then()
            .statusCode(200)
            .body("id", is(1));
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void findByNomeTest(){
        given()
        .when()
        .get("/funcionarios/1")
        .then()
        .statusCode(200)
        .body("nome", is("João"));
    }


    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void updateTest(){
        EnderecoDTO endereco = new EnderecoDTO(99999999, "Rua Nova", 50);
        TelefoneDTO telefone = new TelefoneDTO("11", "911112222");

        FuncionarioDTO dto = new FuncionarioDTO(
            "Leandra",
            "99988877766", // CPF
            "Vendedora",
            "leandra@gmail.com",
            "lean",
            "111",
            telefone,
            endereco
        );

        given()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dto)
            .when()
            .put("/clientes/2") // Atualizando o cliente com ID 2 (Visao)
            .then()
            .statusCode(204);
    }



    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void findAllTest(){
        given()
        .when()
        .get("/funcionarios")
        .then()
        .statusCode(200)
        .body("nome", hasItem(is("Mário")));;
    }

    @Test
    @TestSecurity(user = "tester", roles = "Funcionario")
    public void deleteTest(){
        given()
        .when()
        .pathParam("id", 3)
        .delete("/funcionarios/{id}")
        .then()
        .statusCode(204);
    }
    
}

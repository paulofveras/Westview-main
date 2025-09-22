package br.unitins.comics.resource;

import br.unitins.comics.model.Material;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/materiais")
@Produces(MediaType.APPLICATION_JSON)
public class MaterialResource {

    @GET
    @RolesAllowed({"Funcionario", "Cliente"})
    public Response findAll() {
        List<Material> materiais = Arrays.asList(Material.values());
        return Response.ok(materiais).build();
    }
}
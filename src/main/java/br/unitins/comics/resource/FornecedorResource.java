package br.unitins.comics.resource;

import org.jboss.logging.Logger;

import br.unitins.comics.dto.FornecedorDTO;
import br.unitins.comics.dto.FornecedorResponseDTO;
import br.unitins.comics.service.FornecedorService;
import br.unitins.comics.util.PageResult;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/fornecedores")
public class FornecedorResource {
    
    @Inject
    public FornecedorService fornecedorService;
    private static final Logger LOG = Logger.getLogger(FornecedorResource.class);

    @GET
    @RolesAllowed("Funcionario")
    public Response findPaged(
            @QueryParam("q") String q,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        LOG.infof("Buscando fornecedores. Termo de busca: %s", q);
        PageResult<FornecedorResponseDTO> result = fornecedorService.findPaged(q, page, pageSize);
        return Response.ok(result).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("Funcionario")
    public Response findAll() {
        LOG.info("Executando o findAll para listagem em formulários.");
        return Response.ok(fornecedorService.findAll()).build();
    }

    @GET
    @RolesAllowed("Funcionario")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Executando o metodo findById. Id: %s", id.toString());
        return Response.ok(fornecedorService.findById(id)).build();
    }

    @POST
    @RolesAllowed("Funcionario")
    public Response create(@Valid FornecedorDTO dto) {
        LOG.info("Criando um novo fornecedor");
        try {
            FornecedorResponseDTO fornecedor = fornecedorService.create(dto);
            LOG.infof("Fornecedor criado com sucesso. Nome: %s", dto.nome());
            return Response.status(Status.CREATED).entity(fornecedor).build();
        } catch (Exception e) {
            LOG.error("Erro ao criar fornecedor", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @RolesAllowed("Funcionario")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FornecedorDTO dto) {
        LOG.infof("Atualizando fornecedor. Id: %s", id.toString());
        fornecedorService.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @RolesAllowed("Funcionario")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando fornecedor. Id: %s", id.toString());
        fornecedorService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}

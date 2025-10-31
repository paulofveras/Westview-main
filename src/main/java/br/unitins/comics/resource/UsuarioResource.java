package br.unitins.comics.resource;

import org.jboss.logging.Logger;

import br.unitins.comics.dto.UsuarioDTO;
import br.unitins.comics.dto.UsuarioListResponseDTO;
import br.unitins.comics.dto.UsuarioUpdateDTO;
import br.unitins.comics.service.UsuarioService;
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
@Path("/usuarios")
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed("Administrador")
    public Response findPaged(
        @QueryParam("q") String q,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        LOG.infof("Listando usuarios. Termo: %s", q);
        PageResult<UsuarioListResponseDTO> result = usuarioService.findPaged(q, page, pageSize);
        return Response.ok(result).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("Administrador")
    public Response findAll() {
        LOG.info("Listando todos os usuarios.");
        return Response.ok(usuarioService.findAll()).build();
    }

    @GET
    @RolesAllowed("Administrador")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando usuario %s", id);
        UsuarioListResponseDTO response = usuarioService.findById(id);
        return Response.ok(response).build();
    }

    @POST
    @RolesAllowed("Administrador")
    public Response create(@Valid UsuarioDTO dto) {
        LOG.infof("Criando usuario %s", dto.username());
        UsuarioListResponseDTO response = usuarioService.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @RolesAllowed("Administrador")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid UsuarioUpdateDTO dto) {
        LOG.infof("Atualizando usuario %s", id);
        usuarioService.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @RolesAllowed("Administrador")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Removendo usuario %s", id);
        usuarioService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("Administrador")
    @Path("/count")
    public long count(@QueryParam("q") String q) {
        return (q == null || q.isBlank())
            ? usuarioService.count()
            : usuarioService.countFiltered(q);
    }
}


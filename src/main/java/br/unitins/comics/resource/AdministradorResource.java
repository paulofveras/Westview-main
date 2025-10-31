package br.unitins.comics.resource;

import org.jboss.logging.Logger;

import br.unitins.comics.dto.AdministradorDTO;
import br.unitins.comics.dto.AdministradorResponseDTO;
import br.unitins.comics.dto.UpdatePasswordDTO;
import br.unitins.comics.dto.UpdateUsernameDTO;
import br.unitins.comics.service.AdministradorService;
import br.unitins.comics.util.PageResult;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
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
@Path("/administradores")
public class AdministradorResource {

    @Inject
    AdministradorService administradorService;

    private static final Logger LOG = Logger.getLogger(AdministradorResource.class);

    @GET
    @RolesAllowed("Administrador")
    public Response findPaged(
        @QueryParam("q") String q,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        LOG.infof("Buscando administradores. Termo: %s", q);
        PageResult<AdministradorResponseDTO> result = administradorService.findPaged(q, page, pageSize);
        return Response.ok(result).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("Administrador")
    public Response findAll() {
        LOG.info("Listando todos administradores.");
        return Response.ok(administradorService.findAll()).build();
    }

    @GET
    @RolesAllowed("Administrador")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Buscando administrador por id %s", id);
        return Response.ok(administradorService.findById(id)).build();
    }

    @GET
    @RolesAllowed("Administrador")
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.infof("Buscando administradores por nome %s", nome);
        return Response.ok(administradorService.findByNome(nome)).build();
    }

    @POST
    @RolesAllowed("Administrador")
    public Response create(@Valid AdministradorDTO dto) {
        LOG.info("Criando administrador.");
        return Response.status(Status.CREATED).entity(administradorService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("Administrador")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid AdministradorDTO dto) {
        LOG.infof("Atualizando administrador %s", id);
        administradorService.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @RolesAllowed("Administrador")
    @Path("/update-password/{id}")
    public Response updatePassword(@PathParam("id") Long id, UpdatePasswordDTO dto) {
        LOG.infof("Atualizando senha do administrador %s", id);
        administradorService.updatePassword(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @RolesAllowed("Administrador")
    @Path("/update-username/{id}")
    public Response updateUsername(@PathParam("id") Long id, UpdateUsernameDTO dto) {
        LOG.infof("Atualizando username do administrador %s", id);
        administradorService.updateUsername(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @RolesAllowed("Administrador")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Removendo administrador %s", id);
        administradorService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @RolesAllowed("Administrador")
    @Path("/count")
    public long count(@QueryParam("q") String q) {
        return (q == null || q.isBlank())
            ? administradorService.count()
            : administradorService.countFiltered(q);
    }
}

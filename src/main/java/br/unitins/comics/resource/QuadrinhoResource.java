package br.unitins.comics.resource;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.comics.dto.QuadrinhoDTO;
import br.unitins.comics.dto.QuadrinhoResponseDTO;
import br.unitins.comics.form.ImageForm;
import br.unitins.comics.service.QuadrinhoFileServiceImpl;
import br.unitins.comics.service.QuadrinhoService;
import br.unitins.comics.util.PageResult;
import br.unitins.comics.validation.ValidationException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/quadrinhos")
public class QuadrinhoResource {

    @Inject
    public QuadrinhoService quadrinhoService;

    @Inject
    public QuadrinhoFileServiceImpl fileService;

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @GET
    @RolesAllowed("Funcionario")
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        LOG.infof("Executando o metodo findById. Id: %s", id.toString());
        return Response.ok(quadrinhoService.findById(id)).build();
    }

    // >>> AJUSTADO: agora aceita paginação básica (padrão do slide)
    @GET
    // @RolesAllowed({"Funcionario", "Cliente"})
    public Response findAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("12") int pageSize) {
        LOG.info("Executando o findAll com paginação");
        return Response.ok(quadrinhoService.findAll(page, pageSize)).build();
    }

    @GET
    @RolesAllowed("Funcionario")
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.info("Executando o metodo findBynome");
        return Response.ok(quadrinhoService.findByNome(nome)).build();
    }

    // >>> NOVO: contador total ou filtrado (atividade do slide)
    @GET
    @Path("/count")
    public long count(@QueryParam("q") String q) {
        return (q == null || q.isBlank()) ? quadrinhoService.count()
                                          : quadrinhoService.countFiltered(q);
    }

    // >>> NOVO: endpoint que retorna o JSON completo de paginação + pesquisa
    @GET
    @Path("/paged")
    public PageResult<QuadrinhoResponseDTO> findPaged(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("12") int pageSize,
            @QueryParam("q") String q) {
        return quadrinhoService.findPaged(q, page, pageSize);
    }

    @POST
    @RolesAllowed("Funcionario")
    public Response create(@Valid QuadrinhoDTO dto) {
        LOG.info("Criando uma nova quadrinho");
        try {
            LOG.infof("Quadrinho criada com sucesso. Nome: %s", dto.nome());
            return Response.status(Status.CREATED).entity(quadrinhoService.create(dto)).build();
        } catch (Exception e) {
            LOG.error("Erro ao criar quadrinho", e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @RolesAllowed("Funcionario")
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, QuadrinhoDTO dto) {
        LOG.debugf("DTO Atualizado: %s", dto);
        quadrinhoService.update(id, dto);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @RolesAllowed("Funcionario")
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        LOG.infof("Deletando quadrinho. Id: %s", id.toString());
        quadrinhoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @PATCH
    @RolesAllowed("Funcionario")
    @Path("/{id}/image/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@PathParam("id") Long id, @MultipartForm ImageForm form) {
        LOG.info("Fazendo upload de imagem");
        try {
            fileService.salvar(id, form.getNomeImagem(), form.getImagem());
            return Response.noContent().build();
        } catch (NotFoundException e) {
            LOG.error("Quadrinho não encontrado para upload de imagem", e);
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValidationException e) {
            LOG.error("Erro de validação ao fazer upload de imagem", e);
            return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).entity(e.getMessage()).build();
        }
    }

    @GET
    // @RolesAllowed("Funcionario")
    @Path("/image/download/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        LOG.info("Fazendo download de imagem");
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }
}

package br.unitins.comics.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.unitins.comics.model.Quadrinho;
import br.unitins.comics.repository.QuadrinhoRepository;
import br.unitins.comics.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class QuadrinhoFileServiceImpl implements FileService {
    // ex = /user/janio/quarkus/images/quadrinho
    private final String PATH_USER = System.getProperty("user.home")
        + File.separator + "quarkus"
        + File.separator + "images"
        + File.separator + "quadrinho" + File.separator;

    @Inject
    QuadrinhoRepository quadrinhoRepository;

    @Override
    @Transactional
    public void salvar(Long id, String nomeImagem, byte[] imagem) {
        Quadrinho quadrinho = quadrinhoRepository.findById(id);
        if (quadrinho == null) {
            throw new NotFoundException("Quadrinho não encontrado para upload de imagem.");
        }
        try {
            quadrinho.setNomeImagem(salvarImagem(nomeImagem, imagem));
        } catch (IOException e) {
            throw new ValidationException("imagem", e.getMessage());
        }
    }

    private String salvarImagem(String nomeImagem, byte[] imagem) throws IOException {
        // verificar o tipo da imagem
        String mimeType = detectarMimeType(nomeImagem, imagem);
        List<String> listMimeType = Arrays.asList("image/jpg", "image/gif", "image/png", "image/jpeg");
        if (mimeType == null || !listMimeType.contains(mimeType))
            throw new IOException("Tipo de imagem não suportado.");

        // verificar o tamanho do arquivo - nao permitir maior que 10mb
        if (imagem.length > 1024 * 1024 * 10) {
            throw new IOException("Arquivo muito grande, tamanho máximo 10mb.");
        }

        // criar pasta quando nao existir
        File diretorio = new File(PATH_USER);
        if (!diretorio.exists()) 
            diretorio.mkdirs();

        // gerar nome do arquivo 
        String nomeArquivo = UUID.randomUUID() 
                                + "." 
                                + mimeType.substring(mimeType.lastIndexOf("/") + 1);
        String path = PATH_USER + nomeArquivo;

        // salvar o arquivo
        File file = new File(path);
        if (file.exists())
          throw new IOException("Este arquivo ja existe. Programador, tu deve melhorar esse codigo");

        // criar o arquivo no SO
        file.createNewFile();

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagem);
        fos.flush();
        fos.close();

        return nomeArquivo;
    }

    private String detectarMimeType(String nomeImagem, byte[] imagem) throws IOException {
        String mimeType = null;

        if (nomeImagem != null && !nomeImagem.isBlank()) {
            try {
                mimeType = Files.probeContentType(Path.of(nomeImagem));
            } catch (Exception e) {
                mimeType = null;
            }
            if (mimeType == null) {
                mimeType = URLConnection.guessContentTypeFromName(nomeImagem);
            }
        }

        if (mimeType == null && imagem != null) {
            try (BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(imagem))) {
                mimeType = URLConnection.guessContentTypeFromStream(bis);
            }
        }

        if (mimeType == null && imagem != null) {
            Path tempFile = Files.createTempFile("quadrinho-upload", ".tmp");
            try {
                Files.write(tempFile, imagem);
                mimeType = Files.probeContentType(tempFile);
            } finally {
                Files.deleteIfExists(tempFile);
            }
        }

        return mimeType;
    }


    @Override
    public File download(String nomeImagem) {
       return new File(PATH_USER + nomeImagem);
    }

}
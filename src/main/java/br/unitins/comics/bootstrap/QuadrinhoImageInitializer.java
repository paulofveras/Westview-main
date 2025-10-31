package br.unitins.comics.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class QuadrinhoImageInitializer {

    private static final Logger LOG = Logger.getLogger(QuadrinhoImageInitializer.class);

    private static final Path IMAGE_DIRECTORY = Path.of(
            System.getProperty("user.home"),
            "quarkus",
            "images",
            "quadrinho");

    private static final Map<String, String> SEEDED_IMAGES = Map.of(
            "secret-wars.png", "images/quadrinho/secret-wars.png",
            "x-men.png", "images/quadrinho/x-men.png",
            "get-up.png", "images/quadrinho/get-up.png");

    void ensureSeedImages(@Observes StartupEvent event) {
        try {
            Files.createDirectories(IMAGE_DIRECTORY);
            for (Map.Entry<String, String> entry : SEEDED_IMAGES.entrySet()) {
                Path target = IMAGE_DIRECTORY.resolve(entry.getKey());
                if (Files.notExists(target)) {
                    try (InputStream in = Thread.currentThread()
                                                .getContextClassLoader()
                                                .getResourceAsStream(entry.getValue())) {
                        if (in == null) {
                            LOG.warnf("Imagem seed %s não encontrada no classpath.", entry.getValue());
                            continue;
                        }
                        Files.copy(in, target);
                        LOG.debugf("Imagem padrão criada em %s", target);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("Não foi possível inicializar as imagens padrão dos quadrinhos.", e);
        }
    }
}

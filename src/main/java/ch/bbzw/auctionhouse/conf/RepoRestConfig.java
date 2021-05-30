package ch.bbzw.auctionhouse.conf;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class RepoRestConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration repositoryRestConfiguration, final CorsRegistry cors) {
        repositoryRestConfiguration.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
        repositoryRestConfiguration.setBasePath("/api");
    }
}

package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "br.com.alura.screenmatch.model")
@EnableJpaRepositories(basePackages = "br.com.alura.screenmatch.repository")

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	@Autowired
	private SeriesRepository repositorio;

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Principal painel = new Principal(repositorio);
		painel.exibirMenu();

    }
}

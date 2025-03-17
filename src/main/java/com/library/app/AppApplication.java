package com.library.app;
import com.library.app.entity.Genre;
import com.library.app.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Autowired
	private GenreService genreService;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		genreService.save(new Genre("HORROR"));
//		genreService.save(new Genre("DRAMA"));
//		genreService.save(new Genre("ROMANTIC"));
//		genreService.save(new Genre("COMEDY"));
	}
}

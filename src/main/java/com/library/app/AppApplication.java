package com.library.app;
import com.library.app.dto.GenreDTO;
import com.library.app.entity.Role;
import com.library.app.entity.User;
import com.library.app.repository.UserRepository;
import com.library.app.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Set;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Autowired
	private GenreService genreService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User("root", "$2a$12$TH59u6xE84rdOXV2DRczn.kq6zFc/RKKeZfHdAXdTUhg2rufAcb7.", Set.of(Role.ADMIN.getRoleName(), Role.USER.getRoleName())));
		userRepository.save(new User("user", "$2a$12$TH59u6xE84rdOXV2DRczn.kq6zFc/RKKeZfHdAXdTUhg2rufAcb7.", Set.of(Role.USER.getRoleName())));
		genreService.save(new GenreDTO("HORROR"));
		genreService.save(new GenreDTO("DRAMA"));
		genreService.save(new GenreDTO("ROMANTIC"));
		genreService.save(new GenreDTO("COMEDY"));
	}
}

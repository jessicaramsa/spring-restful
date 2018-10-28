package hello.demo.controlador;

import org.springframework.data.repository.CrudRepository;
import hello.demo.controlador.User;

public interface UserRepository extends CrudRepository<User, String> {
	
}

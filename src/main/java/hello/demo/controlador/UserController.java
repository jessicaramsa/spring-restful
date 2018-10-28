package hello.demo.controlador;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import hello.demo.controlador.User;
import hello.demo.controlador.UserRepository;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/create")
    public String create(@RequestBody User user) {
    	userRepository.save(user);
        return "User: " + user.getName() + " saved.";
    }
    
    @DeleteMapping("/delete/byName/{name}")
    public @ResponseBody String deleteByName(@PathVariable String name) {
    	if (userRepository.existsById(name)) {
	    	userRepository.deleteById(name);
	        return "User: " + name + " deleted.";
    	} else return error("User " + name + " not found.");
    }
    
    @DeleteMapping("/delete/all")
    public @ResponseBody String deleteAll() {
    	userRepository.deleteAll();
        return "Users deleted.";
    }
    
    @GetMapping("/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @GetMapping("/search/byName/{name}")
    public @ResponseBody Object searchByName(@PathVariable String name) {
    	if (userRepository.existsById(name)) {
    		return userRepository.findById(name).get();
    	} else return error("User " + name + " not found.");
    }

    @GetMapping("/search/byEmail/{email}")
    public @ResponseBody Object searchByEmail(@PathVariable String email) {
    	ArrayList<User> temps = (ArrayList<User>) userRepository.findAll();
    	User temp = new User();
    	int i = 0;
    	boolean found = temps.get(i).getEmail().equals(email);
    	
    	while (!found && i < temps.size()) {
    		found = temps.get(i).getEmail().equals(email);

        	if (temps.get(i).getEmail().equals(email)) {
        		temp = temps.get(i);
        		return temp;
        	} else {
        		i++;
        	}
    	}
		return found?temps.get(i):error("Email " + email + " not found.");
    }

    @PutMapping("/update/email/{name}")
    public @ResponseBody String updateEmail(@PathVariable String name,
    										@RequestBody User newEmail) {
    	if (userRepository.existsById(name)) {
    		User temp = userRepository.findById(name).get();
    		temp.setEmail(newEmail.getEmail());
    		userRepository.save(temp);
    		return "Email updated.";
    	} else return error("User " + name + " not found.");
    }

    @PutMapping("/update/password/{name}")
    public @ResponseBody String updatePassword(@PathVariable String name,
    										@RequestBody User newPsd) {
    	if (userRepository.existsById(name)) {
    		User temp = userRepository.findById(name).get();
    		temp.setPassword(newPsd.getPassword());
    		userRepository.save(temp);
    		return "Password updated.";
    	} else return error("User " + name + " not found.");
    }
    
    @GetMapping("/error")
    public @ResponseBody String error(String msg) {
    	return msg;
    }
}
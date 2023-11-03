package io.aiven.spring.mysql.demosql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(path = "/demo")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody User addNewUser(@RequestParam String name, @RequestParam String email){
        User springUser = new User();
        springUser.setName(name);
        springUser.setEmail(email);
        userRepository.save(springUser);
        return springUser;
    }
    //command: curl http://localhost:8080/demo/add -d name="Jerin" -d email="jerin@zoho.com" (example)

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }
    //command: curl http://localhost:8080/demo/all
    // can also use the URL alone to view in browser

    @PutMapping("/{id}")
    public @ResponseBody User updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
    //command:  -X PUT -H "Content-Type: application/json" -d "{\"name\": \"udaya\", \"email\": \"uperv@email.com\"}" http://localhost:8080/demo/2 (example)

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }
    //command: curl -X DELETE http://localhost:8080/demo/3 (to delete id no '3' for example


}

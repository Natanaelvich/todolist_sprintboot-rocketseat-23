package br.com.natanaelvich.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
 * Modificador
 * public, private, protected
 * 
 * 
 * class, interface, enum
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * String (texto)
     * Integer (int) numeros inteiros
     * Double (double) Números 0.0000
     * Float (float) Números 0.0000 - maior numero de casas decimais
     * char (A C)
     * Date (data)
     * void
     */
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null){
            System.out.println("Usuario ja existe");
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Usuário já existe");
        }
        var userCreated = this.userRepository.save(userModel);
        // System.out.println(userModel.getUsername());
        return ResponseEntity.ok().body(userCreated);
    }
}
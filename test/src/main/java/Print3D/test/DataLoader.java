package Print3D.test;

import Print3D.test.model.Usuario;
import Print3D.test.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;

import java.util.Random;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        String[] roles = {"usuario", "creador"};

        // Generar 10 usuarios de prueba
        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setRut(faker.idNumber().valid());
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setTelefono(random.nextInt(1000000000));
            usuario.setDireccion(faker.address().fullAddress());
            usuario.setUsuario(faker.internet().username());
            usuario.setContrasenia(faker.internet().password());
            
            usuario.setRol(roles[random.nextInt(roles.length)]);

            usuario.setResenias(null);
            usuarioRepository.save(usuario);
        }


    }

}

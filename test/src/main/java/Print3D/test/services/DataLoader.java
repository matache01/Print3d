package Print3D.test.services;

import Print3D.test.model.Usuario;
import Print3D.test.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
// import java.util.Date;
// import java.util.List;
// import java.util.Random;

@Profile("dev_244")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args)throws Exception{
        
        Faker faker = new Faker();
        //Random random = new Random();

        for(int i =0; i < 10; i++){
            Usuario usuario = new Usuario();
            usuario.setRut(faker.idNumber().valid());
            usuario.setNombre(faker.name().firstName());
            usuario.setApellido(faker.name().lastName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setTelefono(faker.number().numberBetween(100000000, 999999999));
            usuario.setDireccion(faker.address().fullAddress());
            usuario.setUsuario(faker.name().username());
            usuario.setContrasenia(faker.internet().password());
            String rol = faker.options().option("creador","cliente");
            usuario.setRol(rol);
            String resenia = faker.lorem().sentence();
            usuario.setResenias(resenia);

            System.out.println("Generando Usuario" + usuario);

            try {
                usuarioRepository.save(usuario);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}

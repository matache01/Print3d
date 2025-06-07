// package Print3D.test.services;

// import Print3D.test.model.Usuario;
// import Print3D.test.repository.UsuarioRepository;
// import net.datafaker.Faker;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;
// import java.util.Date;
// import java.util.List;
// import java.util.Random;



// public class DataLoader implements CommandLineRunner{

//     @Autowired
//     private UsuarioRepository usuarioRepository;

//     @Override
//     public void run(String... args)throws Exception{
        
//         Faker faker = new Faker();
//         Random random = new Random();

//         for(int i =0; i < 10; i++){
//             Usuario usuario = new Usuario();
//             usuario.setRut(faker.name().fristName());
//             usuario.setNombre(faker.code().nombre());
//             usuario.setApellido(faker.code().apellido);
//             usuario.setEmail(faker.code().email);
//             usuario.setTelefono(faker.code().telefono);
//             usuario.setDireccion(faker.code().direccion);
//             usuario.setUsuario(faker.code().nomusuario);
//             usuario.setContrasenia(faker.code().contrasenia);
//             usuario.setRol(faker.code().rol);
//             usuario.setResenias(faker.code().resenias);

//             System.out.println("Generando Usuario" + usuario);

//             try {
//                 usuarioRepository.save(usuario);
//             } catch (Exception e) {
//                 // TODO: handle exception
//             }
//         }
//     }
// }

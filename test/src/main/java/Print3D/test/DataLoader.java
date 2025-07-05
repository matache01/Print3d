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

        for (int i = 0; i < 10; i++) {
            Usuario usuario = new Usuario();
            usuario.setRut(generarRutChileno(random));
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
    private String generarRutChileno(Random random) {

        int num = 5_000_000 + random.nextInt(20_000_000);
        String numStr = String.valueOf(num);
        
        char dv = calcularDigitoVerificador(numStr);
        
        return formatearRut(numStr, dv);
    }

    private char calcularDigitoVerificador(String rut) {
        int suma = 0;
        int multiplicador = 2;
        
        for (int i = rut.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(rut.charAt(i)) * multiplicador;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }
        
        int resto = suma % 11;
        return switch (resto) {
            case 0 -> '0';
            case 1 -> 'K';
            default -> Character.forDigit(11 - resto, 10);
        };
    }

    private String formatearRut(String numStr, char dv) {
        String paddedNum = String.format("%8s", numStr).replace(' ', '0');
        
        return String.format("%s.%s.%s-%c",
            paddedNum.substring(0, 2),
            paddedNum.substring(2, 5),
            paddedNum.substring(5),
            dv
        );
    }
}


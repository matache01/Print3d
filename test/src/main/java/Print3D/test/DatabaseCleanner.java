package Print3D.test;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("dev")
@Component
@Order(1)
public class DatabaseCleanner implements CommandLineRunner {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            
            entityManager.createNativeQuery("DELETE FROM usuarios").executeUpdate();
            
            entityManager.createNativeQuery("ALTER SEQUENCE usuarios_id_seq RESTART WITH 1").executeUpdate();
            
            System.out.println(" Base de datos limpiada (PostgreSQL Neon)");
        } catch (Exception e) {
            System.err.println(" Error al limpiar la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
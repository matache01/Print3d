package Print3D.test.repository;

import Print3D.test.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

Optional <Usuario> findByRut (String rut);

@Query(value = """
    SELECT u.* FROM usuarios u WHERE u.rol = 'creador'""", nativeQuery = true)
List<Usuario> findCreador();

@Query(value = "select u.* from usuarios u where u.rol = 'cliente'", nativeQuery = true)
List<Usuario> findCliente();

}

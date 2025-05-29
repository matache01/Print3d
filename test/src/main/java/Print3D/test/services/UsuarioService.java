package Print3D.test.services;

import Print3D.test.model.Usuario;
import Print3D.test.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List <Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario addUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(int id, Usuario datosNuevos) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuarioExistente = optionalUsuario.get();

            usuarioExistente.setRut(datosNuevos.getRut());
            usuarioExistente.setNombre(datosNuevos.getNombre());
            usuarioExistente.setApellido(datosNuevos.getApellido());
            usuarioExistente.setEmail(datosNuevos.getEmail());
            usuarioExistente.setTelefono(datosNuevos.getTelefono());
            usuarioExistente.setDireccion(datosNuevos.getDireccion());
            usuarioExistente.setUsuario(datosNuevos.getUsuario());
            usuarioExistente.setContrasenia(datosNuevos.getContrasenia());
            usuarioExistente.setRol(datosNuevos.getRol());
            usuarioExistente.setResenias(datosNuevos.getResenias());

            return usuarioRepository.save(usuarioExistente);
        } else {
            return null;
        }
    }
    
    @Transactional
    public void deleteUsuario(int id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findUsuarioById(int id) {
        return usuarioRepository.findById(id);
    }

    public Optional <Usuario> findByRut (String rut){
        return usuarioRepository.findByRut(rut);
    }

    public List<Usuario> obtenerCreadores() {
        return usuarioRepository.findCreador();
    }

    public List<Usuario> obtenerClientes() {
        return usuarioRepository.findCliente();
    }

}

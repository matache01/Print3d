package Print3D.test.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Print3D.test.model.Usuario;
import Print3D.test.services.UsuarioService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para gestión de usuarios")
public class UsusarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    public ResponseEntity<List<Usuario>> getUsuarios(){
        List<Usuario> usuarios = usuarioService.getUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("")
    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    public ResponseEntity<Usuario> CreateUser(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del usuario a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = Usuario.class)))
        @RequestBody Usuario usuario){
        
        if(usuario.getRut() == null || usuario.getRut().isEmpty() ||
            usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
            usuario.getApellido() == null || usuario.getApellido().isEmpty() ||
            usuario.getEmail() == null || usuario.getEmail().isEmpty() ||
            usuario.getUsuario() == null || usuario.getUsuario().isEmpty() ||
            usuario.getContrasenia() == null || usuario.getContrasenia().isEmpty() ||
            usuario.getDireccion() == null || usuario.getDireccion().isEmpty() ||
            usuario.getRol() == null || usuario.getRol().isEmpty()){
                return ResponseEntity.badRequest().build();
            }
        if (usuario.getTelefono() <=0) {
            return ResponseEntity.badRequest().build();
        }

        if (usuario.getResenias() == null) {
            usuario.setResenias("");
        }

        Usuario usuarioCreado = usuarioService.addUsuario(usuario);
        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> getById(
        @Parameter(description = "ID del usuario", required = true, example = "1")
        @PathVariable("id") int id) {
        return usuarioService.findUsuarioById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/rut/{rut}")
    @Operation(summary = "Obtener usuario por RUT", description = "Retorna un usuario específico según su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Usuario> getByRut(
        @Parameter(description = "RUT del usuario", required = true, example = "12.345.678-9")
        @PathVariable("rut") String rut) {
        return usuarioService.findByRut(rut)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("actualizar/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<?> updateUusario(
        @Parameter(description = "ID del usuario a actualizar", required = true)
        @PathVariable("id") int id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos actualizados del usuario",
            required = true,
            content = @Content(schema = @Schema(implementation = Usuario.class)))
        @RequestBody Usuario usuarioAct) {
        
        Usuario actualizado = usuarioService.updateUsuario(id, usuarioAct);
        if (actualizado != null){
            return ResponseEntity.ok(actualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> deleteUsuarioByid(
        @Parameter(description = "ID del usuario a eliminar", required = true)
        @PathVariable("id") int id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/creadores")
    @Operation(summary = "Listar creadores", description = "Obtiene la lista de todos los usuarios con rol 'creador'")
    @ApiResponse(responseCode = "200", description = "Lista de creadores encontrada",
                content = @Content(schema = @Schema(implementation = Usuario.class)))
    public ResponseEntity<List<Usuario>> listarCreadores() {
        List<Usuario> creadores = usuarioService.obtenerCreadores();
        return ResponseEntity.ok(creadores);
    }

    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes", description = "Obtiene la lista de todos los usuarios con rol 'cliente'")
    @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada",
                content = @Content(schema = @Schema(implementation = Usuario.class)))
    public ResponseEntity<List<Usuario>> listarClientes() {
        List<Usuario> clientes = usuarioService.obtenerClientes();
        return ResponseEntity.ok(clientes);
    }
}
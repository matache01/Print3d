package Print3D.test.controller;

import Print3D.test.assemblers.UsuarioModelAssembler;
import Print3D.test.model.Usuario;
import Print3D.test.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios")
@Tag(name = "Usuarios V2", description = "Operaciones con usuarios usando HATEOAS")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    @GetMapping("")
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de usuarios con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios con enlaces",
            content = @Content(schema = @Schema(implementation = Usuario.class)))
    public CollectionModel<EntityModel<Usuario>> getAllUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.getUsuarios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withSelfRel());
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> getUsuarioById(
            @Parameter(description = "ID del usuario", required = true, example = "1")
            @PathVariable int id) {

        return usuarioService.findUsuarioById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Obtener usuario por RUT", description = "Retorna un usuario por su RUT con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> getByRut(
            @Parameter(description = "RUT del usuario", required = true, example = "12.345.678-9")
            @PathVariable("rut") String rut) {

        return usuarioService.findByRut(rut)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario y retorna con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos del usuario")
    })
    public ResponseEntity<EntityModel<Usuario>> createUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del usuario a crear",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Usuario.class)))
            @RequestBody Usuario usuario) {

        if (usuario.getRut() == null || usuario.getNombre() == null || usuario.getRol() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (usuario.getResenias() == null) {
            usuario.setResenias("");
        }

        Usuario nuevo = usuarioService.addUsuario(usuario);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(nuevo));
    }

    @PutMapping("/actualizar/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario y devuelve con enlaces HATEOAS")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar", required = true, example = "1")
            @PathVariable("id") int id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del usuario",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Usuario.class)))
            @RequestBody Usuario usuarioAct) {

        Usuario actualizado = usuarioService.updateUsuario(id, usuarioAct);
        if (actualizado != null){
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> deleteUsuarioById(
            @Parameter(description = "ID del usuario a eliminar", required = true, example = "1")
            @PathVariable("id") int id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/creadores")
    @Operation(summary = "Listar creadores", description = "Obtiene usuarios con rol 'creador' con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista de creadores encontrada",
            content = @Content(schema = @Schema(implementation = Usuario.class)))
    public CollectionModel<EntityModel<Usuario>> listarCreadores() {
        List<EntityModel<Usuario>> creadores = usuarioService.obtenerCreadores().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(creadores,
                linkTo(methodOn(UsuarioControllerV2.class).listarCreadores()).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("todos-usuarios"));
    }

    @GetMapping("/clientes")
    @Operation(summary = "Listar clientes", description = "Obtiene usuarios con rol 'cliente' con enlaces HATEOAS")
    @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada",
            content = @Content(schema = @Schema(implementation = Usuario.class)))
    public CollectionModel<EntityModel<Usuario>> listarClientes() {
        List<EntityModel<Usuario>> clientes = usuarioService.obtenerClientes().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(clientes,
                linkTo(methodOn(UsuarioControllerV2.class).listarClientes()).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getAllUsuarios()).withRel("todos-usuarios"));
    }
}

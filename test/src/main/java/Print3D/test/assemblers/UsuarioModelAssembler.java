package Print3D.test.assemblers;

import Print3D.test.controller.UsusarioController;
import Print3D.test.model.Usuario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {
    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
            linkTo(methodOn(UsusarioController.class).getById(usuario.getId())).withSelfRel(),
            linkTo(methodOn(UsusarioController.class).getUsuarios()).withRel("todos-usuarios"),
            linkTo(methodOn(UsusarioController.class).listarClientes()).withRel("clientes"),
            linkTo(methodOn(UsusarioController.class).listarCreadores()).withRel("creadores")
        );
    }

}

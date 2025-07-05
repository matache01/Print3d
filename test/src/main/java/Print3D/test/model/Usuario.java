package Print3D.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un usuario en el sistema Print3D")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autogenerado del usuario", example = "1")
    private int id;

    @Column(name = "rut", length = 255, unique = true, nullable = false)
    @Schema(
        description = "RUT del usuario en formato XX.XXX.XXX-X",
        example = "12.345.678-9",
        pattern = "^\\d{1,2}\\.\\d{3}\\.\\d{3}[-][0-9kK]{1}$"
    )
    private String rut;

    @Column(name = "nombre", length = 255, unique = false, nullable = false)
    @Schema(description = "Nombre(s) del usuario", example = "Juan")
    private String nombre;

    @Column(name = "apellido", length = 255, unique = false, nullable = false)
    @Schema(description = "Apellido(s) del usuario", example = "Pérez González")
    private String apellido;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    @Schema(
        description = "Email válido del usuario",
        example = "usuario@dominio.cl",
        format = "email"
    )
    private String email;

    @Column(name = "telefono", length = 255, unique = true, nullable = false)
    @Schema(
        description = "Número de teléfono (9 dígitos)",
        example = "912345678",
        minimum = "900000000",
        maximum = "999999999"
    )
    private int telefono;

    @Column(name = "direccion", length = 255, unique = false, nullable = false)
    @Schema(description = "Dirección completa del usuario", example = "Av. Principal 1234, Depto 45, Santiago")
    private String direccion;

    @Column(name = "usuario", length = 255, unique = true, nullable = false)
    @Schema(
        description = "Nombre de usuario para login (entre 5-20 caracteres)",
        example = "juan.perez",
        minLength = 5,
        maxLength = 20
    )
    private String usuario;

    @Column(name = "contrasenia", length = 255, unique = false, nullable = false)
    @Schema(
        description = "Contraseña del usuario (mínimo 8 caracteres)",
        example = "Password123!",
        minLength = 8
    )
    private String contrasenia;

    @Column(name = "rol", length = 255, unique = false, nullable = false)
    @Schema(
        description = "Rol del usuario en el sistema",
        example = "cliente",
        allowableValues = {"cliente", "creador"}
    )
    private String rol;

    @Column(name = "resenias", length = 255, unique = false, nullable = true)
    @Schema(
        description = "Reseñas u observaciones sobre el usuario",
        example = "Cliente frecuente con buen historial de pagos",
        nullable = true
    )
    private String resenias;
}
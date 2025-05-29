package Print3D.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rut", length = 255, unique = true, nullable = false)
    private String rut;

    @Column(name = "nombre", length = 255, unique = false, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 255, unique = false, nullable = false)
    private String apellido;

    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;

    @Column(name = "telefono", length =  255, unique =  true, nullable = false)
    private int telefono;

    @Column(name = "direccion", length = 255, unique = false, nullable = false)
    private String direccion;

    @Column(name = "usuario", length = 255, unique = true, nullable = false)
    private String usuario;

    @Column(name = "contrasenia", length = 255, unique = false, nullable = false)
    private String contrasenia;



    @Column(name = "rol", length = 255, unique = false, nullable = false)
    private String rol;

    @Column(name ="resenias", length = 255, unique = false, nullable = true)
    private String resenias;

}

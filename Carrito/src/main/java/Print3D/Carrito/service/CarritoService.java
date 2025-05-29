package Print3D.Carrito.service;

import Print3D.Carrito.dto.ProductoDTO;
import Print3D.Carrito.dto.UsuarioDTO;
import Print3D.Carrito.model.Carrito;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CarritoService {
    private final Map<String, Carrito> carritos = new HashMap<>();
    private final RestTemplate restTemplate = new RestTemplate();

    private final String usuarioServiceUrl = "http://localhost:8083/usuarios/";
    private final String inventarioServiceUrl = "http://localhost:8082/productos/";

    public Carrito obtenerCarrito(String userId) {
        return carritos.computeIfAbsent(userId, Carrito::new);
    }

    public boolean agregarProducto(String userId, String productId, int cantidad) {
        // Verificar usuario
        UsuarioDTO usuario = restTemplate.getForObject(usuarioServiceUrl + userId, UsuarioDTO.class);
        if (usuario == null) return false;

        // Verificar producto
        ProductoDTO producto = restTemplate.getForObject(inventarioServiceUrl + productId, ProductoDTO.class);
        if (producto == null || producto.getStock() < cantidad) return false;

        obtenerCarrito(userId).agregarProducto(productId, cantidad);
        return true;
    }

    public boolean eliminarProducto(String userId, String productId) {
        Carrito carrito = obtenerCarrito(userId);
        carrito.eliminarProducto(productId);
        return true;
    }

    public void vaciarCarrito(String userId) {
        obtenerCarrito(userId).vaciar();
    }

    public double calcularTotal(String userId) {
        Carrito carrito = obtenerCarrito(userId);
        double total = 0;

        for (Map.Entry<String, Integer> entry : carrito.getProductos().entrySet()) {
            ProductoDTO producto = restTemplate.getForObject(inventarioServiceUrl + entry.getKey(), ProductoDTO.class);
            if (producto != null) {
                total += producto.getPrecio() * entry.getValue();
            }
        }

        return total;
    }
}

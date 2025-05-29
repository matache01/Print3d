package Print3D.Carrito.model;

import java.util.*;

public class Carrito {
    private String userId;
    private Map<String, Integer> productos = new HashMap<>();

    public Carrito(String userId) {
        this.userId = userId;
    }

    public void agregarProducto(String productId, int cantidad) {
        productos.put(productId, productos.getOrDefault(productId, 0) + cantidad);
    }

    public void eliminarProducto(String productId) {
        productos.remove(productId);
    }

    public void vaciar() {
        productos.clear();
    }

    // Getters y setters
    public String getUserId() { return userId; }
    public Map<String, Integer> getProductos() { return productos; }
}

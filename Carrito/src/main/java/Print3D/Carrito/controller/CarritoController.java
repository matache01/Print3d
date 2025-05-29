package Print3D.Carrito.controller;

import Print3D.Carrito.model.Carrito;
import Print3D.Carrito.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping("/{userId}")
    public Carrito obtenerCarrito(@PathVariable String userId) {
        return carritoService.obtenerCarrito(userId);
    }

    @PostMapping("/{userId}/agregar")
    public String agregarProducto(@PathVariable String userId, @RequestParam String productId, @RequestParam int cantidad) {
        boolean exito = carritoService.agregarProducto(userId, productId, cantidad);
        return exito ? "Producto agregado" : "Error al agregar producto";
    }

    @DeleteMapping("/{userId}/eliminar")
    public String eliminarProducto(@PathVariable String userId, @RequestParam String productId) {
        carritoService.eliminarProducto(userId, productId);
        return "Producto eliminado";
    }

    @PostMapping("/{userId}/vaciar")
    public String vaciarCarrito(@PathVariable String userId) {
        carritoService.vaciarCarrito(userId);
        return "Carrito vaciado";
    }

    @GetMapping("/{userId}/total")
    public double calcularTotal(@PathVariable String userId) {
        return carritoService.calcularTotal(userId);
    }
}

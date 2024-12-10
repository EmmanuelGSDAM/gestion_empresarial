package testC;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controladores.ControllerUsuario2;
import modelos.Usuario;

class controllerUsuarioTest {

    private Usuario testUsuario;

    @BeforeEach
    public void setUp() {
        // Crear un usuario de prueba
        testUsuario = new Usuario(20, "NombrePrueba", "Apellido1Prueba", "Apellido2Prueba", "12345678A");
        assertTrue(ControllerUsuario2.insertar(testUsuario), "El usuario de prueba debería ser insertado correctamente");
    }

    @AfterEach
    public void tearDown() {
        // Eliminar el usuario de prueba después de cada prueba
        assertTrue(ControllerUsuario2.eliminar("12345678A"), "El usuario de prueba debería ser eliminado correctamente");
    }

    @Test
    public void testInsertarUsuario() {
        Usuario nuevoUsuario = new Usuario(20, "NuevoNombre", "NuevoApellido1", "NuevoApellido2", "87654321B");
        assertTrue(ControllerUsuario2.insertar(nuevoUsuario), "El nuevo usuario debería ser insertado correctamente");
        assertNotNull(ControllerUsuario2.buscar("87654321B"), "El usuario recién insertado debería existir en la base de datos");
        assertTrue(ControllerUsuario2.eliminar("87654321B"), "El nuevo usuario debería ser eliminado correctamente");
    }

    @Test
    public void testListarUsuarios() {
        ControllerUsuario2.listar();
        
        assertNotNull(ControllerUsuario2.buscar("12345678A"), "El usuario de prueba debería aparecer al listar");
    }

    @Test
    public void testModificarUsuario() {
        testUsuario.setNombre("NombreActualizado");
        assertTrue(ControllerUsuario2.modificar(testUsuario), "El usuario debería ser modificado correctamente");
        Usuario actualizado = ControllerUsuario2.buscar("12345678A");
        assertNotNull(actualizado, "El usuario modificado debería existir en la base de datos");
        assertEquals("NombreActualizado", actualizado.getNombre(), "El nombre debería ser actualizado correctamente");
    }

    @Test
    public void testBuscarUsuario() {
        Usuario encontrado = ControllerUsuario2.buscar("12345678A");
        assertNotNull(encontrado, "El usuario debería ser encontrado por su DNI");
        assertEquals("NombrePrueba", encontrado.getNombre(), "El nombre del usuario encontrado debería coincidir");
    }

    @Test
    public void testEliminarUsuario() {
        assertTrue(ControllerUsuario2.eliminar("12345678A"), "El usuario debería ser eliminado correctamente");
        assertNull(ControllerUsuario2.buscar("12345678A"), "El usuario eliminado no debería existir en la base de datos");
    }

    @Test
    public void testRestaurarUsuario() {
        assertTrue(ControllerUsuario2.eliminar("12345678A"), "El usuario debería ser eliminado correctamente");
        assertTrue(ControllerUsuario2.restaurar("12345678A"), "El usuario debería ser restaurado correctamente");
        assertNotNull(ControllerUsuario2.buscar("12345678A"), "El usuario restaurado debería aparecer en la base de datos");
    }

    @Test
    public void testRestaurarTodosUsuarios() {
        assertTrue(ControllerUsuario2.eliminar("12345678A"), "El usuario debería ser eliminado correctamente");
        assertTrue(ControllerUsuario2.restaurarTodos(), "Todos los usuarios eliminados deberían ser restaurados correctamente");
        assertNotNull(ControllerUsuario2.buscar("12345678A"), "El usuario restaurado debería aparecer en la base de datos");
    }

}

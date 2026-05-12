package GameState;

/**
 *Interface responsable de manejar los estados del juego.
 */
public interface GameState {
    /**
     * Ejecutar la acción de cada estado concreto.
     */
    void handle();
}
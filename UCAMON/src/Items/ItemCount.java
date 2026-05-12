package Items;

/**
 * La clase ItemCount representa un contador para un elemento específico.
 */
public class ItemCount {

    /**
     * Nombre del item.
     */
    String name;

    /**
     * Cantidad del item.
     */
    int count;

    /**
     * Constructor para inicializar un objeto ItemCount con un nombre y una cantidad inicial de 1.
     *
     * @param name El nombre del elemento.
     */
    ItemCount(String name) {
        this.name = name;
        this.count = 1;
    }
}

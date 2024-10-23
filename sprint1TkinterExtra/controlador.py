from modelo import ModeloJuego
from vista import VistaJuego

# Controlador del juego que gestiona la interacción entre modelo y vista
class ControladorJuego:
    def __init__(self, modelo, vista):
        """
        Inicializa el controlador con el modelo y la vista.

        Parámetros:
        - modelo: Instancia de ModeloJuego.
        - vista: Instancia de VistaJuego.
        """
        self.modelo = modelo  # Almacena el modelo del juego
        self.vista = vista    # Almacena la vista del juego

    def jugar(self, eleccion_jugador, es_dos_jugadores=False, eleccion_jugador_dos=None):
        """
        Realiza una jugada y obtiene el resultado.

        Parámetros:
        - eleccion_jugador: Elección del jugador 1.
        - es_dos_jugadores: Si se juega entre dos jugadores.
        - eleccion_jugador_dos: Elección del jugador 2 (si aplica).

        Retorna:
        - Resultado de la jugada.
        """
        return self.modelo.jugar(eleccion_jugador, es_dos_jugadores, eleccion_jugador_dos)

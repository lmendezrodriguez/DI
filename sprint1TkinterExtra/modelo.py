import random

# Modelo de un juego de Piedra, Papel o Tijera
class ModeloJuego:
    def __init__(self):
        """
        Inicializa el juego con opciones y contadores de victorias.
        """
        self.opciones = ['Piedra', 'Papel', 'Tijera']
        self.victorias_jugador1 = 0
        self.victorias_jugador2 = 0
        self.max_victorias = 3  # Mejor de 3

    def jugar(self, eleccion_jugador1, es_dos_jugadores=False, eleccion_jugador2=None):
        """
        Realiza una jugada. Determina el ganador entre dos jugadores o contra la máquina.

        Parámetros:
        - eleccion_jugador1: Elección del jugador 1.
        - es_dos_jugadores: Si es un juego de dos jugadores.
        - eleccion_jugador2: Elección del jugador 2 (si aplica).

        Retorna:
        - Resultado del juego.
        """
        if es_dos_jugadores:
            return self.determinar_ganador(eleccion_jugador1, eleccion_jugador2)
        else:
            eleccion_maquina = random.choice(self.opciones)
            return self.determinar_ganador(eleccion_jugador1, eleccion_maquina), eleccion_maquina

    def determinar_ganador(self, eleccion1, eleccion2):
        """
        Compara las elecciones y determina el ganador.

        Parámetros:
        - eleccion1: Elección del jugador 1.
        - eleccion2: Elección del jugador 2.

        Retorna:
        - Resultado del enfrentamiento.
        """
        if eleccion1 == eleccion2:
            return "Empate"
        elif (eleccion1 == 'Piedra' and eleccion2 == 'Tijera') or \
             (eleccion1 == 'Tijera' and eleccion2 == 'Papel') or \
             (eleccion1 == 'Papel' and eleccion2 == 'Piedra'):
            self.victorias_jugador1 += 1
            return "Jugador 1 gana"
        else:
            self.victorias_jugador2 += 1
            return "Jugador 2 gana"

    def obtener_resultado_final(self):
        """
        Devuelve el resultado final del juego.

        Retorna:
        - Mensaje sobre el ganador o empate.
        """
        if self.victorias_jugador1 > self.victorias_jugador2:
            return "Jugador 1 gana el juego"
        elif self.victorias_jugador2 > self.victorias_jugador1:
            return "Jugador 2 gana el juego"
        else:
            return "El juego termina en empate"

    def reiniciar_victorias(self):
        """
        Reinicia los contadores de victorias.
        """
        self.victorias_jugador1 = 0
        self.victorias_jugador2 = 0

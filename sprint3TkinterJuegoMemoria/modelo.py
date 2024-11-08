import threading
import time
import random
import datetime


# import descargar_imagen de recursos.py

class GameModelo:
    def __init__(self, difficulty, player_name, cell_size=100):
        """
        Inicializa el modelo del juego con la dificultad y nombre de la jugadora.

        :param difficulty: Nivel de dificultad: fácil, medio o difícil.
        :param player_name: Nombre de la jugadora
        :param cell_size: Tamaño de celdas del juego
        """
        self.difficulty = difficulty
        self.player_name = player_name
        self.cell_size = cell_size
        self.start_time = datetime.datetime.now()
        self.moves = 0

    def __str__(self):
        return (f"Modelo(dificultad='{self.difficulty}', nombre='{self.player_name}',"
                f"tiempo={self.start_time})")

import threading
import time
import random
import datetime
from tkinter import messagebox

import recursos


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
        self.board = []
        self.hidden = None
        self.images = {}
        self.images_loaded = False
        self._generate_board_()
        self._load_images()

    def __str__(self):
        return (
            f"Modelo(dificultad='{self.difficulty}', nombre='{self.player_name}',"
            f"tiempo={self.start_time})")

    def _generate_board_(self):
        if self.difficulty == 1:
            board_size = 4
        elif self.difficulty == 2:
            board_size = 6
        elif self.difficulty == 3:
            board_size = 8
        else:
            messagebox.showerror("Error",
                                 "La dificultad no ha sido cargada")
        # Genera identificadores de pares de cartas
        image_ids = list(
            range(1, board_size * 2 + 1)) * 2  # Cada par tiene el mismo ID

        # Mezcla aleatoriamente los identificadores
        random.shuffle(image_ids)

        # Divide la lista de identificadores en una estructura 2D para el tablero
        # Recorremos la lista de identificadores de cartas con un índice que va desde 0 hasta el tamaño del tablero, en pasos de `board_size`
        for i in range(0, board_size ** 2, board_size):
            # Creamos una sublista desde el índice actual `i` hasta `i + board_size`
            row = image_ids[i:i + board_size]
            # Agregamos esta sublista como una "fila" en el tablero
            self.board.append(row)

    def _load_images(self):
        def load_images_thread():
            # URL base de donde se descargarán las imágenes
            base_url = "https://raw.githubusercontent.com/lmendezrodriguez/DI/refs/heads/main/sprint3TkinterJuegoMemoria/res/img/"  # URL base de las imágenes
            try:
                # Cargar imagen oculta

                hidden_image_url = f"{base_url}hidden.png"
                self.hidden = recursos.descargar_imagen(hidden_image_url,
                                                        self.cell_size)

                # Cargar imágenes para cada identificador de carta en el tablero
                unique_ids = set(id for row in self.board for id in row)
                print(unique_ids)
                for image_id in unique_ids:
                    image_url = f"{base_url}{image_id}.png"
                    self.images[image_id] = recursos.descargar_imagen(
                        image_url, self.cell_size)
                print(self.images)

            except IOError as e:
                messagebox.showerror("Error",
                                     "La imagen no ha sido cargada")
            self.images_loaded = True

        threading.Thread(target=load_images_thread, daemon=True).start()

    def images_are_load(self):
        return self.images_loaded

    def start_time(self):
        pass

    def get_time(self):
        pass

    def check_match(self, pos1, pos2):
        pass

    def is_game_complete(self):
        pass

    def save_score(self):
        pass

    def load_scores(self):
        pass

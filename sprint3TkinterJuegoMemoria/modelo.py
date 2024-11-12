import threading
import random
import time
from tkinter import messagebox

import recursos


# import descargar_imagen de recursos.py

class GameModelo:
    def __init__(self, difficulty, player_name, cell_size=100):
        """
        Inicializa el modelo del juego con la dificultad, nombre del jugador y tamaño de las celdas.

        :param difficulty: Nivel de dificultad del juego (1: fácil, 2: medio, 3: difícil).
        :param player_name: Nombre del jugador.
        :param cell_size: Tamaño de las celdas del juego (por defecto 100).
        """
        self.difficulty = difficulty
        self.player_name = player_name
        self.cell_size = cell_size
        self.start_time = None # Guarda el tiempo de inicio
        self.moves = 0  # Contador de movimientos
        self.board = []  # Tablero vacío
        self.hidden = None  # Imagen oculta inicial
        self.images = {}  # Diccionario para almacenar imágenes de cartas
        self.images_loaded = False  # Bandera para saber si las imágenes están cargadas
        self._generate_board_()  # Genera el tablero según la dificultad
        self._load_images()  # Carga las imágenes en un hilo separado

    def __str__(self):
        """
        Representación en cadena del objeto GameModelo.
        """
        return (
            f"Modelo(dificultad='{self.difficulty}', nombre='{self.player_name}',"
            f"tiempo={self.start_time})")

    def _generate_board_(self):
        """
        Genera el tablero de juego basado en la dificultad seleccionada.
        Crea una estructura 2D con identificadores de pares de cartas.
        """
        # Determina el tamaño del tablero según la dificultad
        if self.difficulty == 1:
            board_size = 4
        elif self.difficulty == 2:
            board_size = 6
        elif self.difficulty == 3:
            board_size = 8
        else:
            messagebox.showerror("Error", "La dificultad no ha sido cargada")
            return

        # Genera una lista de identificadores de cartas para los pares
        image_ids = list(range(1, board_size * 2 + 1)) * 2  # Cada par tiene el mismo ID
        random.shuffle(image_ids)  # Mezcla aleatoriamente los identificadores

        # Divide los identificadores en una estructura 2D para el tablero
        for i in range(0, board_size ** 2, board_size):
            row = image_ids[i:i + board_size]  # Sublista que representa una fila
            self.board.append(row)  # Añade la fila al tablero

    def _load_images(self):
        """
        Carga las imágenes de las cartas y la imagen oculta en un hilo separado.
        La carga se realiza en segundo plano para no bloquear la interfaz.
        """
        def load_images_thread():
            base_url = "https://raw.githubusercontent.com/lmendezrodriguez/DI/refs/heads/main/sprint3TkinterJuegoMemoria/res/img/"  # URL base de las imágenes
            try:
                # Carga la imagen oculta
                hidden_image_url = f"{base_url}hidden.png"
                self.hidden = recursos.descargar_imagen(hidden_image_url, self.cell_size)

                # Carga imágenes para cada identificador de carta en el tablero
                unique_ids = set(id for row in self.board for id in row)  # Identificadores únicos de cartas
                for image_id in unique_ids:
                    image_url = f"{base_url}{image_id}.png"
                    self.images[image_id] = recursos.descargar_imagen(image_url, self.cell_size)

            except IOError as e:
                # Muestra un error si las imágenes no se cargan correctamente
                messagebox.showerror("Error", "La imagen no ha sido cargada")
            self.images_loaded = True  # Marca que las imágenes se han cargado

        # Inicia un hilo para cargar las imágenes sin bloquear la interfaz
        threading.Thread(target=load_images_thread, daemon=True).start()

    def images_are_load(self):
        """
        Verifica si las imágenes se han cargado correctamente.

        :return: True si las imágenes están cargadas, False en caso contrario.
        """
        return self.images_loaded

    def start_timer(self):
        if self.start_time is None:
            self.start_time = time.time()  # Guardamos el tiempo de inicio al comenzar el juego

    def get_time(self):
        """
        Devuelve el tiempo transcurrido desde el inicio del juego.

        :return: El tiempo transcurrido en segundos.
        """
        if self.start_time is None:
            return "00:00"  # Si el temporizador no ha comenzado aún, devolvemos 00:00

        elapsed_time = time.time() - self.start_time  # Calcula el tiempo transcurrido en segundos
        minutes = int(elapsed_time // 60)  # Extrae los minutos
        seconds = int(elapsed_time % 60)  # Extrae los segundos
        return f"{minutes:02}:{seconds:02}"  # Formatea la cadena como MM:SS (con ceros a la izquierda)

    def check_match(self, pos1, pos2):
        """
        Verifica si dos cartas seleccionadas coinciden (actualmente no implementado).

        :param pos1: La posición de la primera carta seleccionada.
        :param pos2: La posición de la segunda carta seleccionada.

        :return: True si las cartas coinciden, False en caso contrario.
        """
        if self.board[pos1[0]][pos1[1]] == self.board[pos2[0]][pos2[1]]:
            return True
        return False

    def is_game_complete(self):
        """
        Verifica si el juego ha terminado (actualmente no implementado).

        :return: True si el juego está completo, False en caso contrario.
        """
        pass

    def save_score(self):
        """
        Guarda la puntuación del jugador (actualmente no implementado).
        """
        pass

    def load_scores(self):
        """
        Carga las puntuaciones previas (actualmente no implementado).
        """
        pass

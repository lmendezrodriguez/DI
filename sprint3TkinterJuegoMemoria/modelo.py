import json
import threading
import random
import time
from datetime import datetime
from tkinter import messagebox
import recursos


class GameModelo:
    def __init__(self, difficulty, player_name, cell_size=70):
        """
        Inicializa el modelo del juego con la dificultad, nombre del jugador y tamaño de las celdas.

        :param difficulty: Nivel de dificultad del juego (1: fácil, 2: medio, 3: difícil).
        :param player_name: Nombre del jugador.
        :param cell_size: Tamaño de las celdas del juego (por defecto 100).
        """
        self.difficulty = difficulty # Dificultad del juego
        self.player_name = player_name # Nombre del jugador
        self.cell_size = cell_size # Tamaño de las cartas
        self.start_time = None  # Guarda el tiempo de inicio del juego
        self.moves = 0  # Contador de movimientos
        self.hits = 0  # Contador de aciertos
        self.board = []  # Tablero inicial vacío
        self.hidden = None  # Imagen oculta inicial
        self.images = {}  # Diccionario para almacenar imágenes de cartas
        self.images_loaded = False  # Bandera para verificar si las imágenes están cargadas
        self.card_pairs = None
        self._generate_board()  # Genera el tablero según la dificultad
        self._load_images()  # Carga las imágenes en un hilo separado

    def __str__(self):
        """
        Representación en cadena del objeto GameModelo.
        """
        return (
            f"Modelo(dificultad='{self.difficulty}', nombre='{self.player_name}',"
            f" tiempo_inicio={self.start_time})"
        )

    def _generate_board(self):
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
        self.card_pairs = board_size ** 2 // 2
        image_ids = list(range(1, self.card_pairs + 1)) * 2  # Cada par tiene el mismo ID
        random.shuffle(image_ids)  # Mezcla aleatoriamente los identificadores

        # Divide los identificadores en una estructura 2D para el tablero
        for i in range(0, board_size ** 2, board_size):
            row = image_ids[
                  i:i + board_size]  # Sublista que representa una fila
            self.board.append(row)  # Añade la fila al tablero

    def _load_images(self):
        """
        Carga las imágenes de las cartas y la imagen oculta en un hilo separado.
        La carga se realiza en segundo plano para no bloquear la interfaz.
        """

        def load_images_thread():
            base_url = "https://raw.githubusercontent.com/lmendezrodriguez/DI/refs/heads/main/sprint3TkinterJuegoMemoria/res/img/"
            try:
                # Carga la imagen oculta
                hidden_image_url = f"{base_url}hidden.png"
                self.hidden = recursos.descargar_imagen(hidden_image_url,
                                                        self.cell_size)

                # Carga imágenes para cada identificador de carta en el tablero
                for image_id in range(1, self.card_pairs + 1):
                    image_url = f"{base_url}{image_id}.png"
                    self.images[image_id] = recursos.descargar_imagen(
                        image_url, self.cell_size)

            except IOError as e:
                # Muestra un error si las imágenes no se cargan correctamente
                messagebox.showerror("Error", "La imagen no ha sido cargada")
                return e
            self.images_loaded = True  # Marca que las imágenes se han cargado

        # Inicia un hilo para cargar las imágenes sin bloquear la interfaz
        threading.Thread(target=load_images_thread, daemon=True).start()

    def start_timer(self):
        """
        Inicia el temporizador al comenzar el juego, registrando el tiempo actual.
        """
        if self.start_time is None:
            self.start_time = time.time()

    def get_time(self):
        """
        Devuelve el tiempo transcurrido desde el inicio del juego.

        :return: El tiempo transcurrido en formato "MM:SS".
        """
        if self.start_time is None:
            return "00:00"  # Si el temporizador no ha comenzado aún, devolvemos 00:00

        elapsed_time = time.time() - self.start_time  # Calcula el tiempo transcurrido en segundos
        minutes = int(elapsed_time // 60)  # Extrae los minutos
        seconds = int(elapsed_time % 60)  # Extrae los segundos
        return f"{minutes:02}:{seconds:02}"  # Formatea la cadena como MM:SS (con ceros a la izquierda)

    def check_match(self, pos1, pos2):
        """
        Verifica si dos cartas seleccionadas coinciden.

        :param pos1: La posición de la primera carta seleccionada (tupla de coordenadas).
        :param pos2: La posición de la segunda carta seleccionada (tupla de coordenadas).

        :return: True si las cartas coinciden, False en caso contrario.
        """
        if self.board[pos1[0]][pos1[1]] == self.board[pos2[0]][pos2[1]]:
            self.hits += 1
            return True
        return False

    def is_game_complete(self):
        """
        Verifica si el juego ha terminado.

        :return: True si el juego está completo, False en caso contrario.
        """
        # Verifica si los aciertos son suficientes para terminar el juego
        if self.hits == self.card_pairs:
            self.save_score()  # Guarda la puntuación al completar el juego
            return True
        return False

    def save_score(self):
        """
        Guarda la puntuación del jugador en el archivo de puntuaciones.
        """
        # Cargar las puntuaciones actuales
        scores = self.load_scores()

        # Crear el nuevo registro de puntuación
        new_score = {
            'nombre': self.player_name,
            'movimientos': self.moves,
            'fecha': datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        }

        # Añadir el nuevo puntaje a la dificultad correspondiente
        if self.difficulty == 1:
            scores["fácil"].append(new_score)
        elif self.difficulty == 2:
            scores["medio"].append(new_score)
        elif self.difficulty == 3:
            scores["difícil"].append(new_score)

        # Ordenar las puntuaciones por menor número de movimientos
        for level in ["fácil", "medio", "difícil"]:
            scores[level] = sorted(scores[level],
                                   key=lambda x: x['movimientos'])[
                            :3]  # Mantiene solo los tres mejores

        # Guardar las puntuaciones actualizadas en el archivo
        with open("res/ranking.txt", 'w', encoding='utf-8') as file:
            json.dump(scores, file, indent=4)

    @staticmethod
    def load_scores():
        """
        Carga las puntuaciones previas.

        :return: Un diccionario con las puntuaciones clasificadas por dificultad.
        """
        try:
            with open("res/ranking.txt", 'r', encoding='utf-8') as file:
                scores = json.load(file)
        except FileNotFoundError:
            # Crear una estructura vacía si el archivo no existe
            scores = {'fácil': [], 'medio': [], 'difícil': []}
        return scores
import tkinter as tk
from tkinter import simpledialog, messagebox


class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback,
                 quit_callback):
        """
        Inicializa el menú principal de la aplicación con los botones necesarios.

        :param root: Referencia a la ventana principal de Tkinter.
        :param start_game_callback: Función a ejecutar cuando se haga clic en el botón "Jugar".
        :param show_stats_callback: Función a ejecutar cuando se haga clic en el botón "Estadísticas".
        :param quit_callback: Función a ejecutar cuando se haga clic en el botón "Salir".
        """
        self.root = root
        self.root.title(
            "Memoriza las cartas!")  # Título de la ventana principal
        self.root.geometry("400x400")  # Tamaño de la ventana

        # Crear el botón "Jugar" y enlazarlo con el callback para iniciar el juego
        play_button = tk.Button(root, text="Jugar",
                                command=start_game_callback)
        play_button.pack(pady=10)

        # Crear el botón "Estadísticas" y enlazarlo con el callback para mostrar estadísticas
        stats_button = tk.Button(root, text="Estadísticas",
                                 command=show_stats_callback)
        stats_button.pack(pady=10)

        # Crear el botón "Salir" y enlazarlo con el callback para cerrar la aplicación
        quit_button = tk.Button(root, text="Salir", command=quit_callback)
        quit_button.pack(pady=10)

    def ask_player_name(self):
        """
        Lanza un cuadro de diálogo para que el jugador ingrese su nombre.

        :return: El nombre ingresado o None si el jugador cancela el diálogo.
        """
        while True:
            # Mostrar un cuadro de diálogo para ingresar el nombre
            player_name = simpledialog.askstring("Nombre de jugador",
                                                 "Teclea tu nombre:",
                                                 initialvalue="Guybrush")

            if player_name is None:
                # Si el jugador cancela, se retorna None
                return None
            elif len(player_name.strip()) < 1:
                # Si el nombre está vacío, se muestra un error
                messagebox.showerror("Error",
                                     "El nombre no puede estar vacío. Por favor, ingresa un nombre.")
            else:
                # Si el nombre es válido, se retorna el nombre ingresado
                return player_name.strip()


class GameView:
    def __init__(self, root, on_card_click_callback,
                 update_move_count_callback, update_time_callback):
        """
        Inicializa la vista del juego.

        :param root: Referencia a la ventana principal de Tkinter.
        :param on_card_click_callback: Función que se llama cuando se hace clic en una carta.
        :param update_move_count_callback: Función que se llama para actualizar el contador de movimientos.
        :param update_time_callback: Función que se llama para actualizar el temporizador.
        """
        self.hidden_image = None
        self.timer = None
        self.move_counter = None
        self.root = root
        self.window = tk.Toplevel(
            self.root)  # Crea una ventana secundaria para el juego
        self.window.geometry("500x500")  # Tamaño de la ventana de juego
        self.window.title(
            "Memoriza las cartas")  # Título de la ventana de juego
        self.labels = {}  # Diccionario para almacenar las cartas en el tablero
        self.on_card_click_callback = on_card_click_callback  # Callback cuando se hace clic en una carta
        self.update_move_count_callback = update_move_count_callback  # Callback para actualizar el contador de movimientos
        self.update_time_callback = update_time_callback  # Callback para actualizar el temporizador

    def create_board(self, model):
        """
        Crea el tablero de juego y coloca las cartas en la ventana.

        :param model: El modelo del juego, que contiene los datos del tablero y las imágenes.
        """
        board_size = len(model.board)  # Tamaño del tablero

        # Obtener la imagen oculta para representar el reverso de la carta
        self.hidden_image = model.hidden

        # Crear una cuadrícula de etiquetas (Labels) en la ventana para representar las cartas
        for row in range(board_size):
            for col in range(board_size):
                # Crear el label para la carta, asociando la imagen oculta inicialmente
                label = tk.Label(self.window, image=self.hidden_image)
                label.grid(row=row, column=col, padx=5, pady=5)

                # Asociar un identificador único (ID de la carta) al label
                label.image_id = model.board[row][col]

                # Asociar la acción de hacer clic en la carta con el callback
                label.bind("<Button-1>",
                           lambda e, r=row, c=col: self.on_card_click_callback(
                               (r, c)))

                # Almacenar el label en el diccionario con su posición en el tablero
                self.labels[(row, col)] = label

        # Añadir etiquetas para mostrar el contador de movimientos y el temporizador
        self.move_counter = tk.Label(self.window, text="Movimientos: 0")
        self.move_counter.grid(row=board_size, column=0,
                               columnspan=board_size // 2)
        self.timer = tk.Label(self.window, text="00:00")
        self.timer.grid(row=board_size, column=board_size // 2,
                        columnspan=board_size // 2)

    def update_board(self, pos, image):
        """
        Actualiza la imagen de una carta en una posición específica (pos), configurando la imagen
        correspondiente según el image_id proporcionado.

        :param pos: La posición de la carta en el tablero.
        :param image: La imagen a mostrar en esa posición.
        """
        self.labels.get(pos).config(image=image)

    def reset_cards(self, pos1, pos2):
        """
        Restaura las imágenes de dos cartas a su estado oculto, útil cuando el jugador no encuentra
        una coincidencia entre dos cartas seleccionadas.

        :param pos1: La posición de la primera carta a restaurar.
        :param pos2: La posición de la segunda carta a restaurar.
        """
        self.labels.get(pos1).config(image=self.hidden_image)
        self.labels.get(pos2).config(image=self.hidden_image)

    def update_move_count(self, moves):
        """
        Actualiza el contador de movimientos en la interfaz, modificando el texto de la etiqueta
        que muestra los movimientos actuales.

        :param moves: El número actual de movimientos realizados por el jugador.
        """
        self.move_counter.config(text=f"Movimientos: {moves}")

    def update_time(self, time):
        """
        Actualiza el temporizador en la interfaz para reflejar el tiempo transcurrido.

        :param time: El tiempo transcurrido que se mostrará en el temporizador.
        """
        self.timer.config(text=time)
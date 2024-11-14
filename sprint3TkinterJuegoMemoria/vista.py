import tkinter as tk
from tkinter import simpledialog, messagebox

class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback, quit_callback):
        """
        Inicializa el menú principal de la aplicación con los botones necesarios.

        :param root: Referencia a la ventana principal de Tkinter.
        :param start_game_callback: Función a ejecutar cuando se haga clic en el botón "Jugar".
        :param show_stats_callback: Función a ejecutar cuando se haga clic en el botón "Estadísticas".
        :param quit_callback: Función a ejecutar cuando se haga clic en el botón "Salir".
        """
        self.root = root
        self.root.title("Memoriza las cartas!")  # Título de la ventana principal
        self.root.geometry("400x400")  # Tamaño de la ventana
        self.root.config(bg="#f0f0f0")  # Color de fondo de la ventana

        # Título del juego
        title_label = tk.Label(root, text="¡Memoriza las cartas!", font=("Arial", 24, "bold"), bg="#f0f0f0", fg="#333")
        title_label.pack(pady=20)

        # Crear el botón "Jugar" y enlazarlo con el callback para iniciar el juego
        play_button = tk.Button(root, text="Jugar", command=start_game_callback, font=("Arial", 14), bg="#4CAF50", fg="white", relief="raised", width=20)
        play_button.pack(pady=10)

        # Crear el botón "Estadísticas" y enlazarlo con el callback para mostrar estadísticas
        stats_button = tk.Button(root, text="Estadísticas", command=show_stats_callback, font=("Arial", 14), bg="#2196F3", fg="white", relief="raised", width=20)
        stats_button.pack(pady=10)

        # Crear el botón "Salir" y enlazarlo con el callback para cerrar la aplicación
        quit_button = tk.Button(root, text="Salir", command=quit_callback, font=("Arial", 14), bg="#f44336", fg="white", relief="raised", width=20)
        quit_button.pack(pady=10)

    def ask_player_name(self):
        """
        Lanza un cuadro de diálogo para que el jugador ingrese su nombre.

        :return: El nombre ingresado o None si el jugador cancela el diálogo.
        """
        while True:
            # Mostrar un cuadro de diálogo para ingresar el nombre
            player_name = simpledialog.askstring("Nombre de jugador", "Teclea tu nombre:", initialvalue="Guybrush")

            if player_name is None:
                # Si el jugador cancela, se retorna None
                return None
            elif len(player_name.strip()) < 1:
                # Si el nombre está vacío, se muestra un error
                messagebox.showerror("Error", "El nombre no puede estar vacío. Por favor, ingresa un nombre.")
            else:
                # Si el nombre es válido, se retorna el nombre ingresado
                return player_name.strip()

    def show_difficulty_selection(self):
        """
        Muestra una ventana para seleccionar la dificultad del juego.

        Crea una ventana emergente para que el jugador seleccione una dificultad entre
        fácil, medio y difícil.
        """
        difficulty_toplevel = tk.Toplevel(self.root)  # Crea una nueva ventana emergente
        difficulty_toplevel.geometry("200x200")  # Establece el tamaño de la ventana
        label = tk.Label(difficulty_toplevel, text="Selecciona la dificultad", font=("Arial", 14))
        label.pack(pady=5)

        var_difficulty = tk.IntVar()  # Variable para almacenar la selección
        var_difficulty.set(1)  # Valor por defecto (fácil)

        # Radiobuttons para elegir la dificultad
        tk.Radiobutton(difficulty_toplevel, text="Fácil", variable=var_difficulty, value=1).pack(pady=5)
        tk.Radiobutton(difficulty_toplevel, text="Medio", variable=var_difficulty, value=2).pack(pady=5)
        tk.Radiobutton(difficulty_toplevel, text="Difícil", variable=var_difficulty, value=3).pack(pady=5)

        def choose_difficulty():
            """Asigna la dificultad elegida y cierra la ventana emergente."""
            difficulty_toplevel.destroy()  # Cierra el diálogo

        tk.Button(difficulty_toplevel, text="Elegir", command=choose_difficulty, bg="#4CAF50", fg="white", font=("Arial", 12)).pack(pady=5)  # Botón para confirmar la selección
        difficulty_toplevel.grab_set()  # Hace que la ventana de selección de dificultad sea modal (bloquea la ventana principal)
        difficulty_toplevel.wait_window()  # Espera a que se cierre la ventana

        return var_difficulty.get()


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
        self.hidden_image = None # Imagen trasera de la carta
        self.timer = None # Etiqueta para emporizador
        self.move_counter = None # Etiqueta para contar movimientos
        self.root = root # Ventana padre
        self.window = tk.Toplevel(self.root)  # Crea una ventana secundaria para el juego
        self.window.title("Memoriza las cartas")  # Título de la ventana de juego
        self.labels = {}  # Diccionario para almacenar las cartas en el tablero
        self.on_card_click_callback = on_card_click_callback  # Callback cuando se hace clic en una carta
        self.update_move_count_callback = update_move_count_callback  # Callback para actualizar el contador de movimientos
        self.update_time_callback = update_time_callback  # Callback para actualizar el temporizador

        # Inicializar el tamaño de la ventana
        self.window.geometry("700x700")  # Tamaño de la ventana de juego (se ajustará luego)
        self.window.update_idletasks()  # Actualiza las tareas pendientes de la ventana
        self.center_window()  # Llamar a la función para centrar la ventana

    def create_board(self, model):
        """
        Crea el tablero de juego y coloca las cartas en la ventana.

        :param model: El modelo del juego, que contiene los datos del tablero y las imágenes.
        """
        board_size = len(model.board)  # Tamaño del tablero (suponiendo que es cuadrado)

        # Obtener la imagen oculta para representar el reverso de la carta
        self.hidden_image = model.hidden

        # Crear el label con el mensaje "Encuentra las parejas de cartas"
        instruction_label = tk.Label(self.window,
                                     text="Encuentra las parejas de cartas",
                                     font=("Arial", 16), bg="#4CAF50",
                                     fg="white", relief="raised", width=30)
        instruction_label.pack(pady=(20, 10))  # Añadimos un poco de espacio arriba y abajo

        # Crear el Frame que contendrá el tablero y centrarlo en la ventana
        board_frame = tk.Frame(self.window, bg="white")
        board_frame.pack(pady=20, padx=20, expand=True)  # Expande para que quede centrado

        # Crear una cuadrícula de etiquetas (Labels) dentro del frame para representar las cartas
        for row in range(board_size):
            for col in range(board_size):
                # Crear el label para la carta, con la imagen oculta inicialmente
                label = tk.Label(board_frame, image=self.hidden_image)
                label.grid(row=row, column=col, padx=5, pady=5)

                # Asignar el ID de la carta y la acción de clic
                label.image_id = model.board[row][col]
                label.bind("<Button-1>", lambda e, r=row, c=col: self.on_card_click_callback((r, c)))

                # Almacenar el label en el diccionario con su posición en el tablero
                self.labels[(row, col)] = label

        # Crear y colocar el contador de movimientos y el temporizador debajo del tablero
        self.move_counter = tk.Label(self.window, text="Movimientos: 0",
                                     font=("Arial", 14),
                                     bg="#4CAF50", fg="white", relief="raised",
                                     width=15)
        self.move_counter.pack(pady=(10, 5))

        self.timer = tk.Label(self.window, text="00:00", font=("Arial", 14),
                              bg="#2196F3", fg="white", relief="raised",
                              width=15)
        self.timer.pack(pady=(0, 20))

        self.window.update_idletasks()  # Asegura que todos los componentes hayan sido renderizados
        self.center_window()  # Llamar a la función para centrar la ventana después de crear el contenido

    def center_window(self):
        """
        Centra la ventana en el monitor.
        """
        window_width = self.window.winfo_width()  # Obtener el ancho de la ventana
        window_height = self.window.winfo_height()  # Obtener el alto de la ventana

        # Obtener las dimensiones de la pantalla
        screen_width = self.window.winfo_screenwidth()
        screen_height = self.window.winfo_screenheight()

        # Calcular las posiciones para centrar la ventana
        position_top = int(screen_height / 2 - window_height / 2)
        position_right = int(screen_width / 2 - window_width / 2)

        # Establecer la geometría de la ventana centrada
        self.window.geometry(f'{window_width}x{window_height}+{position_right}+{position_top}')

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


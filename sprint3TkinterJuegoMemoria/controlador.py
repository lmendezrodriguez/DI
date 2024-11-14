import json
import tkinter as tk
from tkinter import Radiobutton, Button, messagebox, ttk

from modelo import GameModelo
from vista import MainMenu, GameView


class GameController:
    def __init__(self, root):
        """
        Inicializa el controlador del juego y configura los componentes del menú principal.

        :param root: Referencia a la ventana principal de Tkinter.
        """
        self.loading_window = None  # Ventana de carga que se mostrará mientras se cargan las imágenes
        self.game_view = None  # Vista del juego
        self.root = root  # Ventana principal de la aplicación
        self.model = GameModelo  # Modelo del juego, aún no inicializado
        self.selected = []  # Lista de cartas seleccionadas
        self.timer_started = False  # Bandera para el inicio del temporizador

        # Crear el menú principal y pasarle las funciones callback
        self.main_menu = MainMenu(
            root,
            self.start_game,  # Función para iniciar el juego
            self.show_stats,  # Función para mostrar estadísticas
            self.quit_game  # Función para salir del juego
        )

    def start_game(self):
        """Inicia una nueva partida."""

        # Mostrar la selección de dificultad y esperar a que el usuario seleccione
        self.show_difficulty_selection()

        # Si existe un nombre de jugador y dificultad seleccionada, crear el modelo del juego
        if self.selected_difficulty is not None:
            player_name = self.main_menu.ask_player_name()  # Solicita el nombre del jugador
            if player_name is None:
                messagebox.showerror("Error",
                                     "No has seleccionado ningún nombre.")
            else:
                self.model = GameModelo(self.selected_difficulty,
                                        player_name)  # Crear el modelo con los datos seleccionados
                self.show_loading_window(
                    "Cargando tablero")  # Muestra la ventana de carga
                self.check_images_loaded()  # Revisa si las imágenes están cargadas
                self.root.wait_window(
                    self.loading_window)  # Espera hasta que la ventana de carga se cierre
                self.timer_started = True
                self.game_view = GameView(self.root, self.on_card_click,
                                          self.update_move_count,
                                          self.update_time)  # Vista del juego
                self.game_view.window.protocol("WM_DELETE_WINDOW",
                                               self.return_to_main_menu)
                self.root.withdraw()  # Oculta la ventana principal mientras juega
                self.game_view.create_board(
                    self.model)  # Crea el tablero en la vista


        else:
            messagebox.showerror("Error",
                                 "No has seleccionado ninguna dificultad.")

    def show_stats(self):
        """Muestra las estadísticas de juego."""

        messagebox.showinfo("Estadísticas", self.load_scores())

    def quit_game(self):
        """Cierra la aplicación."""
        self.root.quit()  # Cierra la ventana principal de la aplicación

    def show_difficulty_selection(self):
        """
        Muestra una ventana para seleccionar la dificultad del juego.

        Crea una ventana emergente para que el jugador seleccione una dificultad entre
        fácil, medio y difícil.
        """
        difficulty_toplevel = tk.Toplevel(
            self.root)  # Crea una nueva ventana emergente
        difficulty_toplevel.geometry(
            "200x200")  # Establece el tamaño de la ventana
        label = tk.Label(difficulty_toplevel,
                         text="Selecciona la dificultad")  # Etiqueta informativa
        label.pack(pady=5)

        var_difficulty = tk.IntVar()  # Variable para almacenar la selección
        var_difficulty.set(1)  # Valor por defecto (fácil)

        # Radiobuttons para elegir la dificultad
        Radiobutton(difficulty_toplevel, text="Fácil", variable=var_difficulty,
                    value=1).pack(pady=5)
        Radiobutton(difficulty_toplevel, text="Medio", variable=var_difficulty,
                    value=2).pack(pady=5)
        Radiobutton(difficulty_toplevel, text="Difícil",
                    variable=var_difficulty, value=3).pack(pady=5)

        def choose_difficulty():
            """Asigna la dificultad elegida y cierra la ventana emergente."""
            self.selected_difficulty = var_difficulty.get()
            difficulty_toplevel.destroy()  # Cierra el diálogo

        Button(difficulty_toplevel, text="Elegir",
               command=choose_difficulty).pack(
            pady=5)  # Botón para confirmar la selección
        difficulty_toplevel.grab_set()  # Hace que la ventana de selección de dificultad sea modal (bloquea la ventana principal)
        difficulty_toplevel.wait_window()  # Espera a que se cierre la ventana

    def show_loading_window(self, message):
        """
        Muestra una ventana de carga mientras se están preparando los recursos (tablero e imágenes).

        :param message: El mensaje a mostrar en la ventana de carga.
        """
        self.loading_window = tk.Toplevel(self.root)
        self.loading_window.title("Cargando")  # Título de la ventana de carga
        self.loading_window.geometry("250x100")  # Tamaño de la ventana
        self.loading_window.grab_set()  # Bloquea la interacción con la ventana principal

        # Etiqueta con el mensaje de carga
        label = tk.Label(self.loading_window, text=message)
        label.pack(pady=20)

        # Barra de progreso indeterminada
        progress = ttk.Progressbar(self.loading_window, mode="indeterminate")
        progress.pack(pady=10, padx=20)
        progress.start()  # Inicia la barra de progreso

    def check_images_loaded(self):
        """
        Verifica si las imágenes del juego han sido cargadas.
        Si están cargadas, cierra la ventana de carga, si no, reintenta cada 500 ms.
        """
        if self.model.images_loaded:  # Si las imágenes están cargadas
            self.loading_window.destroy()  # Cierra la ventana de carga
        else:
            self.loading_window.after(500,
                                      self.check_images_loaded)  # Reintenta después de 500ms

    def on_card_click(self, pos):
        """
        Maneja el clic en una carta del tablero.

        :param pos: La posición de la carta seleccionada en el tablero.
        """
        self.model.start_timer()
        self.update_time()
        print(pos)
        if len(self.selected) < 1:
            self.selected.append(pos)
            image_id = self.model.board[pos[0]][pos[1]]
            image = self.model.images.get(image_id)
            self.game_view.update_board(pos, image)
        elif len(self.selected) < 2 and self.selected[0] != pos:
            #self.game_view.window.attributes('-disabled', True)
            self.selected.append(pos)
            image_id = self.model.board[pos[0]][pos[1]]
            image = self.model.images.get(image_id)
            self.game_view.update_board(pos, image)
            self.handle_card_selection()
            self.model.moves += 1
            self.update_move_count(self.model.moves)
            self.check_game_complete()

    def handle_card_selection(self):
        """
        Maneja la selección de una carta por parte del jugador.
        """
        if not self.model.check_match(self.selected[0], self.selected[1]):
            self.game_view.window.after(500, self.game_view.reset_cards,
                                        self.selected[0], self.selected[1])
        self.selected.clear()

    def update_move_count(self, moves):
        """
        Actualiza el contador de movimientos.

        :param moves: El número de movimientos realizados hasta el momento.
        """
        self.game_view.move_counter.config(
            text=f"Movimiento: {moves}")

    def check_game_complete(self):
        """
        Verifica si el juego ha terminado.

        :return: True si el juego está completo, False en caso contrario.
        """
        if self.model.is_game_complete():
            messagebox.showinfo("¡Albricias!",
                                f"Has encontrado las {self.model.hits} parejas en {self.model.moves} movimientos!")
            self.return_to_main_menu()

    def return_to_main_menu(self):
        self.destroy()  # Cierra la ventana Toplevel
        self.root.deiconify()

    def update_time(self):
        """
        Actualiza el tiempo de juego.

        :param time: El tiempo transcurrido desde el inicio del juego.
        """
        if self.timer_started:
            current_time = self.model.get_time()  # Obtiene el tiempo transcurrido desde el modelo
            self.game_view.update_time(
                current_time)  # Actualiza la vista con el tiempo actual

            # Repite la llamada a este método después de 1 segundo en un nuevo hilo
            self.root.after(1000, self.update_time)  # Llama cada 1 segundo

    def destroy(self):
        """
        Cierra la ventana del juego y restablece los recursos y la interfaz.
        """
        # 1. Cerrar la ventana del juego
        if self.game_view is not None:
            self.game_view.window.destroy()

        # 2. Restablecer variables del juego
        self.selected = []  # Limpiar selecciones de cartas
        self.timer_started = False  # Detener el temporizador

    def load_scores(self):
        """
        Carga las puntuaciones previas.
        """
        try:
            with open("res/ranking.txt", 'r', encoding='utf-8') as file:
                scores = json.load(file)
        except FileNotFoundError:
            # Crear una estructura vacía si el archivo no existe
            scores = {'fácil': [], 'medio': [], 'difícil': []}
        output = "Ranking de Puntajes:\n\n"
        for difficulty, points in scores.items():
            output += f"Dificultad: {difficulty.capitalize()}\n"
            if points:
                for i, score in enumerate(points, start=1):
                    output += f"  {i}. {score['nombre']} - Movimientos: {score['movimientos']} - Fecha: {score['fecha']}\n"
            else:
                output += "  No hay puntajes registrados.\n"
            output += "\n"  # Salto de línea entre dificultades
        return output
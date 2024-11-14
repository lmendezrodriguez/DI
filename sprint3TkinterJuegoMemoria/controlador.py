import json
import tkinter as tk
from tkinter import Radiobutton, Button, messagebox, ttk

from select import select

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
        self.block_clicks = False # Bandera para bloqueo de cartas

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
        selected_difficulty = self.main_menu.show_difficulty_selection()

        # Si existe un nombre de jugador y dificultad seleccionada, crear el modelo del juego
        if selected_difficulty is not None:
            player_name = self.main_menu.ask_player_name()  # Solicita el nombre del jugador
            if player_name is None:
                messagebox.showerror("Error",
                                     "No has seleccionado ningún nombre.")
            else:
                self.model = GameModelo(selected_difficulty,
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
        # Si ya hay dos cartas seleccionadas o si estamos esperando a que las cartas se desvolteen, no hacer nada
        if len(self.selected) >= 2 or self.block_clicks:
            return

        # Inicia el temporizador en el primer clic
        self.model.start_timer()
        self.update_time()

        # Mostrar la carta seleccionada
        image_id = self.model.board[pos[0]][pos[1]]
        image = self.model.images.get(image_id)

        # Actualizar el tablero con la carta volteada
        self.game_view.update_board(pos, image)

        # Agregar la carta a la lista de seleccionadas
        self.selected.append(pos)

        # Si ya hay dos cartas seleccionadas, comparar y manejar la selección
        if len(self.selected) == 2:
            # Incrementar los movimientos
            self.model.moves += 1
            self.update_move_count(self.model.moves)

            # Bloquear nuevos clics hasta que las cartas se comparen
            self.block_clicks = True

            # Verificar si las cartas coinciden
            self.handle_card_selection()

            # Verificar si el juego ha terminado
            self.check_game_complete()

    def handle_card_selection(self):
        """
        Maneja la selección de dos cartas por parte del jugador y verifica si coinciden.
        Si no coinciden, las voltea de nuevo después de un breve retraso.
        """
        if self.model.check_match(self.selected[0], self.selected[1]):
            # Si las cartas coinciden, no hacer nada (las cartas permanecen descubiertas)
            self.selected.clear()
        else:
            # Si no coinciden, volver a voltear las cartas después de un pequeño retraso
            self.game_view.window.after(500, self.game_view.reset_cards,
                                        self.selected[0], self.selected[1])
            self.selected.clear()

        # Después de un breve retraso, habilitar nuevamente los clics
        self.game_view.window.after(500, self.enable_clicks)

    def enable_clicks(self):
        """
        Permite hacer clic en las cartas nuevamente después de una breve espera.
        """
        self.block_clicks = False

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
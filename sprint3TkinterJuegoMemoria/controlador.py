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
        self.game_view = None
        self.root = root
        self.model = GameModelo  # Suponiendo que se inicializará el modelo en otro momento
        self.selected = []
        self.timer_started = False

        # Crear el menú principal y pasarle las funciones callback
        self.main_menu = MainMenu(
            root,
            self.start_game,
            self.show_stats,
            self.quit_game
        )

    def start_game(self):
        """Inicia una nueva partida."""
        print("Iniciando el juego...")

        # Mostrar la selección de dificultad y esperar a que el usuario seleccione
        self.show_difficulty_selection()
        # Si existe nombre y dificultad se crea el modelo, si no se lanza error
        if self.selected_difficulty is not None:
            player_name = self.main_menu.ask_player_name()
            print(player_name)
            if player_name is None:
                messagebox.showerror("Error",
                                     "No has seleccionado ningún nombre.")
            else:
                self.model = GameModelo(self.selected_difficulty, player_name)
                self.show_loading_window("Cargando tablero")
                print(self.model.images_loaded)
                # Crear vista del juego

        else:
            messagebox.showerror("Error",
                                 "No has seleccionado ninguna dificultad.")

        # self.game_view = GameView(...)

    def show_stats(self):
        """Muestra las estadísticas de juego."""
        print("Mostrando estadísticas...")
        # TODO

    def quit_game(self):
        """Cierra la aplicación."""
        self.root.quit()

    def show_difficulty_selection(self):
        """Abre una ventana para seleccionar la dificultad"""
        # Se crea TopLevel para elegir dificultad
        difficulty_toplevel = tk.Toplevel(self.root)
        difficulty_toplevel.geometry("200x200")
        # Etiqueta, variable tkinter y radiobuttons
        # para manejar selección de dificultad
        label = tk.Label(difficulty_toplevel,
                         text="Selecciona la dificultad")
        label.pack(pady=5)
        var_difficulty = tk.IntVar()
        var_difficulty.set(1)
        Radiobutton(difficulty_toplevel, text="Fácil", variable=var_difficulty,
                    value=1).pack(pady=5)
        Radiobutton(difficulty_toplevel, text="Medio", variable=var_difficulty,
                    value=2).pack(pady=5)
        Radiobutton(difficulty_toplevel, text="Difícil",
                    variable=var_difficulty,
                    value=3).pack(pady=5)

        # Función para capturar el valor y cerrar el diálogo
        def choose_difficulty():
            """ Asigna la dificultad elegida a una variable"""
            self.selected_difficulty = var_difficulty.get()
            difficulty_toplevel.destroy()  # Cierra el diálogo

        # Botón para confirmar la selección
        Button(difficulty_toplevel, text="Elegir",
               command=choose_difficulty).pack(pady=5)
        # Configurar la ventana como modal para bloquear interacción con `root`
        difficulty_toplevel.grab_set()

        # Espera a que el diálogo se cierre antes de continuar
        difficulty_toplevel.wait_window()

    def show_loading_window(self, message):
        # Crear la ventana de carga
        loading_window = tk.Toplevel(self.root)
        loading_window.title("Cargando")
        loading_window.geometry("250x100")

        # Prevenir interacción con la ventana principal
        loading_window.grab_set()

        # Crear el mensaje de carga
        label = tk.Label(loading_window, text=message)
        label.pack(pady=20)

        # Crear un progress bar
        progress = ttk.Progressbar(loading_window, mode="indeterminate")
        progress.pack(pady=10, padx=20)
        progress.start()

        # Función para cerrar la ventana de carga una vez que las imágenes estén listas
        def check_images_loaded():
            if self.model.images_loaded:  # Verifica si las imágenes están cargadas
                loading_window.destroy()
                self.game_view = GameView(self.root, self.on_card_click,
                                          self.update_move_count,
                                          self.update_time)
                self.game_view.create_board(self.model)
            else:
                loading_window.after(500,
                                     check_images_loaded)  # Reintenta cada 500ms

        check_images_loaded()

    def on_card_click(self, pos):
        pass

    def handle_card_selection(self):
        pass

    def update_move_count(self, moves):
        pass

    def check_game_complete(self):
        pass

    def return_to_main_menu(self):
        pass

    def show_stats(self):
        pass

    def update_time(self, time):
        pass

import tkinter as tk
from tkinter import Radiobutton, Button, messagebox

from modelo import GameModelo
from vista import MainMenu


class GameController:
    def __init__(self, root):
        """
        Inicializa el controlador del juego y configura los componentes del menú principal.

        :param root: Referencia a la ventana principal de Tkinter.
        """
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
        else:
            messagebox.showerror("Error",
                                 "No has seleccionado ninguna dificultad.")
        #print(self.model.__str__())
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
        var_difficulty = tk.StringVar()
        var_difficulty.set("fácil")
        Radiobutton(difficulty_toplevel, text="Fácil", variable=var_difficulty,
                    value="fácil").pack(pady=5)
        Radiobutton(difficulty_toplevel, text="Medio", variable=var_difficulty,
                    value="medio").pack(pady=5)
        Radiobutton(difficulty_toplevel, text="Difícil", variable=var_difficulty,
                    value="difícil").pack(pady=5)

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
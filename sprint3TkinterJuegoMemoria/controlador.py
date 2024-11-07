import tkinter as tk
from vista import MainMenu


class GameController:
    def __init__(self, root):
        """
        Inicializa el controlador del juego y configura los componentes del menú principal.

        :param root: referencia a la ventana principal de Tkinter.
        """
        self.root = root
        self.model = None  # Suponiendo que se inicializará el modelo en otro momento
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
        # TODO
        # self.model = GameModel(...)
        # self.game_view = GameView(...)

    def show_stats(self):
        """Muestra las estadísticas de juego."""
        print("Mostrando estadísticas...")
        # TODO

    def quit_game(self):
        """Cierra la aplicación."""
        self.root.quit()


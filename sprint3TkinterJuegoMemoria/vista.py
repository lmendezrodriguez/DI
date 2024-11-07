import tkinter
import tkinter as tk


import tkinter as tk

class MainMenu:
    def __init__(self, root, start_game_callback, show_stats_callback, quit_callback):
        """
        Inicializa el menú principal de la aplicación con los botones necesarios.

        :param root: referencia a la ventana principal de Tkinter.
        :param start_game_callback: función a ejecutar cuando se haga clic en el botón "Jugar".
        :param show_stats_callback: función a ejecutar cuando se haga clic en el botón "Estadísticas".
        :param quit_callback: función a ejecutar cuando se haga clic en el botón "Salir".
        """
        self.root = root
        self.root.title("Memoriza las cartas!")
        self.root.geometry("400x600")

        # Crear el botón "Jugar" y enlazarlo con el callback para iniciar el juego
        play_button = tk.Button(root, text="Jugar", command=start_game_callback)
        play_button.pack(pady=10)

        # Crear el botón "Estadísticas" y enlazarlo con el callback para mostrar estadísticas
        stats_button = tk.Button(root, text="Estadísticas", command=show_stats_callback)
        stats_button.pack(pady=10)

        # Crear el botón "Salir" y enlazarlo con el callback para cerrar la aplicación
        quit_button = tk.Button(root, text="Salir", command=quit_callback)
        quit_button.pack(pady=10)


import tkinter
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
        self.root.title("Memoriza las cartas!")
        self.root.geometry("400x600")

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
        """Lanza un diálogo para introducir el nombre de la jugadora
        :return: El nombre ingresado por la jugadora o None si se cancela el diálogo.
        """
        while True:
            player_name = simpledialog.askstring("Nombre de jugador",
                                                 "Teclea tu nombre:",
                                                 initialvalue="Guybrush")
            if player_name is None:
                # El usuario canceló y se devuelve None
                return None
            elif len(player_name) < 1:
                # El nombre está vacío, se muestra un mensaje de error
                messagebox.showerror("Error",
                                     "El nombre no puede estar vacío. Por favor, ingresa un nombre.")
            else:
                # Se ingresó un nombre válido
                return player_name

import tkinter as tk
from modelo import ModeloJuego
from vista import VistaJuego
from controlador import ControladorJuego

if __name__ == "__main__":
    """
    Punto de entrada para la aplicación de Piedra, Papel o Tijera.
    Inicializa el modelo, controlador y vista, y arranca el bucle principal de la interfaz gráfica.
    """
    root = tk.Tk()  # Crea la ventana principal de la aplicación
    modelo = ModeloJuego()  # Inicializa el modelo del juego
    controlador = ControladorJuego(modelo, None)  # Crea el controlador, aún sin vista
    vista = VistaJuego(root, controlador)  # Inicializa la vista con la ventana y el controlador
    controlador.vista = vista  # Asocia la vista al controlador
    root.mainloop()  # Inicia el bucle principal de la aplicación

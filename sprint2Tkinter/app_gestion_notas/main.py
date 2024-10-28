import tkinter as tk

# Importa el Modelo, la Vista y el Controlador de la aplicación
from controlador import ControladorNotas
from modelo import ModeloNotas
from vista import VistaNotas


def main():
    """
    Inicializa y ejecuta la aplicación de gestión de notas con el patrón MVC.
    """
    root = tk.Tk()  # Crea la ventana principal de la aplicación

    # Instancia el modelo (gestión de datos) y la vista (interfaz gráfica)
    modelo = ModeloNotas()
    vista = VistaNotas(root)

    # Crea el controlador para coordinar las acciones entre el modelo y la vista
    controlador = ControladorNotas(modelo, vista)

    # Centra la ventana principal después de inicializar la interfaz
    root.after(100, controlador.centrar_pantalla)
    root.mainloop()  # Inicia el bucle principal de la aplicación


# Ejecuta la función main si el script se ejecuta directamente
if __name__ == "__main__":
    main()

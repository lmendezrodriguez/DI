import tkinter as tk

class VistaNotas:
    """
    Vista de la aplicación de gestión de notas. Configura la interfaz gráfica
    y todos los elementos visuales de la aplicación.
    """

    def __init__(self, root):
        """
        Inicializa la ventana principal y sus componentes.

        Args:
            root (tk.Tk): La ventana principal de la aplicación.
        """
        # Configuración de la ventana principal
        self.root = root
        self.root.title("App gestión de notas")
        self.root.geometry("600x450")

        # Etiqueta de título de la aplicación
        self.etiqueta = tk.Label(self.root, text="Aplicación de gestión de notas")
        self.etiqueta.pack(pady=5)

        # Listbox para mostrar las notas
        self.listbox = tk.Listbox(self.root, selectmode=tk.SINGLE, width=50)
        self.listbox.pack(pady=5)

        # Campo de entrada para agregar notas
        self.entrada = tk.Entry(self.root, width=50)
        self.entrada.pack(pady=5)

        # Botón para agregar una nota
        self.button_agregar = tk.Button(self.root, text="Agregar Nota")
        self.button_agregar.pack(pady=5)

        # Botón para eliminar una nota seleccionada
        self.button_eliminar = tk.Button(self.root, text="Eliminar Nota")
        self.button_eliminar.pack(pady=5)

        # Botón para cargar notas desde un archivo
        self.button_cargar = tk.Button(self.root, text="Cargar Notas")
        self.button_cargar.pack(pady=5)

        # Botón para guardar notas en un archivo
        self.button_guardar = tk.Button(self.root, text="Guardar Notas")
        self.button_guardar.pack(pady=5)

        # Botón para descargar y mostrar una imagen
        self.button_descargar = tk.Button(self.root, text="Descargar Imagen")
        self.button_descargar.pack(pady=5)

        # Etiqueta para mostrar las coordenadas del cursor
        self.etiqueta_coordenadas = tk.Label(self.root)
        self.etiqueta_coordenadas.pack(pady=5)

        # Etiqueta para mostrar la imagen descargada
        self.etiqueta_imagen = tk.Label(self.root)
        self.etiqueta_imagen.pack(pady=5)

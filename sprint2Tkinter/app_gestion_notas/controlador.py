from tkinter import messagebox
import tkinter as tk
from PIL import Image, ImageTk
import requests
from io import BytesIO
import threading


class ControladorNotas:
    """
    Controlador para gestionar la lógica de la aplicación de notas, incluyendo
    operaciones de agregar, eliminar, guardar y cargar notas, así como
    manejar la descarga y visualización de imágenes.
    """

    def __init__(self, modelo, vista):
        """
        Inicializa el controlador, configurando el modelo y la vista, y asigna
        los comandos de los botones a los métodos correspondientes.

        Args:
            modelo: Instancia del modelo que gestiona los datos de las notas.
            vista: Instancia de la vista que muestra la interfaz gráfica.
        """
        self.modelo = modelo
        self.vista = vista

        # Asignación de funciones a los botones de la vista
        self.vista.button_agregar.config(command=self.agregar_nota)
        self.vista.button_eliminar.config(command=self.eliminar_nota)
        self.vista.button_guardar.config(command=self.guardar_notas)
        self.vista.button_cargar.config(command=self.cargar_notas)
        self.vista.root.bind("<Button-1>", self.actualizar_coordenadas)
        self.vista.button_descargar.config(command=self.iniciar_descarga)

    def actualizar_listbox(self):
        """Actualiza el Listbox de la vista con las notas actuales."""
        self.vista.listbox.delete(0, tk.END)
        for nota in self.modelo.obtener_notas():
            self.vista.listbox.insert(tk.END, nota)

    def agregar_nota(self):
        """Añade una nueva nota al modelo y actualiza la vista."""
        nota = self.vista.entrada.get()
        self.modelo.agregar_nota(nota)
        self.actualizar_listbox()

    def eliminar_nota(self):
        """Elimina la nota seleccionada del modelo y actualiza la vista."""
        seleccion = self.vista.listbox.curselection()
        if seleccion:
            indice = seleccion[0]
            self.modelo.eliminar_nota(indice)
        else:
            messagebox.showwarning("Advertencia", "Por favor selecciona una nota para eliminar.")
        self.actualizar_listbox()

    def guardar_notas(self):
        """Guarda las notas en un archivo y muestra un mensaje de confirmación."""
        self.modelo.guardar_notas()
        messagebox.showinfo("Información", "Notas descargadas")

    def cargar_notas(self):
        """Carga las notas desde el archivo y actualiza el Listbox de la vista."""
        self.vista.listbox.delete(0, tk.END)
        for nota in self.modelo.cargar_notas():
            self.vista.listbox.insert(tk.END, nota)

    def actualizar_coordenadas(self, event):
        """
        Actualiza las coordenadas del cursor en la etiqueta de la vista.

        Args:
            event: Evento de clic del ratón.
        """
        self.vista.etiqueta_coordenadas.config(text=f"Coordenadas: {event.x} , {event.y}")

    def descargar_imagen(self, url, callback):
        """
        Descarga una imagen desde una URL en un hilo separado y ejecuta el
        callback en el hilo principal para actualizar la vista.

        Args:
            url (str): URL de la imagen a descargar.
            callback (func): Función de callback para actualizar la etiqueta con la imagen.
        """
        try:
            respuesta = requests.get(url)
            respuesta.raise_for_status()  # Lanza excepción si falla la descarga
            imagen = Image.open(BytesIO(respuesta.content)).resize((150, 150), Image.Resampling.LANCZOS)
            imagen_tk = ImageTk.PhotoImage(imagen)
            self.vista.root.after(0, callback, imagen_tk)  # Llama al callback en el hilo principal
        except requests.exceptions.RequestException as e:
            print(f"Error al descargar la imagen: {e}")
            self.vista.root.after(0, callback, None)  # Maneja error en el hilo principal

    def actualizar_etiqueta(self, imagen_tk):
        """
        Actualiza la etiqueta de imagen en la vista con la imagen descargada o
        muestra un mensaje de error si la descarga falla.

        Args:
            imagen_tk: La imagen descargada y procesada para mostrar.
        """
        if imagen_tk:
            self.vista.etiqueta_imagen.config(image=imagen_tk)
            self.vista.etiqueta_imagen.image = imagen_tk  # Mantiene referencia de la imagen
            self.redimensionar_pantalla()  # Ajusta tamaño de la ventana
            self.vista.root.after(50, self.centrar_pantalla)  # Centra ventana tras ajuste
        else:
            self.vista.etiqueta_imagen.config(text="Error al descargar la imagen.")

    def iniciar_descarga(self):
        """Inicia un hilo para descargar la imagen desde la URL."""
        url = 'https://github.com/lmendezrodriguez/DI/blob/main/sprint2Tkinter/app_gestion_notas/res/mono3cabezas.jpg?raw=true'
        hilo = threading.Thread(target=self.descargar_imagen, args=(url, self.actualizar_etiqueta))
        hilo.start()

    def centrar_pantalla(self):
        """Centra la ventana principal en la pantalla."""
        ancho_pantalla = self.vista.root.winfo_screenwidth()
        alto_pantalla = self.vista.root.winfo_screenheight()
        ancho_ventana = self.vista.root.winfo_width()
        alto_ventana = self.vista.root.winfo_height()
        pwidth = round(ancho_pantalla / 2 - ancho_ventana / 2)
        pheight = round(alto_pantalla / 2 - alto_ventana / 2)
        posicion = f"{ancho_ventana}x{alto_ventana}+{pwidth}+{pheight}"
        self.vista.root.geometry(posicion)

    def redimensionar_pantalla(self):
        """Redimensiona la ventana principal para ajustarse al tamaño de la imagen descargada."""
        nuevo_alto = self.vista.root.winfo_height() + self.vista.etiqueta_imagen.winfo_reqheight()
        self.vista.root.geometry(f"600x{nuevo_alto}")

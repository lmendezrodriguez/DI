from tkinter import messagebox
from PIL import Image, ImageTk
import requests
from io import BytesIO


def descargar_imagen(url, size):
    """
    Descarga una imagen desde una URL y la redimensiona a un tamaño especificado.

    Parameters:
        url (str): La URL de la imagen a descargar.
        size (int): El tamaño (ancho y alto) al que se redimensionará la imagen.

    Returns:
        ImageTk.PhotoImage: La imagen redimensionada como un objeto PhotoImage de Tkinter.
        None: Si ocurre un error en la descarga o procesamiento de la imagen.
    """
    try:
        # Realiza la solicitud HTTP para obtener la imagen
        respuesta = requests.get(url)
        respuesta.raise_for_status()  # Lanza excepción si la descarga falla

        # Abre la imagen desde los datos descargados y la redimensiona
        imagen = Image.open(BytesIO(respuesta.content)).resize((size, size),
                                                               Image.Resampling.LANCZOS)

        # Convierte la imagen de PIL a formato compatible con Tkinter
        imagen_tk = ImageTk.PhotoImage(imagen)
        return imagen_tk  # Devuelve la imagen lista para usar en Tkinter

    except requests.exceptions.RequestException as e:
        # Muestra un mensaje de error si la descarga o procesamiento falla
        messagebox.showerror("Error",
                             "No se ha podido descargar la imagen alojada en " + url)
        return None

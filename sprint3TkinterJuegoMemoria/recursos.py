from tkinter import messagebox

from PIL import Image, ImageTk
import requests
from io import BytesIO

def descargar_imagen(url, size):
    try:
        respuesta = requests.get(url)
        respuesta.raise_for_status()  # Lanza excepción si falla la descarga
        imagen = Image.open(BytesIO(respuesta.content)).resize((size),
                                                               Image.Resampling.LANCZOS)
        imagen_tk = ImageTk.PhotoImage(imagen)
        return imagen_tk
    except requests.exceptions.RequestException as e:
        messagebox.showerror("Error",
                             "No se ha podido descargar la imagen alojada en " + url)
        return None


import tkinter as tk
from tkinter import Entry


def mostrar_texto():
    """
    Muestra en la etiqueta_texto_frame el texto introducido
    por el usuario en entrada

    """
    etiqueta_texto_frame.config(text=entrada.get())


def borrar_texto():
    """
    Borra el texto introducido por el usuario en entrada

    """
    entrada.delete(0, "end")


# Crear la ventana principal
root = tk.Tk()
root.title("Ejemplo de Frame")
root.geometry("300x200")

# Crear frame para Labels y Entry
frame_1 = tk.Frame(root, bd=2, relief="sunken")
frame_1.pack(padx=5, pady=5, fill="both", expand=True)

# Crear y configurar Labels y Entry
etiqueta_titulo_frame = tk.Label(frame_1, text="Escribe abajo:")
etiqueta_titulo_frame.pack(pady=5)
entrada = tk.Entry(frame_1, width=30, bg="white")
entrada.pack(pady=5)
etiqueta_texto_frame = tk.Label(frame_1, text="")
etiqueta_texto_frame.pack(pady=5)

# Crear y configurar frame 2
frame_2 = tk.Frame(root, bd=2, relief="sunken")
frame_2.pack(padx=5, pady=5, fill="both", expand=True)

# Crear y configurar botones
boton_mostrar_texto = tk.Button(frame_2, text="Mostrar texto",
                                command=mostrar_texto)
boton_mostrar_texto.pack(pady=5)
boton_eliminar_texto = tk.Button(frame_2, text="Borrar texto",
                                 command=borrar_texto)
boton_eliminar_texto.pack(pady=5)

# Iniciar el loop principal de la ventana
root.mainloop()

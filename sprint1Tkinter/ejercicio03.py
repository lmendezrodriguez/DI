import tkinter as tk
from tkinter import Entry


def mostrar_label():
    """
    Cambia el texto de etiqueta_entrada mostrando un saludo personalizado.

    Esta función muestra el texto "Hola, {nombre}" en el label
    etiqueta_entrada tomando el valor introducido por el usuario en entrada.
    """
    nombre = entrada.get()
    etiqueta_saludo.config(text=f"Hola, {nombre}!", bg="green")


root = tk.Tk()
root.title("Ejemplo de Entry")
root.geometry("300x200")

etiqueta_entrada = tk.Label(root, text="Escribe tu nombre!", pady=5)
etiqueta_entrada.pack()

etiqueta_saludo = tk.Label(root, text="", pady=5)
etiqueta_saludo.pack()

entrada = tk.Entry(root, width=30)
entrada.pack(pady=5)

boton_entrada = tk.Button(root, text="Saludo!",
                          command=mostrar_label, pady=5)
boton_entrada.pack()

root.mainloop()

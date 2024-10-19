import tkinter as tk


def cambiar_texto_label():
    """
    Cambia el texto de etiqueta_3 al presionar el botón.

    Esta función actualiza el texto de etiqueta_3 cuando se
    presiona el botón que la llama, cambiando su valor de
    "Botón sin pulsar" a "Botón pulsado".
    """
    etiqueta_3.config(text="Botón pulsado")


# Crear la ventana principal
root = tk.Tk()
root.title("Ejemplo de Labels")
root.geometry("300x200")

# Crear y empaquetar la primera etiqueta
etiqueta_1 = tk.Label(root, text="Bienvenido!")
etiqueta_1.pack()

# Crear y empaquetar la segunda etiqueta
etiqueta_2 = tk.Label(root, text="Lucía")
etiqueta_2.pack()

# Crear y empaquetar la tercera etiqueta con un texto inicial
etiqueta_3 = tk.Label(root, text="Botón sin pulsar")
etiqueta_3.pack()

# Crear y empaquetar un botón que cambia el texto de etiqueta_3
boton = tk.Button(root, text="Cambiar texto", command=cambiar_texto_label)
boton.pack()

# Iniciar el bucle principal de la interfaz gráfica
root.mainloop()

import tkinter as tk


def cambiar_texto_label():
    """
    Cambia el texto de la etiqueta_3 al presionar el botón.

    Esta función actualiza el texto de la etiqueta_3 cuando se
    presiona el botón que la llama, cambiando su valor de
    "Botón sin pulsar" a "Botón pulsado".
    """
    etiqueta_3.config(text="Botón pulsado")


root = tk.Tk()
root.title("Ejemplo de Labels")
root.geometry("300x200")

etiqueta_1 = tk.Label(root, text="Bienvenido!")
etiqueta_1.pack()

etiqueta_2 = tk.Label(root, text="Lucía")
etiqueta_2.pack()

etiqueta_3 = tk.Label(root, text="Botón sin pulsar")
etiqueta_3.pack()

boton = tk.Button(root, text="Cambiar texto", command=cambiar_texto_label)
boton.pack()

root.mainloop()

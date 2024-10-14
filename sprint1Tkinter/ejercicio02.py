import tkinter as tk


def cambiar_texto_label():
    """
    Cambia el texto de la etiqueta al presionar el boton_1.

    Esta función muestra el texto "Botón pulsado" en el label
    etiqueta cuando el botón se pulsa.
    """
    etiqueta.config(text="Botón pulsado")


def cerrar_ventana():
    """
        Cierra la ventana principal al presionar el boton_1.

        Esta función cierra la ventana root.
        """
    root.destroy()


root = tk.Tk()
root.title("Ejemplo de Buttons")
root.geometry("300x200")

etiqueta = tk.Label(root, text="")
etiqueta.pack()

boton_1 = tk.Button(root, text="Mostrar texto etiqueta",
                    command=cambiar_texto_label)
boton_1.pack()

boton_2 = tk.Button(root, text="Cerrar ventana", command=cerrar_ventana)
boton_2.pack()

root.mainloop()

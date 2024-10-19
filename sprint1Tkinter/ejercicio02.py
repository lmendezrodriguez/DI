import tkinter as tk


def cambiar_texto_label():
    """
    Cambia el texto de la etiqueta al presionar el botón 1.

    Esta función muestra el texto "Botón pulsado" en la etiqueta
    cuando el botón se pulsa.
    """
    etiqueta.config(text="Botón pulsado")


def cerrar_ventana():
    """
    Cierra la ventana principal al presionar el botón 2.

    Esta función cierra la ventana root.
    """
    root.destroy()


# Crear la ventana principal
root = tk.Tk()
root.title("Ejemplo de Buttons")
root.geometry("300x200")

# Crear y empaquetar la etiqueta (inicialmente vacía)
etiqueta = tk.Label(root, text="")
etiqueta.pack()

# Crear y empaquetar el primer botón que muestra el texto en la etiqueta
boton_1 = tk.Button(root, text="Mostrar texto etiqueta", command=cambiar_texto_label)
boton_1.pack()

# Crear y empaquetar el segundo botón que cierra la ventana
boton_2 = tk.Button(root, text="Cerrar ventana", command=cerrar_ventana)
boton_2.pack()

# Iniciar el bucle principal de la interfaz gráfica
root.mainloop()
import tkinter as tk


def cambiar_color_fondo():
    """
     Cambia el color de fondo de root cuando en función del Radiobutton
     seleccionado
     """
    root.config(bg=var_radio.get())


# Crear ventana principal
root = tk.Tk()
root.title("Ejemplo de Radiobutton")
root.geometry("300x200")

# Crear etiqueta de menú de Radiobuttons
etiqueta_chk_button = tk.Label(root, text="Elige un color", pady=5)
etiqueta_chk_button.pack()

# Variable para manejar los Radiobuttons
var_radio = tk.StringVar()
var_radio.set("white")

# Crear Radiobuttons para colores
radio_rojo = tk.Radiobutton(root, text="Rojo", variable=var_radio,
                            value="red", pady=5, command=cambiar_color_fondo)
radio_rojo.pack()

radio_verde = tk.Radiobutton(root, text="Verde", variable=var_radio,
                             value="green", pady=5,
                             command=cambiar_color_fondo)
radio_verde.pack()

radio_azul = tk.Radiobutton(root, text="Azul", variable=var_radio,
                            value="blue", pady=5, command=cambiar_color_fondo)
radio_azul.pack()

root.mainloop()

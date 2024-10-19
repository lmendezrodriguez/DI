import tkinter as tk


def cambiar_color_fondo():
    """
    Cambia el color de fondo de la ventana principal y todos los elementos
    según el Radiobutton seleccionado.
    """
    color = var_radio.get()
    root.config(bg=color)  # Cambia el color de fondo usando el valor seleccionado
    etiqueta_chk_button.config(bg=color)
    radio_azul.config(bg=color)
    radio_rojo.config(bg=color)
    radio_verde.config(bg=color)

# Crear ventana principal
root = tk.Tk()
root.title("Ejemplo de Radiobutton")
root.geometry("300x200")

# Crear etiqueta que indica el menú de Radiobuttons
etiqueta_chk_button = tk.Label(root, text="Elige un color", pady=5)
etiqueta_chk_button.pack()

# Variable para manejar los Radiobuttons
var_radio = tk.StringVar()
var_radio.set("red")  # Valor por defecto

# Crear Radiobuttons para seleccionar colores
radio_rojo = tk.Radiobutton(root, text="Rojo", variable=var_radio,
                             value="red", pady=5, command=cambiar_color_fondo)
radio_rojo.pack()

radio_verde = tk.Radiobutton(root, text="Verde", variable=var_radio,
                              value="green", pady=5, command=cambiar_color_fondo)
radio_verde.pack()

radio_azul = tk.Radiobutton(root, text="Azul", variable=var_radio,
                             value="blue", pady=5, command=cambiar_color_fondo)
radio_azul.pack()

# Iniciar el bucle principal de la interfaz gráfica
root.mainloop()

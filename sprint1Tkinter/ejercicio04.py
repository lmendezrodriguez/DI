import tkinter as tk


def mostrar_seleccion():
    """
     Muestra en un label las opciones seleccionadas o no seleccionadas
     en los checkbutton.
     """
    seleccion_leer = var_check_leer.get()
    estado_leer = "Seleccionado" if seleccion_leer else "No seleccionado"

    seleccion_dep = var_check_dep.get()
    estado_dep = "Seleccionado" if seleccion_dep else "No seleccionado"

    seleccion_music = var_check_music.get()
    estado_music = "Seleccionado" if seleccion_music else "No seleccionado"

    etiqueta_aficciones.config(text=f"Leer - {estado_leer} \nDeporte  - "
                                    f"{estado_dep}\nMúsica  - {estado_music}")


# Crear ventana principal
root = tk.Tk()
root.title("Ejemplo de Checkbutton")
root.geometry("300x200")

# Crear etiqueta de menú de Checkbutton
etiqueta_chk_button = tk.Label(root, text="Aficciones", pady=5)
etiqueta_chk_button.pack()

# Checkbuttons y variables para manejarlos
var_check_leer = tk.IntVar()
check_leer = tk.Checkbutton(root, text="Leer", variable=var_check_leer,
                            command=mostrar_seleccion)
check_leer.pack()

var_check_dep = tk.IntVar()
check_dep = tk.Checkbutton(root, text="Deporte", variable=var_check_dep,
                           command=mostrar_seleccion)
check_dep.pack()

var_check_music = tk.IntVar()
check_music = tk.Checkbutton(root, text="Música", variable=var_check_music,
                             command=mostrar_seleccion)
check_music.pack()

# Etiqueta que refleja los Checkbutton seleccionados
etiqueta_aficciones = tk.Label(root, text="", pady=5)
etiqueta_aficciones.pack()

root.mainloop()

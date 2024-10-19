import tkinter as tk


def cambiar_etiqueta():
    """
    Al pulsar el botón 'Mostrar selección', refleja en el texto de
    etiqueta_fruta la fruta seleccionada en la lista.
    """
    seleccion = listbox.curselection()  # Obtener la selección actual
    if seleccion:  # Comprobar si hay una selección
        etiqueta_fruta.config(
            text=f"Fruta seleccionada: {listbox.get(seleccion[0])}"
        )


# Crear ventana principal
root = tk.Tk()
root.title("Ejemplo de Listbox")
root.geometry("300x200")

# Crear etiqueta que indica el menú de frutas
etiqueta_listbox = tk.Label(root, text="Lista de Frutas", pady=5)
etiqueta_listbox.pack()

# Crear lista de frutas
lista_frutas = ['Manzana', 'Banana', 'Naranja']

# Crear Listbox y añadir frutas
listbox = tk.Listbox(root, selectmode=tk.SINGLE, height=3)
for fruta in lista_frutas:
    listbox.insert(tk.END, fruta)
listbox.pack(pady=5)

# Botón para mostrar la selección
boton_seleccion = tk.Button(root, text="Mostrar selección",
                            command=cambiar_etiqueta)
boton_seleccion.pack(pady=5)

# Etiqueta para mostrar la fruta seleccionada
etiqueta_fruta = tk.Label(root, text="Fruta seleccionada: Ninguna")
etiqueta_fruta.pack(pady=5)

# Iniciar el bucle principal de la interfaz gráfica
root.mainloop()

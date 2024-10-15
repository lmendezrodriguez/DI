import tkinter as tk


def cambiar_etiqueta():
    """
     Al pulsar boton_seleccion refleja en el texto de etiqueta_fruta la fruta
     seleccionada en la lista
     """
    seleccion = listbox.curselection()
    etiqueta_fruta.config(
        text=f"Fruta seleccionada: {listbox.get(seleccion[0])}")


# Crear ventana principal
root = tk.Tk()
root.title("Ejemplo de Listbox")
root.geometry("300x200")

# Crear etiqueta de menú de Checkbutton
etiqueta_listbox = tk.Label(root, text="Lista de Frutas", pady=5)
etiqueta_listbox.pack()

# Crear lista de frutas
lista_frutas = ['Manzana', 'Banana', 'Naranja']

# Crear listbox
listbox = tk.Listbox(root, selectmode=tk.SINGLE, height=3)
for fruta in lista_frutas:
    listbox.insert(tk.END, fruta)
listbox.pack(pady=5)

# Botón para mostrar selección
boton_seleccion = tk.Button(root, text="Mostrar selección",
                            command=cambiar_etiqueta)
boton_seleccion.pack(pady=5)

# Etiqueta para mostrar fruta seleccionada
etiqueta_fruta = tk.Label(root, text="Fruta seleccionada: Ninguna")
etiqueta_fruta.pack(pady=5)

root.mainloop()

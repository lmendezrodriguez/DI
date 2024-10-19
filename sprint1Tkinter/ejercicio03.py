import tkinter as tk


def mostrar_label():
    """
    Cambia el texto de etiqueta_saludo mostrando un saludo personalizado.

    Esta función muestra el texto "Hola, {nombre}" en la etiqueta
    etiqueta_saludo tomando el valor introducido por el usuario en la entrada.
    """
    nombre = entrada.get()  # Obtiene el texto ingresado por el usuario
    etiqueta_saludo.config(text=f"Hola, {nombre}!", bg="green")  # Muestra el saludo


# Crear la ventana principal
root = tk.Tk()
root.title("Ejemplo de Entry")
root.geometry("300x200")

# Crear y empaquetar la etiqueta que solicita el nombre
etiqueta_entrada = tk.Label(root, text="Escribe tu nombre!", pady=5)
etiqueta_entrada.pack()

# Crear y empaquetar la etiqueta para mostrar el saludo (inicialmente vacía)
etiqueta_saludo = tk.Label(root, text="", pady=5)
etiqueta_saludo.pack()

# Crear y empaquetar el campo de entrada para que el usuario escriba su nombre
entrada = tk.Entry(root, width=30)
entrada.pack(pady=5)

# Crear y empaquetar el botón que muestra el saludo
boton_entrada = tk.Button(root, text="Saludo!", command=mostrar_label, pady=5)
boton_entrada.pack()

# Iniciar el bucle principal de la interfaz gráfica
root.mainloop()

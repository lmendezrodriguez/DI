import tkinter as tk

def actualizar_valor(val):
    """
    Actuliza el valor de etiqueta según la selección en el Scale
    """
    etiqueta.config(text=f"Valor: {val}")

# Crear la ventana principal
root = tk.Tk()
root.title("Ejemplo de Scale")
root.geometry("300x200")

# Crear Scale
scale = tk.Scale(root, from_=0, to=100, orient="horizontal", command=actualizar_valor)
scale.pack(pady=10)

# Etiqueta para mostrar el valor seleccionado
etiqueta = tk.Label(root, text="Valor: 0")
etiqueta.pack(pady=5)

root.mainloop()
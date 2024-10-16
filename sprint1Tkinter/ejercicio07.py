import tkinter as tk


def dibujar_formas():
    """
    Recoge las coordenadas de los Entry y dibuja un círculo y un rectángulo.

    """
    # Obtener las coordenadas del círculo
    x1_circle = int(entry_x1_circle.get())
    y1_circle = int(entry_y1_circle.get())
    x2_circle = int(entry_x2_circle.get())
    y2_circle = int(entry_y2_circle.get())

    # Obtener las coordenadas del rectángulo
    x1_rect = int(entry_x1_rect.get())
    y1_rect = int(entry_y1_rect.get())
    x2_rect = int(entry_x2_rect.get())
    y2_rect = int(entry_y2_rect.get())

    # Limpiar el Canvas antes de dibujar
    canvas.delete("all")

    # Dibujar el círculo y el rectángulo
    canvas.create_oval(x1_circle, y1_circle, x2_circle, y2_circle,
                       outline="blue", width=2)
    canvas.create_rectangle(x1_rect, y1_rect, x2_rect, y2_rect,
                            outline="red", width=2)


# Crear la ventana principal
root = tk.Tk()
root.title("Ejemplo de Canvas y formas")

# Crear un Canvas donde dibujaremos las formas
canvas = tk.Canvas(root, width=400, height=400, bg="white")
canvas.grid(row=0, column=0, columnspan=4, padx=10, pady=10)

# Etiquetas y campos de entrada para el círculo
tk.Label(root, text="Círculo (x1, y1, x2, y2):").grid(row=1, column=0, padx=5,
                                                      pady=5)
entry_x1_circle = tk.Entry(root)
entry_x1_circle.grid(row=1, column=1)
entry_y1_circle = tk.Entry(root)
entry_y1_circle.grid(row=1, column=2)
entry_x2_circle = tk.Entry(root)
entry_x2_circle.grid(row=1, column=3)
entry_y2_circle = tk.Entry(root)
entry_y2_circle.grid(row=1, column=4)

# Etiquetas y campos de entrada para el rectángulo
tk.Label(root, text="Rectángulo (x1, y1, x2, y2):").grid(row=2, column=0,
                                                         padx=5, pady=5)
entry_x1_rect = tk.Entry(root)
entry_x1_rect.grid(row=2, column=1)
entry_y1_rect = tk.Entry(root)
entry_y1_rect.grid(row=2, column=2)
entry_x2_rect = tk.Entry(root)
entry_x2_rect.grid(row=2, column=3)
entry_y2_rect = tk.Entry(root)
entry_y2_rect.grid(row=2, column=4)

# Botón para dibujar las formas
btn_dibujar = tk.Button(root, text="Dibujar Formas", command=dibujar_formas)
btn_dibujar.grid(row=3, column=0, columnspan=5, pady=10)

# Iniciar el loop principal de la ventana
root.mainloop()

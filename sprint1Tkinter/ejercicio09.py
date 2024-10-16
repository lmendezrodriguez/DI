import tkinter as tk
from tkinter import messagebox


def cerrar_ventana():
    """
    Cierra la ventana principal
    """
    root.destroy()


def abrir_info():
    """
    Abre una ventana emergente con información
    """
    messagebox.showinfo("Información", "Este es un mensaje "
                                       "informativo")


# Configurar ventana principal
root = tk.Tk()
root.title("Ejemplo de uso de menús")
root.geometry("300x200")

# Crear menú principal
menu_principal = tk.Menu(root)
root.config(menu=menu_principal)

# Crear submenú archivo
menu_archivo = tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Archivo", menu=menu_archivo)
menu_archivo.add_cascade(label="Abrir")
menu_archivo.add_cascade(label="Salir", command=cerrar_ventana)

# Crear submenú Ayuda
menu_ayuda = tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Ayuda", menu=menu_ayuda)
menu_ayuda.add_cascade(label="Acerca de", command=abrir_info)

root.mainloop()

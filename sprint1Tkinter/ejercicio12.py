import tkinter as tk
from tkinter import ttk
import random
from tkinter import messagebox


class Usuario:
    """Clase que representa un usuario con nombre, edad y género."""

    def __init__(self, nombre="Thomas", edad=20, genero="H"):
        """Inicializa un nuevo objeto Usuario."""
        self.nombre = nombre
        self.edad = edad
        self.genero = genero

    def __str__(self):
        """Devuelve una representación legible del usuario."""
        return f"{self.nombre} - {self.edad} años - {self.genero}"


def registrar_usuario():
    """Función que registra un nuevo usuario basado en los datos del 
    formulario."""

    # Obtener los valores de los campos
    nombre = entrada_usuario.get()
    edad = escala_edad.get()
    genero = var_genero.get()

    # Verificar que todos los campos están completos
    if not nombre or not edad or not genero:
        messagebox.showwarning(
            "Advertencia",
            "Por favor rellena todos los campos para registrar un "
            "usuario."
        )
        return

    # Confirmar si se desea añadir al usuario
    if messagebox.askyesno(
            "Confirmación",
            f"¿Quieres añadir a {nombre} con edad {edad} y género "
            f"{genero}?"
    ):
        # Crear usuario y añadirlo a la lista
        usuario = Usuario(nombre, int(edad), genero)
        usuarios.insert(0, usuario)
        cargar_lista_usuarios(usuarios)


def crear_lista_usuarios():
    """Función que genera una lista inicial de usuarios de ejemplo."""

    usuarios = []
    nombres = ["Ana", "Carlos", "Sofía", "Miguel", "Lucía", "Juan", "Valeria",
               "Pablo", "Elena", "Javier"]
    generos = ["Masculino", "Femenino", "Otro"]

    # Crear una lista de usuarios aleatorios
    for _ in range(11):
        usuarios.append(
            Usuario(random.choice(nombres), random.randint(18, 90),
                    random.choice(generos)))
    return usuarios


def cargar_lista_usuarios(lista):
    """Carga una lista de usuarios en el widget Listbox."""

    listbox_usuarios.delete(0, tk.END)
    for usuario in lista:
        listbox_usuarios.insert(tk.END, usuario)


def eliminar_usuario():
    """Elimina un usuario seleccionado del Listbox."""

    seleccion = listbox_usuarios.curselection()
    if seleccion:
        indice = seleccion[0]
        usuario_a_eliminar = usuarios[indice]

        # Mostrar confirmación antes de eliminar
        if messagebox.askyesno(
                "Confirmación",
                f"¿Seguro que quieres eliminar a "
                f"{usuario_a_eliminar.nombre}?"
        ):
            usuarios.pop(indice)  # Eliminar de la lista de usuarios
            cargar_lista_usuarios(usuarios)  # Recargar el listbox
    else:
        messagebox.showwarning("Advertencia",
                               "Por favor selecciona un usuario "
                               "para eliminar.")


def msg_guardar_lista():
    """Lanza un mensaje al pulsar el bóton del menú"""
    messagebox.showinfo("Guardar lista", "La lista ha sido "
                                         "guardada.")


def msg_cargar_lista():
    """Carga la lista de usuarios y lanza un mensaje al pulsar el bóton del 
    menú"""
    cargar_lista_usuarios(usuarios)
    messagebox.showinfo("Cargar lista", "La lista ha sido "
                                        ""
                                        "cargada.")

def cerrar_programa():
    """Cierra el programa"""
    root.destroy()

# Crear ventana principal
root = tk.Tk()
root.title("Registro de Usuarios")
root.geometry("400x600")
root.minsize(400, 600)  # Establecer tamaño mínimo

# Crear menú principal
menu_principal = tk.Menu(root)
root.config(menu=menu_principal)

# Crear submenú archivo
menu_lista = tk.Menu(menu_principal, tearoff=0)
menu_principal.add_cascade(label="Lista", menu=menu_lista)
menu_lista.add_cascade(label="Guardar lista", command=msg_guardar_lista)
menu_lista.add_cascade(label="Cargar lista", command=msg_cargar_lista)

# Crear etiqueta para el registro de usuarios
etiqueta_registro = tk.Label(root, text="Registrar usuario",
                             pady=5, font=("Helvetica", 12, "bold"))
etiqueta_registro.pack()

# Crear frame para los datos del usuario
frame_registro = tk.Frame(root, bg="white", bd=2,
                          relief="sunken")
frame_registro.pack(padx=5, pady=5, fill="both", expand=True)

# Crear campo para nombre del usuario
etiqueta_nombre_usuario = tk.Label(frame_registro, text="Nombre usuario:",
                                   bg="white")
etiqueta_nombre_usuario.grid(row=1, column=0, padx=10, pady=10)
entrada_usuario = tk.Entry(frame_registro, width=20, bg="white")
entrada_usuario.grid(row=1, column=1, padx=10, pady=10)

# Crear campo para edad del usuario
etiqueta_edad_usuario = tk.Label(frame_registro, text="Edad usuario:",
                                 bg="white")
etiqueta_edad_usuario.grid(row=2, column=0, padx=10, pady=10)
escala_edad = tk.Scale(frame_registro, from_=0, to=100, orient="horizontal",
                       bg="white")
escala_edad.grid(row=2, column=1, padx=10, pady=10)

# Crear campo para género del usuario
etiqueta_genero_usuario = tk.Label(frame_registro, text="Género usuario:",
                                   bg="white")
etiqueta_genero_usuario.grid(row=3, column=0, padx=5)

# Crear frame para los botones de selección de género
frame_genero = tk.Frame(frame_registro, bg="white")
frame_genero.grid(row=3, column=1)

# Crear Radiobuttons para género del usuario
var_genero = tk.StringVar()
var_genero.set("Femenino")
rbutton_femenino = tk.Radiobutton(frame_genero, text="Femenino",
                                  variable=var_genero, value="Femenino",
                                  pady=5, bg="white")
rbutton_femenino.pack()

rbutton_masculino = tk.Radiobutton(frame_genero, text="Masculino",
                                   variable=var_genero, value="Masculino",
                                   pady=5, bg="white")
rbutton_masculino.pack()

rbutton_otro = tk.Radiobutton(frame_genero, text="Otro",
                              variable=var_genero, value="Otro", pady=5,
                              bg="white")
rbutton_otro.pack()

# Crear botón para registrar usuario
boton_registrar_usuario = tk.Button(root,
                                    text="Registrar usuario",
                                    command=registrar_usuario)
boton_registrar_usuario.pack(pady=5)

# Crear separador para secciones
separator_reg_lista = ttk.Separator(root, orient="horizontal")
separator_reg_lista.pack(fill="x", pady=10)

# Crear etiqueta para la lista de usuarios
etiqueta_lista_usuarios = tk.Label(root, text="Lista de Usuarios",
                                   pady=5, font=("Helvetica", 12, "bold"))
etiqueta_lista_usuarios.pack()

# Crear frame para la lista de usuarios
frame_lista_usuarios = tk.Frame(root, bg="white", bd=2,
                                relief="sunken")
frame_lista_usuarios.pack(padx=10, pady=10, fill="both", expand=True)

# Crear Scrollbar vertical
scroll_vertical = tk.Scrollbar(frame_lista_usuarios, orient="vertical")
scroll_vertical.pack(side="right", fill="y")

# Crear Listbox para la lista de usuarios
listbox_usuarios = tk.Listbox(frame_lista_usuarios, selectmode=tk.SINGLE,
                              font=("Helvetica", 10, "bold"),
                              yscrollcommand=scroll_vertical.set, height=3)
listbox_usuarios.pack(side="left", fill="both", expand=True)
scroll_vertical.config(command=listbox_usuarios.yview)

# Crear botón para eliminar usuario
boton_eliminar_usuario = tk.Button(root, text="Eliminar usuario",
                                   command=eliminar_usuario)
boton_eliminar_usuario.pack(pady=5)

# Crear separador para secciones
separator_lista_cerrar = ttk.Separator(root, orient="horizontal")
separator_lista_cerrar.pack(fill="x", pady=10)

# Crear botón cerrar
button_cerrar = tk.Button(root, text="Cerrar programa", command=cerrar_programa)
button_cerrar.pack()

# Crear y cargar la lista de usuarios
usuarios = crear_lista_usuarios()
cargar_lista_usuarios(usuarios)

root.mainloop()

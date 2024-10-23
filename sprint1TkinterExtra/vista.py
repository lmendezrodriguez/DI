import tkinter as tk

# Vista del juego
class VistaJuego:
    def __init__(self, master, controlador):
        """
        Inicializa la vista del juego.

        Parámetros:
        - master: Ventana principal de la aplicación.
        - controlador: Instancia de ControladorJuego.
        """
        self.master = master
        self.controlador = controlador
        self.master.title("Piedra Papel Tijera")

        # Frame de inicio
        self.frame_inicio = tk.Frame(master)
        self.frame_inicio.pack()

        # Etiqueta de instrucciones
        self.etiqueta = tk.Label(self.frame_inicio, text="Seleccione una opción:")
        self.etiqueta.pack(pady=5)

        # Botones de selección de modo de juego
        self.boton_un_jugador = tk.Button(self.frame_inicio, text="1 Jugador", command=self.iniciar_un_jugador)
        self.boton_un_jugador.pack(pady=5)

        self.boton_dos_jugadores = tk.Button(self.frame_inicio, text="2 Jugadores", command=self.iniciar_dos_jugadores)
        self.boton_dos_jugadores.pack(pady=5)

        self.boton_salir = tk.Button(self.frame_inicio, text="Salir", command=master.quit)
        self.boton_salir.pack(pady=5)

        # Etiqueta para mostrar el resultado
        self.etiqueta_resultado = tk.Label(master, text="", font=('Helvetica', 16))
        self.etiqueta_resultado.pack(pady=10)

        self.contador_rondas = 0  # Contador de rondas
        self.botonera = None  # Frame para los botones de opciones
        self.boton_iniciar = None  # Botón para reiniciar el juego

    def iniciar_un_jugador(self):
        """Inicia el modo de un jugador."""
        self.limpiar_frame_inicio()
        self.crear_boton_opciones(es_dos_jugadores=False)

    def iniciar_dos_jugadores(self):
        """Inicia el modo de dos jugadores."""
        self.limpiar_frame_inicio()
        self.crear_boton_opciones(es_dos_jugadores=True)

    def limpiar_frame_inicio(self):
        """Limpia los widgets del frame de inicio."""
        for widget in self.frame_inicio.winfo_children():
            widget.destroy()

    def crear_boton_opciones(self, es_dos_jugadores):
        """
        Crea botones para las opciones del juego.

        Parámetros:
        - es_dos_jugadores: Indica si es un juego de dos jugadores.
        """
        if self.botonera:
            self.botonera.destroy()  # Destruir el frame de botones anterior

        self.botonera = tk.Frame(self.master)  # Nuevo frame para botones
        self.botonera.pack(pady=10)

        opciones = ['Piedra', 'Papel', 'Tijera']
        for opcion in opciones:
            boton = tk.Button(self.botonera, text=opcion, command=lambda op=opcion: self.hacer_eleccion(op, es_dos_jugadores))
            boton.pack(pady=5)

    def hacer_eleccion(self, eleccion, es_dos_jugadores):
        """
        Maneja la elección del jugador.

        Parámetros:
        - eleccion: Elección del jugador.
        - es_dos_jugadores: Indica si es un juego de dos jugadores.
        """
        self.contador_rondas += 1  # Incrementa el contador de rondas
        if es_dos_jugadores:
            self.etiqueta_resultado.config(text=f"Jugador 1 eligió: {eleccion}. Elija para el Jugador 2:")
            self.crear_boton_opciones_jugador_dos(eleccion)
        else:
            resultado, eleccion_maquina = self.controlador.jugar(eleccion)
            self.etiqueta_resultado.config(text=f"Tu elección: {eleccion}, Elección de la máquina: {eleccion_maquina}. Resultado: {resultado}")
            self.mostrar_puntuaciones()

    def crear_boton_opciones_jugador_dos(self, eleccion_jugador_uno):
        """Crea botones para que el segundo jugador elija."""
        if self.botonera:
            self.botonera.destroy()  # Destruir el frame de botones anterior

        self.botonera = tk.Frame(self.master)  # Nuevo frame para el segundo jugador
        self.botonera.pack(pady=10)

        opciones = ['Piedra', 'Papel', 'Tijera']
        for opcion in opciones:
            boton = tk.Button(self.botonera, text=opcion, command=lambda op=opcion: self.mostrar_resultado(eleccion_jugador_uno, op))
            boton.pack(pady=5)

    def mostrar_resultado(self, eleccion_jugador_uno, eleccion_jugador_dos):
        """
        Muestra el resultado de la partida entre dos jugadores.

        Parámetros:
        - eleccion_jugador_uno: Elección del jugador 1.
        - eleccion_jugador_dos: Elección del jugador 2.
        """
        resultado = self.controlador.jugar(eleccion_jugador_uno, es_dos_jugadores=True, eleccion_jugador_dos=eleccion_jugador_dos)
        self.etiqueta_resultado.config(text=f"Jugador 1 eligió: {eleccion_jugador_uno}, Jugador 2 eligió: {eleccion_jugador_dos}. Resultado: {resultado}")
        self.mostrar_puntuaciones()

    def mostrar_puntuaciones(self):
        """Muestra las puntuaciones actuales de los jugadores."""
        puntuacion = f"Victorias Jugador 1: {self.controlador.modelo.victorias_jugador1}, Victorias Jugador 2: {self.controlador.modelo.victorias_jugador2}"
        self.etiqueta_resultado.config(text=self.etiqueta_resultado.cget("text") + f"\n{puntuacion}")

        # Verifica si alguien ha ganado 3 rondas
        if self.controlador.modelo.victorias_jugador1 >= self.controlador.modelo.max_victorias or \
           self.controlador.modelo.victorias_jugador2 >= self.controlador.modelo.max_victorias:
            resultado_final = self.controlador.modelo.obtener_resultado_final()
            self.etiqueta_resultado.config(text=self.etiqueta_resultado.cget("text") + f"\n{resultado_final}")
            self.reiniciar_juego()

    def reiniciar_juego(self):
        """Reinicia el juego y resetea la vista."""
        self.controlador.modelo.reiniciar_victorias()
        self.contador_rondas = 0  # Reinicia el contador de rondas
        self.botonera.destroy()  # Destruir los botones de opciones
        self.boton_iniciar = tk.Button(self.master, text="Jugar de nuevo", command=self.iniciar_un_jugador)
        self.boton_iniciar.pack(pady=5)  # Añadir un padding vertical

import tkinter as tk
from controlador import GameController
from vista import MainMenu

if __name__ == "__main__":
    root = tk.Tk()
    controller = GameController(root)
    root.mainloop()
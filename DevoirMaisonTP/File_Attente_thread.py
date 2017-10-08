from random import *
from threading import Thread
from time import sleep
from queue import Queue


class FileAttente():
    def __init__(self, labelfileatt):
        self.q = Queue(10)
        self.labelFA = labelfileatt
        self.liste= []



    def enfiler(self, nb):
        if(not self.q.full()):
            self.q.put(nb)
            self.liste.append(nb)


        self.labelFA.config(text = self.liste)


    def defiler(self):
        if(not self.q.empty()):
            self.liste.remove(self.liste[0])
            self.q.task_done()
        return self.q.get()


class Producteur(Thread):
    def __init__(self, waiting, file, labelproduc, posprod):
        Thread.__init__(self)
        self.labelP = labelproduc
        self.wait=waiting
        self.file_att= file
        self.posP = posprod

    def run(self):
        while(1):
            rand = randint(0,100)
            self.file_att.enfiler(rand)
            texteproduc = "Produc " + str(self.posP) + ": ajout de l'entier " + str(rand)
            self.labelP.config(text=texteproduc)
            sleep(self.wait)

class Consommateur(Thread):
    def __init__(self, waiting, file, labelconso, posconso):
        Thread.__init__(self)
        self.wait=waiting
        self.file_att = file
        self.labelC = labelconso
        self.posC = posconso

    def run(self):
        while(1):
            texteconso = "Conso " + str(self.posC) + ": retrait de l'entier " + str(self.file_att.defiler())
            self.labelC.config(text=texteconso)
            sleep(self.wait)


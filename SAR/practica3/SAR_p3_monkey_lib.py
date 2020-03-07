# 3.- Mono Library

import pickle
import random
import re
import sys

## Nombres: 

########################################################################
########################################################################
###                                                                  ###
###  Todos los métodos y funciones que se añadan deben documentarse  ###
###                                                                  ###
########################################################################
########################################################################
'''
Que es una frase?:
las frases vienen delimitadas por un punto, punto y coma, dos retornos de carro "\n\n", una vez tiene es
la frase se pasara a minuscula, los simbolos que no sean alfanumericos. cada nuo de esos tokens son los que serán añadidos, 
añadira un dolar al principio y al final para saber a donde indexar cada frase etc.

Egg and Bacon
$egg and bacon$

$ egg: 1
egg > and: 1
and > bacon:1

E ir añadiendo asi las palabras.

para cada palabra tengo una lista para cuantas veces la vi, y despues al final una lista 

'''


class Monkey():

    def __init__(self):
        self.r1 = re.compile('[.;?!]')
        self.r2 = re.compile('\W+')


    def index_sentence(self, sentence, tri):
        #############
        # COMPLETAR #
        #############
        pass

    def sort_index(self, d):
        for k in d:
            l = sorted(((y, x) for x, y in d[k].items()), reverse=True)
            d[k] = (sum(x for x, _ in l), l)


    def compute_index(self, filename, tri):
        self.index = {
                        'name': filename, 
                        "bi": {}
                    }
        if tri:
            self.index["tri"] = {}
        raw_sentence = ""
        #############
        # COMPLETAR #
        #############

        # TOKENIZACION
        # ================================================================
        texto = []

        # Creo en archivo entero
        f = open(filename,"r").readlines()

        # Paso a string para usar el regex y lo paso a minusculas
        f = ' '.join(f).lower()

        # Dobles saltos que separan frases así como los simbolos, son eliminados
        f = self.r1.split(f.replace('\n\n', '.'))

        # Añado un dolar a cada una de las frases y elimino simbolos raros
        for sentence in f:
            texto.append('$ '+ re.sub(self.r2,' ',sentence).strip() +' $')

        # CREADOR DE INDICES BI
        # ================================================================
        # Recorro las frases y relleno el diccionario con ellas con bi gramas
        for t in texto:
            token = t.split()
            # Recorro el token y su siguiente vecino
            for i in range(len(token) - 1):
                # Compruebo si existe el termino, creandolo en caso contrario
                if self.index['bi'].get(token[i]) == None:
                    self.index['bi'][token[i]] = {}

                encontrado = False
                # Actualizo las stats internas del dicho termino      
                for term,freq in self.index['bi'][token[i]].items():
                    # Compruebo si exite el siguiente y actualizo su frequencia
                    if term.strip() == token[i+1].strip() and not encontrado:
                        self.index['bi'][token[i]][term] = freq + 1
                        encontrado = True
                        break
                if not encontrado:
                    self.index['bi'][token[i]][token[i+1]] = 1                      

        
        self.sort_index(self.index['bi'])
        if tri:
            self.sort_index(self.index['tri'])
        

    def load_index(self, filename):
        with open(filename, "rb") as fh:
            self.index = pickle.load(fh)


    def save_index(self, filename):
        with open(filename, "wb") as fh:
            pickle.dump(self.index, fh)


    def save_info(self, filename):
        with open(filename, "w") as fh:
            print("#" * 20, file=fh)
            print("#" + "INFO".center(18) + "#", file=fh)
            print("#" * 20, file=fh)
            print("filename: '%s'\n" % self.index['name'], file=fh)
            print("#" * 20, file=fh)
            print("#" + "BIGRAMS".center(18) + "#", file=fh)
            print("#" * 20, file=fh)
            for word in sorted(self.index['bi'].keys()):
                wl = self.index['bi'][word]
                print("%s\t=>\t%d\t=>\t%s" % (word, wl[0], ' '.join(["%s:%s" % (x[1], x[0]) for x in wl[1]])), file=fh)
            if 'tri' in self.index:
                print(file=fh)
                print("#" * 20, file=fh)
                print("#" + "TRIGRAMS".center(18) + "#", file=fh)
                print("#" * 20, file=fh)
                for word in sorted(self.index['tri'].keys()):
                    wl = self.index['tri'][word]
                    print("%s\t=>\t%d\t=>\t%s" % (word, wl[0], ' '.join(["%s:%s" % (x[1], x[0]) for x in wl[1]])), file=fh)


    def generate_sentences(self, n=10):
        #############
        # COMPLETAR #
        #############
        
        rrrrerpass


if __name__ == "__main__":
    print("Este fichero es una librería, no se puede ejecutar directamente")



# 3.- Mono Library

import pickle
import random as rd
import re
import sys

## Nombres: 
#   Vicente Fructuoso Chofré
#   Vladyslav Mazurkevych

########################################################################
########################################################################
###                                                                  ###
###  Todos los métodos y funciones que se añadan deben documentarse  ###
###                                                                  ###
########################################################################
########################################################################



class Monkey():

    def __init__(self):
        self.r1 = re.compile('[.;?!]')
        self.r2 = re.compile('\W+')


    def index_sentence(self, sentence, tri):
        #############
        # COMPLETAR #
        #############
        token = sentence.split()
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
        # Recorro el token y su siguiente vecino
        if tri:
            for i in range(len(token) - 2):
                # Compruebo si existe el termino, creandolo en caso contrario
                if self.index['tri'].get((token[i],token[i + 1])) == None:
                    self.index['tri'][(token[i],token[i + 1])] = {}

                encontrado = False
                # Actualizo las stats internas del dicho termino      
                for term,freq in self.index['tri'][(token[i],token[i + 1])].items():
                    # Compruebo si exite el siguiente y actualizo su frequencia
                    if term.strip() == token[i+2].strip() and not encontrado:
                        self.index['tri'][(token[i],token[i + 1])][term] = freq + 1
                        encontrado = True
                        break
                if not encontrado:
                    self.index['tri'][(token[i],token[i + 1])][token[i+2]] = 1
       

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
        try:
            f = open(filename,"r",encoding="utf8").readlines()
        except:
            print('Error al abrir el fichero, revise si existe...')
            return

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
            self.index_sentence(sentence = t, tri= tri)            

        
        self.sort_index(self.index['bi'])
        if tri:
            self.sort_index(self.index['tri'])
        

    def load_index(self, filename):
        try:
            with open(filename, "rb") as fh:
                self.index = pickle.load(fh)
        except:
            print('Error al cargar el indice...')
            exit(0)


    def save_index(self, filename):
        try:
            with open(filename, "wb") as fh:
                pickle.dump(self.index, fh)
        except:
            print('Error al guardar el indice...')
            exit(0)

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


    def seleccionar(self, lista):
        """
        Este método devuelve el elemento de la lista que es elegido 
        mediante una ponderación obtenida con un algoritmo genético.


        :param 
            lista: lista de tuplas de elementos entre los cuales elegir de tipo (aparicion, elemento)

        :return: Elemento elegido
        """
        # Obtengo las apariciones totales y relativas
        apariciones, lista = [a for a,j in lista], [j for a,j in lista]        
        p_total = float(sum(apariciones))

        p_relativo = [w/p_total for w in apariciones]
        # Probabilidades para cada elemento
        probabilidades = [sum(p_relativo[:i+1]) for i in range(len(p_relativo))]

        slot = rd.random()
        for (i, elemento) in enumerate([j for j in lista]):
            if slot <= probabilidades[i]:
                break

        return elemento


    def generate_sentences(self, n=10):
        #############
        # COMPLETAR #
        #############
        
        # GENERACIÓN DE FRASES
        # ================================================================
        PALMAX = 25

        if self.index.get("tri"):
            for i, j in self.index['tri']:
                break            
            elemento = self.seleccionar(self.index['tri'][('$',j)][1])
            #print(elemento)
            # Sigo buscando
            for lin in range(n):
                texto = ''
                for i in range(PALMAX):
                    ele1 = self.seleccionar(self.index['tri'][(j,elemento)][1])
                    j = elemento
                    elemento = ele1
                    if ele1 is not '$':
                        texto += ' '+ ele1
                    else:
                        for i, j in self.index['tri']:
                            break 
                        elemento = self.seleccionar(self.index['tri'][('$',j)][1])
                        break
                print(texto)
        else:
            # Empiezo por $ para eguir con las parabras
            elemento = self.seleccionar(self.index['bi']['$'][1])

            # Sigo buscando
            for lin in range(n):
                texto = ''
                for i in range(PALMAX): 
                    elemento = self.seleccionar(self.index['bi'][elemento][1])
                    if elemento is not '$':
                        texto += ' '+ elemento
                    else:
                        break
                print(texto)

if __name__ == "__main__":
    print("Este fichero es una librería, no se puede ejecutar directamente")



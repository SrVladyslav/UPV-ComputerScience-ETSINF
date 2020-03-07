# 3.- Mono Evolved

import pickle
import random
import sys
from SAR_p3_monkey_lib import Monkey

print('')
'''
    recibira un argumento, un indice de un fichero binario
    Si no digo nada, generara 10 frases al estilo de ese indice a no ser que se lo especifique 

    Cuanto mas peuqeño sea el fichero, mas facil será que se repitan las clases.

    Siempre empezamos por dolar para empezar una frase. Las elegiremos por probabilidades.
    La libreria pepinillo (pickle), permite leer y escribir ficheros binarios en pytjon


    Fichero binario te tiene guardados los indices, y no hace falta cada vez cargar las cosas

    def save_object(obj, filename):
        with open(file_name, 'wb') as fh:
            pickle.dump(obj, fh)
    def load_object(filename):
        with open(file_name, 'rb') as fh:
            obj = pickle.load(fh) 
        return obj

    import random
    random.randint(a, b)
    "Return a random integer N such that a <= N <= b."
    random.choice(seq)
    "Return a random element from the non-empty sequence seq. If seq is empty, raises IndexError."

    No tiene una aplicacion directa para nuestra practica, pero elige con una probabilidad equidistante.

    .index son ficheros parecidos
    .info tiene la informacion necesaria para generar
'''
if __name__ == "__main__":

    if len(sys.argv) not in (2, 3):
        print("python %s indexfile [n]" % sys.argv[0])
        sys.exit(-1)

    index_filename = sys.argv[1]
    try:
        n = int(sys.argv[2])
    except:
        n = 10

    m = Monkey()
    m.load_index(index_filename)
    m.generate_sentences(n)

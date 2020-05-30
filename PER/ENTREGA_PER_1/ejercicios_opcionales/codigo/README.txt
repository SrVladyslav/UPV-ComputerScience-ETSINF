Modificaciones respecto al código original:

> pcaEXPERIMENT.m correspone a pca+knn-exp.m
> pcaTESTING.m corresponde a pca+knn-eva.m 

Hice estas modifiaciones porque el script original no me lo reconocía en Windows, 
incluso poniendo el PATH al binario de Octave. Por ello el script lo transforme en una función 
la cual la llame desde Octave, previamente cargando los datasets con 'load()'. Por lo demás, no
han habido más cambios.

En el pcaEXPERIMENT y pcaTESTING están las implementaciones de código para el uso con y sin Wilson, 
solo hay que descomentar el código necesario.

En el KNN.m están comentadas las distintas distancias, para usarlas, hay que descomentar la necesaria. 
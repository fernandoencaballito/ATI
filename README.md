# ATI

### Integrantes:
* Lucas Soncini.
* Fernando Bejarano.

### Profesora:
* Juliana Gambini.

## TP0

### Requisitos
* Para que se puedan abrir archivos con formato pgm y ppm, se necesita tener instalado "ImageMagick".

### Estado de los requisitos
* 1-a: listo
* 1-b:listo
* swap:listo
### Notas
* 1-e)Copiar parte de la imagen en otra imagen nueva:
Primero seleccionar el area, luego presionar copiar, luego presionar pegar. Se pega lo copiado en la ventana de imagen modificada, se borra lo que habia en modificado antes. No hace falta tener que mover el área copiada.

* 7)Para detectar que la imagen es blanco y negro: BufferedImage.getType() == BufferedImage.TYPE_BYTE_GRAY

* 8)Para detectar imagen en color (RGB):
BufferedImage.getType() == BufferedImage.TYPE_INT_RGB

* Botones del menu: FlowLayout (LayoutManager)
new FlowLayout(FlowLayout.LEFT).//esto es para que esten alineados a la izquierda.
Otra opción es usar JMenuBar y elementos del tipo JMenu. Cada JMenu puede contener items desplegables (JMenuItem).
* Componentes del contentPanel: BoxLayout (LayoutManager).
Box.createHorizontalBox()//caja horizontal 
* para trabajar con imagenes comprimido: ImageIO.


### TP1

#### 7)Aplicar la ecualización del histograma por segunda vez a la misma imagen. Observar el resultado y dar una explicación de lo sucedido.

 No hay cambios, se obtiene la misma imagen. El histograma de la primera ecuacion y el de la segunda son iguales. Esto pasa porque la segunda vez se produce una distribución de gris uniforme de una distribución que ya es uniforme.

 
 Esta bien. Si restas la imagen de la primera aplicación con la segunda, quedan algunos valores en el histograma; si mostras lo que queda se ve todo negro, pero igual hay alguna diferencia en el histograma.
 
#### 13a) imágenes contaminadas con Ruido Gaussiano aditivo para varios valores de σ y μ = 0.
 * Suavizado con el filtro de la media:casi no se nota el ruido, pero se ve borrosa.
 * Suavizado con el filtro de la mediana:elimino el ruido, es apenas borrosa. Igual es mucho mas definido que la anterior.
 * Suavizado con el filtro de Gauss para diferentes valores de σ y μ = 0:arregla ruido pero borronea.
 * Realce de Bordes:no sirve para sacar ruido,remarca bordes.

#### 13b) imágenes contaminadas con Ruido Rayleigh multiplicativo para varios valores de ξ.
 * Suavizado con el filtro de la media:deja manchones mas grandes y muy borroso.
 * Suavizado con el filtro de la mediana:borro ruido y nos es borrosa.
 * Suavizado con el filtro de Gauss para diferentes valores de σ y μ = 0: no arregla y deja borroso (parecido a media)
 * Realce de Bordes.
 
#### 14) Contaminar con ruido Sal y Pimienta con diferentes densidades y aplicarle el filtro de la media y de la mediana.
 La mediana quita el ruido siempre que se use mascara tamaño mayor o igual a 5.
 La media no quita este ruido, quedan manchones y se ve borroso.

###TP2

#### 2) Detectores de borde para imagenes con ruido
 * Sal y pimienta: con sobel y con prewitt deja circulitos por todos lados. No sirve, anda pesimo.
 * Gaussiano: bastante mejor, no se notan tanto los manchones, pero aunque umbralicemos se siguen viendo.Esta pasable
 * Raleigh: sobel y prewit tienen circulos. Igual los bordes estan bien. Esta pasable.
 
#### 3) Prewit y sobel a color( sin ruido)
Marca bien los bordes. Los detecta mejor cuando hay mucho contraste entre el borde y el fondo.
Marca un borde por banda en nuestra implementación.

#### 4) Eliminar primero el ruido en las imagenes del punto anterior con los filtros de la media,mediana, Gaussiano y luego aplicar los detectores de borde.
 [Se usa mascara Prewit, y tambien la de Sobel en todos los casos]
 
 Arregla imagen con la filtro de la media.
 Imagen con ruido:
 * Salt & peper:quedan mucho mas gruesos los bordes y quedan manchones
 * Gausiano: quedan mas gruesos los bordes.
 * Rayleigh: muchos bordes falsos. Da cualquier cosa. 
 
 Arregla imagen con filto de la mediana
 * Salt and peper: se detecta casi perfecto los bordes. Aparecen muy poco circulos. Algunos bordes falsos los borra.
 * Gausiano: aparecen falsas arrugas en toda la imagen (parece un mapa topografico). Un poco peor la definicion de bordes. 
 * Rayleigh: bordes gruesos, bordes los vibran en formas extrañas.
 
 
 Arregla imagen con filtro gausiano (deja muchos manchones)
 *Salta and peper: quedan muchos bordes falsos.Bordes un poco mas grusos.
 *ruido gauss: aparecen arrugas. Destaca bordes secundarios.
 *Rayleigh:obtiene bordes de todo, incluye bordes de cosas borrosas en la imagen original 
 
 
 
#### 5)
a)Mascara sin nombre
Imagen de Lena| cameraman: 
	* Ruido Sal y pimienta: detacta mas o menos los bordes. Aparecen los circulos.| muy marcados los puntos de ruido.
	* Ruido gauss: detecta bordes, aunque queda ciertos puntos por el ruido.|se siguen viendo los puntos del ruido pero apenas
	
b) Mascara Kirsh | cameraman.
Imagen de Lena:
	* Ruido Sal y pimienta: quedan los puntos del ruido.| b
	* Ruido gauss: quedan un poco los puntos del ruido, los bordes estan bien.|
	
c) Mascara Prewitt | cameraman.
En general, deja la imagen mas oscura (incluso sin ruido)
Imagen de Lena | cameraman:
	* Ruido Sal y pimienta: quedan los puntos del ruido |
	* Ruido gauss:  aparecen bordes falsos (como "pelos" sobre la superficie) | 



d) Mascara Sobel | cameraman.
En general, deja la imagen mas oscura (incluso sin ruido)
Imagen de Lena:
	* Ruido Sal y pimienta: quedan los puntos del ruido. |
	* Ruido gauss:  aparecen bordes falsos (como "pelos" sobre la superficie) | 
	
	
#### 6) 
a) Laplaciano con cruces por cero
Lena
* sin ruido: muchos bordes falsos.
* ruido sal y pimienta: no se ven los bordes. Parece una imagen aleatoria.
* ruido gauss: muchos bordes falsos. Se distinguen poco los bordes verdaderos.

Cameraman
* original: mucho borde falsos, algo se ve.
* ruido sal y pimienta: se ve peor.
* gausiano: se ve peor que sal y pimienta. Peor que la original.No se ve casi nada.

b) Laplaciano con pendiente 

Lena
* original: al aumentar el umbral, se van desapareciendo bordes falsos (esto hace que se pierdan algunas lineas)
* salt and peper: casi no se distinguen los bordes
* ruido gausiano: casi no se distingue el ruido.n

Cameraman:
* original sin ruido : segun el umbral, muy pocos bordes falsos.* salt and peper: dificilmente se distinguen los bordes.
* ruido gauss: bastante bien.

c) LoG (doble borde por el filtro gausiano, este filtro corre los bordes). BASTANTE RESISTENTE AL RUIDO!!

Cameraman(sigma=1, mascara de 5x5)
* Original: se ven dobles los bordes.
* ruido gausiano: mas pegados los bordes doble.
* salt & pepper: se  ve bastante bien la silueta.

Lena (sigma=2, mascara 13)
* Original: se ve bien, hay bordes dobles.
* Ruido gauss: igual, pero aparecen los puntos del ruido.
* Ruido Salt and pepper: se ven los bordes, pero muchos bordes falsos.Se distingue Lena.


#### 7) Difusión isotropica y anisotropica

Lena Sal y pimienta
--------------------

* Isotropica: deja manchones pero bastante bien.
* Anisotropica -leclerc: saco arrugas y puntos negros del ruido (quedaron todos los negros).
* Anisotropica - lorentziano: saco arrugas y casi todos los puntos negros del ruido (quedaron los blancos).
* filtro mediana: limpia casi todo el ruido ruido	
* filtro gauss: quedan manchones.

Lena ruido gauss
-----------------------
* Anisotropico- lorenziano: bien definidos los bordes. Algunos puntos del ruido quedan con manchones leves.
* Anisotropico -leclerc: se ven mejor los bordes. Borronea las demas superficies.
* Isotropica:  se ve mas borrosa toda la imagen, incluso los bodes.


#### 8) 
Isotropica se ve borroso y se agrandan los bordes.

##### 9)Umbralizacion global y Otsu.
* Cameraman original, Lena original: se ven iguales 
* Lena sal y pimienta: un poco mejor con Otsu.Se ve un poco mas claro en Otsu.
* Lena ruido gauss: mejor en Otsu, mas detalles.


### TP3

#### Harris.
a)Imagen test.
No detecta la barra de la izquierda con tonos de gris.Le cuesta detectar correctamente esquinas con poco contraste.

Con estos valores va mejor SIZE15/7, SIGMA 3/2, PORCENTAJE THRESHOLD : 60, mascara prewitt


b)Imagen test contaminada (ruido gausiano con sigma 20).
Detecta igual que antes. Incluso con el ruido, comienza a encontrar puntos de la banda izquierda.

c)Pares de imagenes:
* Rotacion: funciona igual. 
* Traslación: anda igual en el nuevo lugar 
* Cambio de iluminación: funciona bastante bien
* Cambio de escala:(prueba_escala.png) falla cuando en la original tenía esquinas redondeas y la escalada la toma con esquinas (eran bordes redondeados también). 
* Movimiento de perspectiva: funciona bien.


#### Susan
a)Imagen test.
Funciona bien

b)Imagen test contaminada.
Para umbral mediano (39) anda bastante bien.
Para umbral muy bajo (menor a 10) aparecen bordes falsos y esquinas falsas (son los puntos de ruido).

c)Pares de imagenes:

* Rotacion: 
45 grados pasa, pero se vuelven mas gruesos los bordes  aparecen esquinas falsas, faltan algunas esquinas (angulos obtusos).
90 grados perfecto, igual a la original. 
* Traslación: perfecto.
* Cambio de iluminación: con mucha iluminacion detecta las sombras como bordes, cuando hay poca iluminación se pierden bordes porque hay  poco contraste. Le cuestan las zonas oscuras con poca luz.
* Cambio de escala:no se ve afectado.
 
* Movimiento de perspectiva: funciona correctamente.

### SIFT (usando http://imagej.net/Fiji/Downloads)
 a)Pares de imagenes:
* Rotacion: 
45 grados funciona bien.
90grados funciona bien.

* Traslación:bien
* Cambio de iluminación: bien. Reconoce pocos keypoints en zonas con poco contraste.
* Cambio de escala:funciona. (usar "expected transformation" -> "similarity").  
* Movimiento de perspectiva:
No funciona con el arco del triunfo. Si con Adam y con 

b)Ruido a la otra imagen (no la original):
* Rotacion: sin problemas.
* Traslación:sin problemas.
* Cambio de iluminación:bien aunque baja la cantidad de keypoints que coinciden( de 142 a  109) para las imagenes del campus.
* Cambio de escala: sin problemas. 
* Movimiento de perspectiva: sin problemas.



b)Ruido ambas imagenes (la original y la otra):
Casi sin problemas, baja un poco la cantidad de keypoints que coinciden.
* Rotacion: bien
* Traslación: bien
* Cambio de iluminación: perfecto
* Cambio de escala: sin problemas
* Movimiento de perspectiva: sin problemas


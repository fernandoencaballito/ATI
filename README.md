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

 
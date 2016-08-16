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


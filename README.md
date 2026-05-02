Si al clonar el repositorio no se encuentran los archivos generados del analizador léxico y sintáctico, es necesario generarlos manualmente utilizando Maven.

Ejecuta los siguientes comandos en la raíz del proyecto:
mvn jflex:generate
mvn cup:generate
Estos comandos se encargarán de:

Generar el analizador léxico a partir del archivo .flex
Generar el parser sintáctico a partir del archivo .cup
Es importante ejecutar estos comandos antes de compilar o ejecutar el proyecto para evitar errores por clases faltantes.

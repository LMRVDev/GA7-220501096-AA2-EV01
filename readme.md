SERVICIO NACIONAL DE APRENDIZAJE 

ANÁLISIS Y DESARROLLO DE SOFTWARE (2758333)

PRESENTADO POR: LUIS MIGUEL RODRIGUEZ VARGAS

INSTRUCTOR: ING. FERNANDO FORERO GOMEZ

Evidencia de desempeño: GA7-220501096-AA2-EV01 codificación de módulos del software según requerimientos del proyecto

Teniendo en cuenta las características del software a desarrollar realizar la codificación del módulo del proyecto realizando conexiones con bases de datos por medio de JDBC tomando como referencia lo visto en el componente formativo “Construcción de aplicaciones con JAVA”. 

_________
Creacion del proyecto Spring Boot https://start.spring.io/
![Creacion del proyecto Spring Boot https://start.spring.io/](public/Seleccion-de-dependencias-SpringBoot.png)

Al iniciar un proyecto de Java con Spring se deben seleccionar las dependencias a utilizar, en este caso se importan las siguientes: 

-Lombok: librería para reducir código redundante (métodos getters, setters, constructores, etc.)

-Spring Web: Permite el desarrollo de aplicaciones web con la incorporación de un servidor Tomcat. 

-Spring Data JPA: Proporciona una capa de abstracción entre el usuario y la base de datos, facilitando la creación de aplicaciones que interactúan con una base de datos relational.

-Spring Boot DevTools: Proporciona herramientas para optimizar el desarrollo en ambientes locales
-MySQL Driver: Permite la conexión con MySQL.

-------
Estructura de proyecto
![Estructura de proyecto](public/Estructura-de-proyecto.png)

Al programar con el modelo de vista-controlador el proyecto se suele estructurar en las siguientes carpetas:

-Models: Representan los objetos de negocio o entidades de la aplicación.

-Repositories: Interactúan con la capa de datos de la aplicación.

-Services: implementan la lógica de negocio de la aplicación.

-Controllers: manejan las solicitudes HTTP y devuelven respuestas.


-------
Archivo application properties
![archivo application properties](public/archivo-application-properties.png)

El archivo aplication.properties es generado automaticamente en el folder src/main/resources y se utiliza para configurar las propiedades del proyecto, en este caso se configura la conexión a base de datos (JDBC- Java Database Connectivity) y la api JPA (Java Persistence API) para convertir objetos Java en registros de base de datos y viceversa.


____
Creacion de base de datos
![Creacion de base de datos](public/crm-db.png)

Antes de ejecutar el proyecto se deben crear la base de datos que se haya especificado en el archivo .properties, en este caso “crm-db”.




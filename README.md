# Instrucciones para Ejecutar el Proyecto

## Requisitos Previos

- Java 11: Asegúrate de tener Java 11 instalado en tu sistema. Puedes descargarlo desde Oracle JDK 11 o usar una distribución de OpenJDK como AdoptOpenJDK.

- Gradle: Asegúrate de tener Gradle instalado. Si no lo tienes, puedes instalarlo siguiendo las instrucciones en Gradle Installation.

## Pasos para Ejecutar el Proyecto

1. Clonar el Repositorio: Clona el repositorio del proyecto a tu máquina local.

   ```bash
   url --> https://github.com/zakur777/msusers.git
   git clone <URL_DEL_REPOSITORIO>
   cd <NOMBRE_DEL_PROYECTO>
   ```

2. Configurar el Entorno: Asegúrate de tener configurado Gradle en tu entorno. Si no lo tienes, puedes instalarlo siguiendo las instrucciones en Gradle Installation.

3. Compilar el Proyecto: Usa Gradle para compilar el proyecto. Desde la raíz del proyecto, ejecuta:

   ```bash
   ./gradlew build
   ```

4. Ejecutar el Proyecto: Una vez compilado, puedes ejecutar el proyecto usando Spring Boot. Ejecuta el siguiente comando:

   ```bash
   ./gradlew bootRun
   ```

5. Acceder a la Aplicación: La aplicación estará disponible en http://localhost:8080/api. Puedes acceder a los endpoints configurados, como:

   `/api/users/login y /api/users/signup.`

6. Acceder a la Consola H2: Si necesitas acceder a la consola H2 para ver la base de datos en memoria, puedes hacerlo en http://localhost:8080/api/h2-console. Usa las siguientes credenciales configuradas en application.properties:

## Resumen de Archivos y Configuraciones Relevantes

- Versión de Java: 11 (configurada en build.gradle)
- Archivo de Configuración de Gradle: build.gradle
- Archivo de Configuración de Spring Boot: application.properties
- Clase Principal de la Aplicación: UsersApplication.java

Si sigues estos pasos, deberías poder ejecutar el proyecto sin problemas.

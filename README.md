# TeamCity

## 1. Introducción

TeamCity es un servidor de integración y entrega continuas (CI/CD) desarrollado por JetBrains. Ofrece una solución empresarial robusta y altamente configurable para **automatizar la compilación, prueba y despliegue de aplicaciones**. Su arquitectura de servidor-agente permite distribuir la carga de trabajo y escalar fácilmente, siendo compatible con múltiples plataformas y lenguajes de programación.

## 2. Características DevOps, CI/CD e IaC

TeamCity facilita la transformación digital y la cultura DevOps mediante:

### CI/CD

- **Pipelines como código**: Definición de pipelines mediante ficheros Kotlin DSL versionados en el repositorio (.teamcity/settings.kts), garantizando trazabilidad y control de versiones.
- **Automatización completa**: Integración con sistemas de control de versiones (Git, SVN, Mercurial) para disparar builds automáticamente ante cambios en el código.
- **Múltiples etapas**: Soporte para pipelines complejos con dependencias entre etapas, permitiendo flujos de compilación, prueba y despliegue secuenciales.
- **Gestión de credenciales**: Sistema seguro de variables de entorno y secretos para proteger credenciales durante el despliegue de infraestructura.

### DevOps e IaC

- **Integración con herramientas de IaC**: Compatible con Terraform, Ansible, CloudFormation y otras herramientas para automatizar la gestión de infraestructura.
- **Visibilidad y trazabilidad**: Panel de control centralizado que muestra el estado de todos los builds, logs detallados y artefactos generados.
- **Informes y métricas**: Estadísticas sobre éxito/fallos de builds, cobertura de código y tendencias de calidad para mejorar continuamente.

## 3. Despliegue de la Herramienta (Docker)

El despliegue de TeamCity en este proyecto utiliza **Docker Compose** con un servidor central y agentes auto-hospedados. El servidor TeamCity gestiona la orquestación mientras que los agentes ejecutan las tareas definidas en los pipelines.

### Prerequisitos

- Docker y Docker Compose instalados en tu sistema.
- Puerto 8111 disponible en tu máquina local (puerto por defecto de TeamCity).

### Instrucciones de despliegue

El fichero de definición se encuentra en `docker-compose.yml`.

1. Navega a la carpeta del proyecto:
   ```bash
   cd path/to/vsfinal
   ```

2. Ejecutar el despliegue:
   ```bash
   docker-compose up -d
   ```

3. Espera a que el servidor TeamCity se inicialice completamente (puede tardar 1-2 minutos).

### Acceder a TeamCity

- **Interfaz web**: http://localhost:8111
- **Primera configuración**: Al acceder por primera vez, se abrirá el asistente de instalación inicial.
- **Licencia**: TeamCity incluye una licencia de demostración para desarrollo local.

### Configuración de claves SSH

Para configurar la autenticación con claves SSH, sigue estos pasos:

1. **Generar un par de claves SSH** en tu máquina local:
   ```bash
   ssh-keygen -t rsa -b 4096
   ```
   
2. **Configurar la clave pública en GitHub**:
   - Copia el contenido de la clave pública (generalmente en `~/.ssh/id_rsa.pub`)
   - Ve a tu repositorio en GitHub: **Settings** → **Deploy keys** → **Add deploy key**
   - Pega la clave pública y guárdala

3. **Configurar la clave privada en TeamCity**:
   - La clave privada se guardará para usarla cuando inicie el servidor
   - Al arrancar el servidor por primera vez, deberás proporcionar la clave privada.

4. **Primera autorización del agente**:
   - La primera vez que se arranca el servidor, TeamCity solicitará autorizar el agente
   - Aprueba la solicitud para que el agente pueda conectarse y ejecutar los builds

## 4. Ejemplo de Pipeline

Se incluye un ejemplo de definición de pipeline en el fichero `.teamcity/settings.kts`. Este fichero Kotlin DSL define un pipeline con 3 etapas:

1. **Compile Project**: Simula la compilación del código.
2. **Run Unit Tests**: Ejecuta las pruebas unitarias.
3. **Deploy to Staging**: Simula el despliegue a un entorno de pruebas.

### Cómo ejecutar el pipeline

1. **Desplegar TeamCity** siguiendo las instrucciones anteriores.

2. **Acceder a la interfaz web**:
   ```
   http://localhost:8111
   ```

3. **Configurar la conexión al repositorio**:
   - En el menú principal, ir a **Administration** → **Version Control** → **VCS Roots**.
   - Crear una nueva raíz VCS y configurar la URL de tu repositorio Git.

4. **Crear un proyecto**:
   - Ir a **Administration** → **Projects**.
   - Seleccionar **Create Project**.
   - Asociar el proyecto al VCS Root configurado anteriormente.

5. **Importar la configuración del pipeline**:
   - TeamCity detectará automáticamente la carpeta `.teamcity/settings.kts` y sincronizará la configuración del proyecto desde el repositorio.
   - Si no se importa automáticamente, ir a **Project Settings** → **Version Control Settings** y vincular el repositorio.

6. **Ejecutar el pipeline**:
   - Ir a la página principal del proyecto y seleccionar la configuración de build "CI/CD Pipeline Example".
   - Hacer clic en **Run** para ejecutar manualmente, o esperar a que se dispare automáticamente al hacer un push al repositorio.

7. **Monitorizar la ejecución**:
   - Observar el progreso de cada etapa en la interfaz web.
   - Revisar los logs detallados de cada paso en el panel de detalles del build.

### Personalizar el pipeline

Para modificar el pipeline, edita el fichero `.teamcity/settings.kts`:

```kotlin
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

/* Archivo de configuración para el proyecto Vsfinal */


project {
    description = "Proyecto final de VS"

    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    name = "CI/CD Pipeline VS"

    vcs {
        root(DslContext.settingsRoot)
    }

    triggers {
        vcs{ }
    }

    steps {
        script {
            name = "Compile Project"
            scriptContent = "echo 'Compilando el proyecto...'"
        }
        script {
            name = "Run Unit Tests"
            scriptContent = "echo 'Ejecutando tests unitarios...'"
        }
        script {
            name = "Deploy to Staging"
            scriptContent = "echo 'Desplegando en el entorno de pruebas...'"
        }
    }
})
```

## 5. Parar y Eliminar los contenedores

Para detener los servicios:

```bash
docker-compose down
```

Para detener y eliminar volúmenes (incluyendo datos persistentes):

```bash
docker-compose down -v
```

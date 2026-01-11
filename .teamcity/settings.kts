import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

/* Archivo de configuración para el proyecto Vsfinal */


project {
    description = "Proyecto para la práctica de VS"

    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    name = "CI/CD Pipeline Example"

    vcs {
        root(DslContext.settingsRoot)
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
    triggers {
        vcs {
        }
    }
})
package _Self

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

/* Archivo de configuración para el proyecto Vsfinal */

project {
    // Es fundamental que este ID coincida con el nombre que creaste en la web
    description = "Proyecto para la práctica de VS"

    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    id("BuildAndTest")
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
})
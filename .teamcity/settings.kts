
package _Self

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script

version = "2024.03"

project {
    buildType(BuildAndTest)
}

object BuildAndTest : BuildType({
    name = "CI/CD Pipeline Example"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        // 1. Fase de Compilación (Build)
        script {
            name = "Compile Project"
            scriptContent = "echo 'Compilando el proyecto...'"
        }
        // 2. Fase de Test
        script {
            name = "Run Unit Tests"
            scriptContent = "echo 'Ejecutando tests unitarios...'"
        }
        // 3. Fase de Despliegue (Deploy)
        script {
            name = "Deploy to Staging"
            scriptContent = "echo 'Desplegando en el entorno de pruebas...'"
        }
    }
})

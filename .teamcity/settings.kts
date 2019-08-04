import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2018_2.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.v2018_2.failureConditions.failOnMetricChange
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

version = "2019.1"

project {
    description = "An open source template engine"
    defaultTemplate = AbsoluteId("DoneskyParent_Donesky")
    vcsRoot(BlockyVcs)
    buildType(Build)
}

object BlockyVcs : GitVcsRoot({
    name = "Blocky"
    url = "git@git.donesky.dev:donesky/blocky.git"
    authMethod = defaultPrivateKey {
    }
})

object Build : BuildType({
    name = "Blocky"

    allowExternalStatus = true

    vcs {
        root(BlockyVcs)
        cleanCheckout = true
    }

    steps {
        gradle {
            name = "Test Blocky"
            tasks = "clean ktlintCheck test"
            enableStacktrace = true
            gradleParams = "--no-daemon"
            dockerImage = "build-server:latest"
            coverageEngine = idea {
                includeClasses = "blocky.*"
                excludeClasses = "kotlin.*"
            }
        }
        gradle {
            name = "Publish Blocky"
            tasks = "clean version publish"
            enableStacktrace = true
            gradleParams = "--no-daemon"
            dockerImage = "build-server:latest"
        }
    }

    triggers {
        vcs {
        }
    }

    failureConditions {
        failOnMetricChange {
            metric = BuildFailureOnMetric.MetricType.COVERAGE_LINE_COUNT
            units = BuildFailureOnMetric.MetricUnit.PERCENTS
            comparison = BuildFailureOnMetric.MetricComparison.LESS
            compareTo = build {
                buildRule = lastFinished()
            }
        }
    }
})
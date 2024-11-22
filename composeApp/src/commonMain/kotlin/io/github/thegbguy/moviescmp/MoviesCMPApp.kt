package io.github.thegbguy.moviescmp

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.CrossfadeTransition
import io.github.thegbguy.moviescmp.dashboard.DashboardScreen
import io.github.thegbguy.moviescmp.di.appModule
import org.koin.compose.KoinApplication

@Composable
fun MoviesCMPApp() {
    KoinApplication(
        application = { modules(appModule) }
    ) {
        Navigator(
            screen = DashboardScreen(),
            disposeBehavior = NavigatorDisposeBehavior(disposeSteps = true)
        ) { navigator ->
            CrossfadeTransition(navigator = navigator)
        }
    }
}
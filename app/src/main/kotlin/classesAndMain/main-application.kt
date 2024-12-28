package classesAndMain

import androidx.compose.ui.unit.dp
    import androidx.compose.ui.window.Window
    import androidx.compose.ui.window.application
    import androidx.compose.ui.window.rememberWindowState
    import classesAndMain.DatabaseHandler
    import classesAndMain.ReservationSystemGUI

    fun main() {
        DatabaseHandler.initializeDatabase()

        application {
            Window(
                onCloseRequest = ::exitApplication,
                title = "System Rezerwacji Sal",
                state = rememberWindowState(width = 1024.dp, height = 768.dp)
            ) {
                ReservationSystemGUI().App()
            }
        }
    }

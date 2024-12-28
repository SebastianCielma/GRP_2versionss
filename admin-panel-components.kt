
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp

    @Composable
    fun AdminPanel(user: User_Class) {
        var currentTab by remember { mutableStateOf(0) }
        val bookingSystem = BookingSystem(DatabaseHandler.loadBookings())

        Column {
            TabRow(selectedTabIndex = currentTab) {
                Tab(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 }
                ) {
                    Text("Zarządzanie Rezerwacjami", modifier = Modifier.padding(8.dp))
                }
                Tab(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 }
                ) {
                    Text("Zarządzanie Użytkownikami", modifier = Modifier.padding(8.dp))
                }
                Tab(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 }
                ) {
                    Text("Zarządzanie Salami", modifier = Modifier.padding(8.dp))
                }
            }

            when (currentTab) {
                0 -> BookingManagementScreen(bookingSystem)
                1 -> UserManagementScreen(user)
                2 -> RoomManagementScreen()
            }
        }
    }

    @Composable
    fun BookingManagementScreen(bookingSystem: BookingSystem) {
        var selectedRoom by remember { mutableStateOf("") }
        var selectedDay by remember { mutableStateOf("") }
        var bookings by remember { mutableStateOf<List<Booking>>(emptyList()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Room Selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("606", "607", "608").forEach { room ->
                    Button(
                        onClick = { selectedRoom = room },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        Text("JM$room")
                    }
                }
            }

            // Day Selection
            Row(
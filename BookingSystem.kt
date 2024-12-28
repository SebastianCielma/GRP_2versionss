package classesAndMain

    class BookingSystem(val bookingsList: List<Booking>) {
        val bookings = BookingList_Class()

        fun searchRooms(building: String, os: OS): List<Room> {
            val rooms = DatabaseHandler.loadBookings()
            return rooms.filter { room ->
                room.building.equals(building, ignoreCase = true) && room.computers.any { it.operatingSystem == os }
            }
        }

        fun bookComputer(student: String, roomNumber: String, computerId: Int, day: String, timeSlot: String): String {
            val existingBooking = bookings.bookings.find { it.roomNumber == roomNumber && it.computerId == computerId && it.day == day && it.timeSlot == timeSlot }
            if (existingBooking != null) {
                return "This computer is already booked for that time slot."
            }
            bookings.bookings.add(Booking(student, roomNumber, computerId, day, timeSlot))
            DatabaseHandler.saveBooking(Booking(student, roomNumber, computerId, day, timeSlot))
            println(bookings.bookings)
            return "Booking the computer! ID: $roomNumber-$computerId"
        }

        fun viewBookings(user: String): List<Booking> {
            return bookings.returnUserBookings(user)
        }

        fun cancelBooking(roomNumber: String, computerId: Int, day: String, timeSlot: String): String {
            val bookingToCancel = bookings.bookings.find { it.roomNumber == roomNumber && it.computerId == computerId && it.day == day && it.timeSlot == timeSlot }
            return if (bookingToCancel != null) {
                bookings.bookings.remove(bookingToCancel)
                "Booking canceled for $roomNumber-$computerId on $day at $timeSlot."
            } else {
                "No booking found to cancel."
            }
        }
    }

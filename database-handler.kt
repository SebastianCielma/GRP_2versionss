import org.jetbrains.exposed.sql.*
    import org.jetbrains.exposed.sql.transactions.transaction
    import org.jetbrains.exposed.dao.*

    object DatabaseHandler {
        private const val DATABASE_NAME = "reservation_system.db"

        fun initializeDatabase() {
            Database.connect(
                "jdbc:sqlite:$DATABASE_NAME",
                driver = "org.sqlite.JDBC"
            )

            transaction {
                SchemaUtils.create(Users, Rooms, Computers, Reservations)

                if (User.all().count() == 0) {
                    User.new {
                        username = "Admin"
                        password = "Admin"
                        isAdmin = true
                    }
                    User.new {
                        username = "Student1"
                        password = "Student1"
                        isAdmin = false
                    }
                }

                if (Room.all().count() == 0) {
                    val room606 = Room.new {
                        building = "JM"
                        number = "606"
                    }

                    for (i in 1..6) {
                        Computer.new {
                            id = i
                            room = room606
                            operatingSystem = when (i % 3) {
                                0 -> OS.LINUX
                                1 -> OS.WINDOWS
                                else -> OS.MAC
                            }
                        }
                    }
                    val room607 = Room.new {
                        building = "JM"
                        number = "607"
                    }

                    for (i in 1..4) {
                        Computer.new {
                            id = i
                            room = room607
                            operatingSystem = when (i % 3) {
                                0 -> OS.MAC
                                1 -> OS.LINUX
                                else -> OS.WINDOWS
                            }
                        }
                    }
                    val room608 = Room.new {
                        building = "JM"
                        number = "608"
                    }

                    for (i in 1..5) {
                        Computer.new {
                            id = i
                            room = room608
                            operatingSystem = when (i % 3) {
                                0 -> OS.WINDOWS
                                1 -> OS.MAC
                                else -> OS.LINUX
                            }
                        }
                    }
                }
            }
        }

        fun saveBooking(booking: Booking) {
            transaction {
                Reservation.new {
                    student = booking.student
                    roomNumber = booking.roomNumber
                    computerId = booking.computerId
                    day = booking.day
                    timeSlot = booking.timeSlot
                }
            }
        }

        fun loadBookings(): List<Booking> {
            return transaction {
                Reservation.all().map {
                    Booking(
                        it.student,
                        it.roomNumber,
                        it.computerId,
                        it.day,
                        it.timeSlot
                    )
                }.toList()
            }
        }
    }

    object Users : IntIdTable() {
        val username = varchar("username", 50).uniqueIndex()
        val password = varchar("password", 50)
        val isAdmin = bool("isAdmin")
    }

    class User(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<User>(Users)
        var username by Users.username
        var password by Users.password
        var isAdmin by Users.isAdmin
    }

    object Rooms : IntIdTable() {
        val building = varchar("building", 50)
        val number = varchar("number", 50)
    }

    class Room(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Room>(Rooms)
        var building by Rooms.building
        var number by Rooms.number
    }

    object Computers : IntIdTable() {
        val id = integer("id")
        val room = reference("room", Rooms)
        val operatingSystem = enumerationByName("operatingSystem", 50, OS::class)
    }

    class Computer(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Computer>(Computers)
        var id by Computers.id
        var room by Room referencedOn Computers.room
        var operatingSystem by Computers.operatingSystem
    }

    object Reservations : IntIdTable() {
        val student = varchar("student", 50)
        val roomNumber = varchar("roomNumber", 50)
        val computerId = integer("computerId")
        val day = varchar("day", 50)
        val timeSlot = varchar("timeSlot", 50)
    }

    class Reservation(id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<Reservation>(Reservations)
        var student by Reservations.student
        var roomNumber by Reservations.roomNumber
        var computerId by Reservations.computerId
        var day by Reservations.day
        var timeSlot by Reservations.timeSlot
    }

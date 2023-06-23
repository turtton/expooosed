import arrow.core.raise.result
import db.table.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "develop",
    )
    transaction {
        addLogger(StdOutSqlLogger)
        result {
            SchemaUtils.create(Users)
        }.onFailure { it.printStackTrace() }
    }
    transaction {
        UserController.createUser(this, "John", 20).onFailure { it.printStackTrace() }
    }
}

import arrow.core.raise.result
import db.table.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.concurrent.atomic.AtomicInteger

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
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val counter = AtomicInteger(0)
    val waitingList = mutableListOf<Job>()
    repeat(10) {
        waitingList += scope.launch {
            transaction {
                UserController.renameUser(this, "John", "John ${counter.getAndIncrement()}")
                    .onFailure {
                        it.printStackTrace()
                    }.onSuccess {
                        it?.let { println(it) } ?: println("User not found")
                    }
            }
        }
    }
    runBlocking {
        waitingList.joinAll()
    }
}

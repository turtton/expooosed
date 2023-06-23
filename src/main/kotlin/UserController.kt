import arrow.core.raise.result
import db.repository.api.UsesUserRepository
import db.repository.exposed.MixinExposedUserRepository
import entity.User
import org.jetbrains.exposed.sql.Transaction

interface UserController<Transaction> : UsesUserRepository<Transaction> {
    // Transactionの実体が知れない限り一生引数で渡すことになる...え
    fun createUser(transaction: Transaction, name: String, age: Int): Result<User?> {
        return userRepository.createUser(transaction, name, age)
    }

    fun renameUser(transaction: Transaction, name: String, newName: String): Result<User?> = result {
        val user = userRepository.getAllUsers(transaction).bind().find { it.name.value == name } ?: return@result null
        userRepository.updateUser(transaction, user.id.value, newName, user.age.value).bind()
    }

    companion object : UserController<Transaction>, MixinExposedUserRepository
}
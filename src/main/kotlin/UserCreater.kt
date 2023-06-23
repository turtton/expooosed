import db.repository.api.UsesUserRepository
import db.repository.exposed.MixinExposedUserRepository
import entity.User
import org.jetbrains.exposed.sql.Transaction

interface UsesUserCreate<Transaction> {
    val userCreater: UserCreater<Transaction>
}

interface UserCreater<Transaction> : UsesUserRepository<Transaction> {
    // Transactionの実体が知れない限り一生引数で渡すことになる...え
    fun createUser(transaction: Transaction, name: String, age: Int): Result<User?> {
        return userRepository.createUser(transaction, name, age)
    }

    companion object : UserCreater<Transaction>, MixinExposedUserRepository
}
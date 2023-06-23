package db.repository.exposed

import arrow.core.raise.result
import db.repository.api.UserRepository
import db.repository.api.UsesUserRepository
import db.table.Users
import entity.User
import org.jetbrains.exposed.sql.Transaction
import vobject.Age
import vobject.Id
import vobject.Name

interface MixinExposedUserRepository : UsesUserRepository<Transaction> {
    override val userRepository: UserRepository<Transaction>
        get() = ExposedDDDUserRepository
}

private object ExposedDDDUserRepository : UserRepository<Transaction> {
    override fun getAllUsers(transaction: Transaction): Result<List<User>> = result {
        transaction.run {
            Users.User.all().map { User(Id(it.id.value), Name(it.name), Age(it.age)) }
        }
    }

    override fun createUser(transaction: Transaction, name: String, age: Int): Result<User?> = result {
        transaction.run {
            Users.User.new {
                this.name = name
                this.age = age
            }.let { User(Id(it.id.value), Name(it.name), Age(it.age)) }
        }
    }

    override fun getUserById(transaction: Transaction, id: Int): Result<User?> {
        TODO("Not yet implemented")
    }

    override fun updateUser(transaction: Transaction, id: Int, name: String, age: Int): Result<User?> {
        TODO("Not yet implemented")
    }

    override fun deleteUser(transaction: Transaction, id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }
}

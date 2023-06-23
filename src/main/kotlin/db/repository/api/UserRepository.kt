package db.repository.api

import entity.User

interface UsesUserRepository<Transaction> {
    val userRepository: UserRepository<Transaction>
}

interface UserRepository<Transaction> {
    fun getAllUsers(transaction: Transaction): Result<List<User>>
    fun getUserById(transaction: Transaction, id: Int): Result<User?>
    fun createUser(transaction: Transaction, name: String, age: Int): Result<User?>
    fun updateUser(transaction: Transaction, id: Int, name: String, age: Int): Result<User?>
    fun deleteUser(transaction: Transaction, id: Int): Result<Boolean>
}
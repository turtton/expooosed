package entity

import vobject.Age
import vobject.Id
import vobject.Name

data class User(
    val id: Id<User>,
    val name: Name<User>,
    val age: Age,
)
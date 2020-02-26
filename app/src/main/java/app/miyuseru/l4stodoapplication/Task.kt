package app.miyuseru.l4stodoapplication

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Task(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var todo: String = "",
    open var content: String = "",
    open var isChecked: Boolean = true,
    open var createdAt: Date = Date(System.currentTimeMillis())
) : RealmObject()
package com.onopry.budgetapp.model.dto

import com.onopry.budgetapp.model.services.*
import com.onopry.budgetapp.utils.*
import java.io.Serializable

data class CategoriesDto(
    val id: String,
    val name: String,
    val icon: Int,
    val color:Int = MY_COLORS.color_category_other,
    val isParent: Boolean = true,
    val isExpence: Boolean = true,
    val parentId: String = "",
    val targetId: String = ""
): Serializable{
    fun toMap() = mutableMapOf<String, Any>(
//        CHILD_CATEGORY_ID to id,
        CHILD_CATEGORY_NAME to name,
        CHILD_CATEGORY_ICON to icon,
        CHILD_CATEGORY_COLOR to color,
        CHILD_CATEGORY_IS_PARENT to isParent,
        CHILD_CATEGORY_IS_EXPENCE to isExpence,
        CHILD_CATEGORY_PARENT_ID to parentId,
        CHILD_CATEGORY_TARGET_ID to targetId
    )

    override fun toString(): String {
        return """
            |id: $id
            |name: $name
        """.trimMargin()
    }
}
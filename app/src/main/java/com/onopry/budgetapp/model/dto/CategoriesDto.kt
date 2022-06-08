package com.onopry.budgetapp.model.dto

import android.os.Parcel
import android.os.Parcelable
import android.view.ViewParent
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.IgnoreExtraProperties
import com.onopry.budgetapp.R
import com.onopry.budgetapp.utils.CATEGORY
import com.onopry.budgetapp.utils.MY_COLORS
import java.io.Serializable

data class CategoriesDto(
    val id: String,
    val name: String,
    val icon: Int = R.drawable.ic_category_placeholder,
    val color:Int = MY_COLORS.color_category_other,
    val isParent: Boolean = true,
    val isExpence: Boolean = true,
    val parentId: String = "",
    val targetId: String = ""
): Serializable {
    fun toMap() = mapOf<String, Any>(
        CATEGORY.NAME to name,
        CATEGORY.ICON to icon,
        CATEGORY.COLOR to color,
        CATEGORY.IS_PARENT to isParent,
        CATEGORY.IS_EXPENCE to isExpence,
        CATEGORY.PARENT_ID to parentId,
        CATEGORY.TARGET_ID to targetId
    )

    fun toMapOperation() = mapOf<String, Any>(
        CATEGORY.ID to id,
        CATEGORY.NAME to name,
        CATEGORY.ICON to icon,
        CATEGORY.COLOR to color,
        CATEGORY.IS_PARENT to isParent,
        CATEGORY.IS_EXPENCE to isExpence,
        CATEGORY.PARENT_ID to parentId,
        CATEGORY.TARGET_ID to targetId
    )

    companion object {
        fun parseSnapshot(snapshot: DataSnapshot) =
            CategoriesDto(
                id = snapshot.key as String,
                name = snapshot.child(CATEGORY.NAME).value as String,
                icon = (snapshot.child(CATEGORY.ICON).value as Long).toInt(),
                color = (snapshot.child(CATEGORY.COLOR).value as Long).toInt(),
                isParent = snapshot.child(CATEGORY.IS_PARENT).value as Boolean,
                isExpence = snapshot.child(CATEGORY.IS_EXPENCE).value as Boolean,
                parentId = snapshot.child(CATEGORY.PARENT_ID).value as String,
                targetId = snapshot.child(CATEGORY.TARGET_ID).value as String,
            )

        fun parseSnapshotOperations(snapshot: DataSnapshot) =
            CategoriesDto(
                id = snapshot.child(CATEGORY.ID).value as String,
                name = snapshot.child(CATEGORY.NAME).value as String,
                icon = (snapshot.child(CATEGORY.ICON).value as Long).toInt(),
                color = (snapshot.child(CATEGORY.COLOR).value as Long).toInt(),
                isParent = snapshot.child(CATEGORY.IS_PARENT).value as Boolean,
                isExpence = snapshot.child(CATEGORY.IS_EXPENCE).value as Boolean,
                parentId = snapshot.child(CATEGORY.PARENT_ID).value as String,
                targetId = snapshot.child(CATEGORY.TARGET_ID).value as String,
            )

    }


}


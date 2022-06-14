package com.onopry.budgetapp.model.features

import com.onopry.budgetapp.R
import com.onopry.budgetapp.model.dto.CategoriesDto
import com.onopry.budgetapp.utils.CATEGORIES_COLORS
import java.util.*

class CategoryDataSourseImpl: CategoriesDataSourse {
    //https://tablericons.com/

    override fun getCategories(): List<CategoriesDto> {
        val parentBeauty =      CategoriesDto(id = UUID.randomUUID().toString(), name = "Уход",       icon = R.drawable.ic_category_beauty,       color = R.color.color_category_beauty)
        val parentBills =       CategoriesDto(id = UUID.randomUUID().toString(), name = "Счета",      icon = R.drawable.ic_category_bills,        color = R.color.color_category_bills)
        val parentCafe =        CategoriesDto(id = UUID.randomUUID().toString(), name = "Кафе",       icon = R.drawable.ic_category_cafe,         color = R.color.color_category_cafe)
        val parentCar =         CategoriesDto(id = UUID.randomUUID().toString(), name = "Авто",       icon = R.drawable.ic_category_car,          color = R.color.color_category_car)
        val parentFamily =      CategoriesDto(id = UUID.randomUUID().toString(), name = "Семья",      icon = R.drawable.ic_category_family,       color = R.color.color_category_family)
        val parentFashion =     CategoriesDto(id = UUID.randomUUID().toString(), name = "Стиль",      icon = R.drawable.ic_category_fashion,      color = R.color.color_category_fashion)
        val parentHealth =      CategoriesDto(id = UUID.randomUUID().toString(), name = "Здоровье",   icon = R.drawable.ic_category_health,       color = R.color.color_category_health)
        val parentHobby =       CategoriesDto(id = UUID.randomUUID().toString(), name = "Хобби",      icon = R.drawable.ic_category_hobby,        color = R.color.color_category_hobby)
        val parentHouse =       CategoriesDto(id = UUID.randomUUID().toString(), name = "Дом",        icon = R.drawable.ic_category_house,        color = R.color.color_category_house)
        val parentOther =       CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",     icon = R.drawable.ic_category_other,        color = R.color.color_category_other)
        val parentPets =        CategoriesDto(id = UUID.randomUUID().toString(), name = "Питомцы",    icon = R.drawable.ic_category_pets,         color = R.color.color_category_pets)
        val parentProducts =    CategoriesDto(id = UUID.randomUUID().toString(), name = "Продукты",   icon = R.drawable.ic_category_products,     color = R.color.color_category_products)
        val parentTransport =   CategoriesDto(id = UUID.randomUUID().toString(), name = "Транспорт",  icon = R.drawable.ic_category_transport,    color = R.color.color_category_transport)

        val parentCategoriesExpence = mutableListOf(parentBeauty, parentBills, parentCafe, parentTransport, parentCar, parentFamily, parentFashion, parentHealth, parentHobby, parentHouse, parentOther, parentPets, parentProducts)

        val parentCategoriesIncome = mutableListOf(
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Зарплата",     icon = R.drawable.ic_income_cash, color = R.color.color_category_income, isExpence = false),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Премеия",      icon = R.drawable.ic_income_cash, color = R.color.color_category_income, isExpence = false),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Инвистиции",   icon = R.drawable.ic_income_cash, color = R.color.color_category_income, isExpence = false),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Продажа",      icon = R.drawable.ic_income_cash, color = R.color.color_category_income, isExpence = false),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",       icon = R.drawable.ic_income_cash, color = R.color.color_category_income, isExpence = false)
        )

        val childCategoriesIncome = mutableListOf<CategoriesDto>()
        childCategoriesIncome.apply {
            parentCategoriesIncome.forEach {
                val copyChild = it.copy(parentId = it.id, isParent = false)
                this.add(copyChild)
            }
        }

        val childCategoriesExpence = mutableListOf(
            //parentBeauty
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Стрижка",                  icon = parentBeauty.icon,   color = parentBeauty.color,      isParent = false, parentId = parentBeauty.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Косметика",                icon = parentBeauty.icon,   color = parentBeauty.color,      isParent = false, parentId = parentBeauty.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentBeauty.icon,   color = parentBeauty.color,      isParent = false, parentId = parentBeauty.id),

            //parentBills
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Электричество",            icon = parentBills.icon,    color = parentBills.color,       isParent = false, parentId = parentBills.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Вода",                     icon = parentBills.icon,    color = parentBills.color,       isParent = false, parentId = parentBills.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Интернет",                 icon = parentBills.icon,    color = parentBills.color,       isParent = false, parentId = parentBills.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Связь",                    icon = parentBills.icon,    color = parentBills.color,       isParent = false, parentId = parentBills.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentBills.icon,    color = parentBills.color,       isParent = false, parentId = parentBills.id),

            //parentCafe
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Кафе",                     icon = parentCafe.icon,     color = parentCafe.color,        isParent = false, parentId = parentCafe.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Бар",                      icon = parentCafe.icon,     color = parentCafe.color,        isParent = false, parentId = parentCafe.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Ресторан",                 icon = parentCafe.icon,     color = parentCafe.color,        isParent = false, parentId = parentCafe.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentCafe.icon,     color = parentCafe.color,        isParent = false, parentId = parentCafe.id),

            //parentCar
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Топливо",                  icon = parentCar.icon,      color = parentCar.color,         isParent = false,  parentId = parentCar.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Парковка",                 icon = parentCar.icon,      color = parentCar.color,         isParent = false,  parentId = parentCar.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Страховка",                icon = parentCar.icon,      color = parentCar.color,         isParent = false,  parentId = parentCar.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Мойка",                    icon = parentCar.icon,      color = parentCar.color,         isParent = false,  parentId = parentCar.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Обслуживание",             icon = parentCar.icon,      color = parentCar.color,         isParent = false,  parentId = parentCar.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentCar.icon,      color = parentCar.color,         isParent = false,  parentId = parentCar.id),

            //parentFamily
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Мама",                     icon = parentFamily.icon,   color = parentFamily.color,      isParent = false, parentId = parentFamily.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Папа",                     icon = parentFamily.icon,   color = parentFamily.color,      isParent = false, parentId = parentFamily.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Дети",                     icon = parentFamily.icon,   color = parentFamily.color,      isParent = false, parentId = parentFamily.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Друзья",                   icon = parentFamily.icon,   color = parentFamily.color,      isParent = false, parentId = parentFamily.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentFamily.icon,   color = parentFamily.color,      isParent = false, parentId = parentFamily.id),

            //parentFashion
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Одежда",                   icon = parentFashion.icon,  color = parentFashion.color,     isParent = false, parentId = parentFashion.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Аксессуары",               icon = parentFashion.icon,  color = parentFashion.color,     isParent = false, parentId = parentFashion.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Нижнее белье",             icon = parentFashion.icon,  color = parentFashion.color,     isParent = false, parentId = parentFashion.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentFashion.icon,  color = parentFashion.color,     isParent = false, parentId = parentFashion.id),

            //parentHealth
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Больница",                 icon = parentHealth.icon,   color = parentHealth.color,      isParent = false, parentId = parentHealth.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Страховка",                icon = parentHealth.icon,   color = parentHealth.color,      isParent = false, parentId = parentHealth.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Лекарства",                icon = parentHealth.icon,   color = parentHealth.color,      isParent = false, parentId = parentHealth.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentHealth.icon,   color = parentHealth.color,      isParent = false, parentId = parentHealth.id),

            //parentHobby
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Спорт",                    icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Путешествия",              icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Кино",                     icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Игры",                     icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Книги",                    icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Музыка",                   icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentHobby.icon,    color = parentHobby.color,       isParent = false, parentId = parentHobby.id),

            //parentHouse
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Аренда",                   icon = parentHouse.icon,    color = parentHouse.color,       isParent = false, parentId = parentHouse.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Мебель",                   icon = parentHouse.icon,    color = parentHouse.color,       isParent = false, parentId = parentHouse.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Электроника",              icon = parentHouse.icon,    color = parentHouse.color,       isParent = false, parentId = parentHouse.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentHouse.icon,    color = parentHouse.color,       isParent = false, parentId = parentHouse.id),

            //parentOther
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Форс-мажор",               icon = parentOther.icon,    color = parentOther.color,       isParent = false, parentId = parentOther.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentOther.icon,    color = parentOther.color,       isParent = false, parentId = parentOther.id),

            //parentPets
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Корм",                     icon = parentPets.icon,     color = parentPets.color,        isParent = false, parentId = parentPets.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Туалет",                   icon = parentPets.icon,     color = parentPets.color,        isParent = false, parentId = parentPets.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Ветеринар",                icon = parentPets.icon,     color = parentPets.color,        isParent = false, parentId = parentPets.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentPets.icon,     color = parentPets.color,        isParent = false, parentId = parentPets.id),

            //parentProducts
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Напитки",                  icon = parentProducts.icon, color = parentProducts.color,    isParent = false, parentId = parentProducts.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Еда",                      icon = parentProducts.icon, color = parentProducts.color,    isParent = false, parentId = parentProducts.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Кофе",                     icon = parentProducts.icon, color = parentProducts.color,    isParent = false, parentId = parentProducts.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentProducts.icon, color = parentProducts.color,    isParent = false, parentId = parentProducts.id),

            //parentTransport
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Общественный транспорт",   icon = parentTransport.icon,color = parentTransport.color,   isParent = false, parentId = parentTransport.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Такси",                    icon = parentTransport.icon,color = parentTransport.color,   isParent = false, parentId = parentTransport.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Самолет",                  icon = parentTransport.icon,color = parentTransport.color,   isParent = false, parentId = parentTransport.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Каршеринг",                icon = parentTransport.icon,color = parentTransport.color,   isParent = false, parentId = parentTransport.id),
            CategoriesDto(id = UUID.randomUUID().toString(), name = "Другое",                   icon = parentTransport.icon,color = parentTransport.color,   isParent = false, parentId = parentTransport.id),
        )

        val finalList = parentCategoriesExpence + parentCategoriesIncome + childCategoriesExpence + childCategoriesIncome

        return finalList
    }
}
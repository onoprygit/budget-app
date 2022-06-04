package com.onopry.budgetapp.utils


import kotlin.Exception

class OperationNotFoundException: Exception()
class OperationIdNotFoundException: Exception()
class OperationAmountDoesNotExist: Exception()
class OperationCategoryNotFoundException: Exception()
class OperationParamsNotFoundException: Exception()
class DateNotExistException: Exception()
class CategoryNotFoundException: Exception()
class TargetNotFoundException: Exception()

class OperationsUploadCancelledException: Exception()
class CategoriesUploadCancelledException: Exception()
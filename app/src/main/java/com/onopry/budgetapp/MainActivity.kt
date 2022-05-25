package com.onopry.budgetapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.onopry.budgetapp.databinding.ActivityMainBinding
import com.onopry.budgetapp.views.screens.*
import com.onopry.budgetapp.utils.MainNavigator

class MainActivity : AppCompatActivity(), MainNavigator {

    // Инициализация переменных
    private lateinit var binding: ActivityMainBinding

    private val analyticsFragment = AnalyticsFragment()
    private val operationFragment = OperationFragment()
    private val budgetAndDebtsFragment = BudgetAndDebtsFragment()
    private val moreFragment = MoreFragment()
    private val addOperationFragment = AddOperationFragment()
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentFragment = analyticsFragment
        replaceFragment(currentFragment)
//        supportFragmentManager
//            .beginTransaction()
//            .addToBackStack(null)
//            .commit()

        binding.navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_analytics -> {
                    showAnalyticsScreen()
                }
                R.id.navigation_transactions -> {
                    showOperationsListScreen()
                }
                R.id.navigation_budget_and_debts -> {
                    showBudgetAndDebtsScreen()
                }
                R.id.navigation_more -> {
                    showMoreScreen()
                }
            }
            true
        }

    }

    override fun showAddOperationScreen() {
        replaceFragment(addOperationFragment, isNested = true)
    }

    override fun showEditOperationScreen(operationId: String) {
        replaceFragment(
            EditOperationFragment.newInstance(
                "ARG_OPERATION_ID", operationId),
            isNested = true
        )
    }

    override fun showAnalyticsScreen() {
        replaceFragment(analyticsFragment)
    }

    override fun showOperationsListScreen() {
        replaceFragment(operationFragment)
    }

    override fun showBudgetAndDebtsScreen() {
        replaceFragment(budgetAndDebtsFragment)
    }

    override fun showMoreScreen() {
        replaceFragment(moreFragment)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: String) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    // Показываем фрагмент на экране
    /** @param [isNested] Is the fragment must open only from other fragment **/
    private fun replaceFragment(fragment: Fragment, isNested: Boolean = false){
        if (isNested){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}
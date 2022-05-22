package com.onopry.budgetapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.onopry.budgetapp.databinding.ActivityMainBinding
import com.onopry.budgetapp.screens.MoreFragment
import com.onopry.budgetapp.screens.BudgetAndDebtsFragment
import com.onopry.budgetapp.screens.AnalyticsFragment
import com.onopry.budgetapp.screens.TransactionsFragment
import com.onopry.budgetapp.utils.MainNavigator

class MainActivity : AppCompatActivity(), MainNavigator {

    // Инициализация переменных
    private lateinit var binding: ActivityMainBinding

    private val analyticsFragment = AnalyticsFragment()
    private val transactionsFragment = TransactionsFragment()
    private val budgetAndDebtsFragment = BudgetAndDebtsFragment()
    private val moreFragment = MoreFragment()
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
                    showTransactionsListScreen()
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

    override fun showAnalyticsScreen() {
        replaceFragment(analyticsFragment)
    }

    override fun showTransactionsListScreen() {
        replaceFragment(transactionsFragment)
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

    override fun toast(messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    // Показываем фрагмент на экране
    private fun replaceFragment(fragment: Fragment){
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
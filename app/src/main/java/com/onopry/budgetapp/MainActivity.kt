package com.onopry.budgetapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationBarView
import com.onopry.budgetapp.databinding.ActivityMainBinding
import com.onopry.budgetapp.ui.MoreFragment
import com.onopry.budgetapp.ui.BudgetAndDebtsFragment
import com.onopry.budgetapp.ui.AnalyticsFragment
import com.onopry.budgetapp.ui.TransactionsFragment

class MainActivity : AppCompatActivity() {

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

        binding.navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_analytics -> {
                    replaceFragment(analyticsFragment)
                }
                R.id.navigation_transactions -> {
                    replaceFragment(transactionsFragment)
                }
                R.id.navigation_budget_and_debts -> {
                    replaceFragment(budgetAndDebtsFragment)
                }
                R.id.navigation_more -> {
                    replaceFragment(moreFragment)
                }
            }
            true
        }

    }

    // Показываем фрагмент на экране
    private fun replaceFragment(fragment: Fragment){
        // Log.d("TAG", "FRAGMENTS BACKSTACK: ${supportFragmentManager.backStackEntryCount}")
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
package com.onopry.budgetapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.onopry.budgetapp.databinding.ActivityMainBinding
import com.onopry.budgetapp.ui.MoreFragment
import com.onopry.budgetapp.ui.BudgetAndDebtsFragment
import com.onopry.budgetapp.ui.AnalyticsFragment
import com.onopry.budgetapp.ui.TransactionsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val analyticsFragment = AnalyticsFragment()
        val transactionsFragment = TransactionsFragment()
        val budgetAndDebtsFragment = BudgetAndDebtsFragment()
        val moreFragment = MoreFragment()

        val navView: BottomNavigationView = binding.navView

        // Навигация между фрагментами через нижнее меню
        navView.setOnNavigationItemSelectedListener { it ->

            when(it.itemId){
                R.id.navigation_analytics -> {
                    selectFragment(analyticsFragment)
                    true
                }
                R.id.navigation_transactions -> {
                    selectFragment(transactionsFragment)
                    true
                }
                R.id.navigation_budget_and_debts -> {
                    selectFragment(budgetAndDebtsFragment)
                    true
                }
                R.id.navigation_more -> {
                    selectFragment(moreFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun selectFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
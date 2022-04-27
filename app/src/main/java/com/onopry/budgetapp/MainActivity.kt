package com.onopry.budgetapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
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
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, analyticsFragment)
                        .commit()
                    true
                }
                R.id.navigation_transactions -> {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, transactionsFragment)
                        .commit()
                    true
                }
                R.id.navigation_budget_and_debts -> {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, budgetAndDebtsFragment)
                        .commit()
                    true
                }
                R.id.navigation_more -> {
                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, moreFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}
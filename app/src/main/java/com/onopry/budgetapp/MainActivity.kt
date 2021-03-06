package com.onopry.budgetapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.onopry.budgetapp.databinding.ActivityMainBinding
import com.onopry.budgetapp.model.services.AUTH
import com.onopry.budgetapp.utils.MainNavigator
import com.onopry.budgetapp.model.services.initFirebase
import com.onopry.budgetapp.utils.startFactory
import com.onopry.budgetapp.view.screens.analytics.AnalyticsFragment
import com.onopry.budgetapp.view.screens.auth.SignInFragment
import com.onopry.budgetapp.view.screens.auth.SignUpFragment
import com.onopry.budgetapp.view.screens.more.MoreFragment
import com.onopry.budgetapp.view.screens.operations.AddOperationFragment
import com.onopry.budgetapp.view.screens.operations.EditOperationFragment
import com.onopry.budgetapp.view.screens.operations.OperationFragment
import com.onopry.budgetapp.view.screens.targets.BudgetAndDebtsFragment
import com.onopry.budgetapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), MainNavigator {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels { startFactory() }

    private val signUpFragment = SignUpFragment()
    private val signInFragment = SignInFragment()

    private val analyticsFragment = AnalyticsFragment()
    private val operationFragment = OperationFragment()
    private val budgetAndDebtsFragment = BudgetAndDebtsFragment()
    private val moreFragment = MoreFragment()
    private val addOperationFragment = AddOperationFragment()
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFirebase()
//        mainViewModel.loadServices()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AUTH.signOut()

//        currentFragment = analyticsFragment
        currentFragment = if (AUTH.currentUser != null) {
            toast("SIGNED IN")
            Log.d("FIREBASE_TAG", AUTH.currentUser?.uid.toString())
            setBottomNavVisible(true)
            analyticsFragment
        } else {
            setBottomNavVisible(false)
            signInFragment
        }

        replaceFragment(currentFragment)

        initNavView()

    }

    private fun initNavView(){
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

    override fun setBottomNavVisible(state: Boolean) {
        if (state) {
            binding.navView.visibility = View.VISIBLE
        } else {
            binding.navView.visibility = View.GONE
        }
    }

    override fun showAnalyticsScreen() {
        setBottomNavVisible(true)
        replaceFragment(analyticsFragment)
    }

    override fun showOperationsListScreen() {
        setBottomNavVisible(true)
        replaceFragment(operationFragment)
    }

    override fun showBudgetAndDebtsScreen() {
        setBottomNavVisible(true)
        replaceFragment(budgetAndDebtsFragment)
    }

    override fun showMoreScreen() {
        setBottomNavVisible(true)
        replaceFragment(moreFragment)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: String) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun showSingUpScreen() {
        setBottomNavVisible(false)
        replaceFragment(signUpFragment, true)
    }

    override fun showSingInScreen() {
        setBottomNavVisible(false)
        replaceFragment(signInFragment, true)

    }

    // ???????????????????? ???????????????? ???? ????????????
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
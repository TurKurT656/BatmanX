package io.omido.batmanx.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.omido.batmanx.util.interfaces.FragmentOnBackPressed

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    abstract val viewModel: VM

    abstract val layoutRes: Int

    abstract val navigationId: Int

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutRes) as DB
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        bindObservables()
        oneTimeEvent()
    }

    override fun onStart() {
        super.onStart()
        everyTimeEvent()
    }

    /**
     *
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */
    abstract fun initBinding()

    abstract fun bindObservables()

    abstract fun oneTimeEvent()

    abstract fun everyTimeEvent()

    fun getCurrentFragment(): Fragment? {
        return supportFragmentManager
            .findFragmentById(navigationId)
            ?.childFragmentManager
            ?.primaryNavigationFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val currentFragment = getCurrentFragment()
        if (currentFragment !is FragmentOnBackPressed) super.onBackPressed()
        (currentFragment as? FragmentOnBackPressed)?.onBackPressed()?.let {
            if (it) super.onBackPressed()
        }
    }

}

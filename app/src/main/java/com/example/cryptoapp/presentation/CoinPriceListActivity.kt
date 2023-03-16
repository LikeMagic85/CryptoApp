package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.CoinApp
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val binding by lazy {
        ActivityCoinPrceListBinding.inflate(layoutInflater)
    }

    private val component by lazy {
       (application as CoinApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        val viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
        adapter.onCoinClickListener = {
            launchCoinDetailFragment(it)
        }

        binding.rvCoinPriceList.adapter = adapter
        viewModel.priceList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun launchCoinDetailFragment(coin: CoinInfo){
        supportFragmentManager.popBackStack()
        if(!isLandScape()){
            setLayout(coin, R.id.main_container)
        }else{
            setLayout(coin, R.id.fragment_container)
        }
    }

    private fun setLayout(coin: CoinInfo, layout:Int) {
        supportFragmentManager.beginTransaction()
            .replace(layout, CoinDetailFragment.newInstance(coin.fromSymbol))
            .addToBackStack(null)
            .commit()
    }

    private fun isLandScape():Boolean{
        return binding.fragmentContainer != null
    }

    override fun onStop() {
        supportFragmentManager.popBackStack()
        super.onStop()
    }
}

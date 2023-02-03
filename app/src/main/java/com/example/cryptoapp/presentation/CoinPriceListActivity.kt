package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }
    private val binding by lazy {
        ActivityCoinPrceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
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

package com.example.dotaherostats.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dotaherostats.databinding.OnbordingItemContainerBinding

class OnboardingAdapter(private val onbordingItems:List<OnboardingItem>? = null): RecyclerView.Adapter<OnboardingAdapter.OnboardingItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        val binding= OnbordingItemContainerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OnboardingItemViewHolder(binding)
    }

    override fun getItemCount(): Int = onbordingItems?.size!!

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        onbordingItems?.get(position)?.let { holder.bind(it) }
    }


    inner class OnboardingItemViewHolder(private val binding:OnbordingItemContainerBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(item: OnboardingItem) {
            binding.imageOnboarding.setImageResource(item.onboardingImage)
            binding.txttextTitle.text = item.title
            binding.txttextDescriptio.text = item.description
        }


    }
}
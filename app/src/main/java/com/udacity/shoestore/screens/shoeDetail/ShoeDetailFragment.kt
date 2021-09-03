package com.udacity.shoestore.screens.shoeDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.shoeList.ShoeListViewModel

class ShoeDetailFragment : Fragment() {

    private val viewModel: ShoeListViewModel by activityViewModels()
    private lateinit var binding: FragmentShoeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.viewModel = viewModel
        binding.newShoe = Shoe("", 0.0, "", "")
        binding.lifecycleOwner = this

        binding.nameTextInput.isErrorEnabled = true
        binding.companyTextInput.isErrorEnabled = true
        binding.sizeTextInput.isErrorEnabled = true
        binding.descriptionInputText.isErrorEnabled = true

        viewModel.isShoeSaved.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_shoeDetailFragment_to_shoeListFragment)
                viewModel.setIsShoeSaved(false)
            }
        }

        binding.cancelButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                if (binding.nameEditText.text.isNullOrBlank()) {
                    binding.nameTextInput.error = getString(R.string.shoe_name_error)
                } else {
                    binding.nameTextInput.error = null
                }
                if (binding.companyEditText.text.isNullOrBlank()) {
                    binding.companyTextInput.error = getString(R.string.shoe_company_error)
                } else {
                    binding.companyTextInput.error = null
                }
                if (binding.sizeEditText.text.isNullOrBlank()) {
                    binding.sizeTextInput.error = getString(R.string.shoe_size_error)
                } else if (binding.sizeEditText.text.toString().toDoubleOrNull() == null) {
                    binding.sizeTextInput.error = getString(R.string.shoe_valid_size_error)
                } else {
                    binding.sizeTextInput.error = null
                }
                if (binding.descriptionEditText.text.isNullOrBlank()) {
                    binding.descriptionInputText.error = getString(R.string.shoe_desc_error)
                } else {
                    binding.descriptionInputText.error = null
                }
            } else {
                binding.nameTextInput.error = null
                binding.companyTextInput.error = null
                binding.sizeTextInput.error = null
                binding.descriptionInputText.error = null
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        viewModel.setError(false)
        super.onDestroy()
    }
}
package com.example.menuprovider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider

class DeliveryFragment : Fragment(), MenuProvider {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
    }
    companion object {
        @JvmStatic
        fun newInstance() = DeliveryFragment()
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.deliver_menu, menu)
    }
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.discount -> {
                Toast.makeText(requireContext(), "Discount", Toast.LENGTH_LONG).show()
            }
            R.id.home -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.host, MainFragment.newInstance())
                    .commit()
            }
        }
        return true
    }
}
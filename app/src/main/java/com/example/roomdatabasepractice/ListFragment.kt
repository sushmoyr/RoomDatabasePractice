package com.example.roomdatabasepractice

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabasepractice.R
import com.example.roomdatabasepractice.data.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var mUserViewModel: UserViewModel
    private val adapter : ListAdapter by lazy {
        ListAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        //Recycler view
        val recyclerView = view.dataList
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, { user->
            adapter.setData(user)
        })

        view.fab.setOnClickListener {
            findNavController().navigate(R.id.action_list_to_add)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)

        val search = menu.findItem(R.id.app_bar_search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_delete ->
            {
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    mUserViewModel.deleteAllUser()
                    Toast.makeText(requireContext(), "Successfully Removed All Data", Toast.LENGTH_SHORT).show()
                }
                builder.setNegativeButton("No") { _, _ ->

                }
                builder.setTitle("Delete Everything?")
                builder.setMessage("Are you sure want to delete all data?")
                builder.create().show()
            }

            R.id.sort ->
            {
                sortData()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null)
        {
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText!=null)
        {
            searchDatabase(newText)
        }
        return true
    }

    private fun searchDatabase(query:String)
    {
        val searchQuery = "%$query%"

        mUserViewModel.searchDatabase(searchQuery).observe(this, { list->
            list.let {
                adapter.setData(it)
            }
        })
    }

    private fun sortData()
    {
        mUserViewModel.sortData().observe(this, { list->
            list.let {
                adapter.setData(it)
            }
        })
    }


}
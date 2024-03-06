package com.myapp.rickandmorty.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.rickandmorty.ui.adapter.PersonsListAdapter
import com.myapp.rickandmorty.core.data.Person
import com.myapp.rickandmorty.databinding.FragmentListBinding
import com.myapp.rickandmorty.repository.RoomRepository
import com.myapp.rickandmorty.ui.viewModel.ListFragmentViewModel
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private val viewModel = ListFragmentViewModel()
    private lateinit var binding: FragmentListBinding
    private val repository = RoomRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        msavedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    private fun init() {
        initRecyclerView()
        getPersonsList()
        setObservers()
    }

    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        viewModel.listPersons.observe(viewLifecycleOwner) { people ->
            val size = people.size
            binding.totalTxt.text = "Total $size characters"

            binding.recylerService.adapter = PersonsListAdapter(people) { person ->
                onItemSelected(person)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recylerService.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onItemSelected(person: Person) {
        val informationDialogFragment = InformationDialogFragment()

        val bundle = Bundle()
        bundle.putString("name", person.name)
        bundle.putInt("age", person.age)
        bundle.putString("address", person.address)
        bundle.putString("number", person.phoneNumber)
        bundle.putString("hobbies", person.hobbies)
        informationDialogFragment.arguments = bundle

        informationDialogFragment.show(parentFragmentManager, "informationDialogFragment")
    }

    private fun getPersonsList() {
        lifecycleScope.launch {
            val people = repository.getAllPersonsByUserUUID()

            viewModel.updatePersons(people)
        }
    }
}
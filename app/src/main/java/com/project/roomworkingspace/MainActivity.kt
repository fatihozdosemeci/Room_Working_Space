package com.project.roomworkingspace

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.roomworkingspace.databinding.ActivityMainBinding
import com.project.roomworkingspace.databinding.DialogUpdateBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    var arrayId: MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val employeeDao = (application as EmployeeApp).db.employeeDao()
        // val workDao = (application as EmployeeApp).db.employeeDao()
        // val employeeWorkDao = (application as EmployeeApp).db.employeeDao()



        binding?.buttonAdd?.setOnClickListener {
            addRecord(employeeDao,arrayId)
        }

        lifecycleScope.launch {

            employeeDao.insert(WorkEntity(workName = "copy", workTime = 15))
            employeeDao.insert(WorkEntity(workName = "delete", workTime = 10))
            employeeDao.insert(WorkEntity(workName = "save", workTime = 20))
            arrayId.add(1)
            arrayId.add(3)
            arrayId.add(2)
            employeeDao.fetchAllEmployees().collect {
                val list = ArrayList(it)
                setupListOfDataIntoRecyclerView(list,employeeDao)
            }


        }

    }

    fun addRecord(employeeDao: EmployeeDao,arrayList: MutableList<Int>){
        val name = binding?.etName?.text.toString()
        val email = binding?.etMailId?.text.toString()

        if(name.isNotEmpty() && email.isNotEmpty())
        {
            lifecycleScope.launch {
                employeeDao.insert(EmployeeEntity(name= name, email = email))

                Toast.makeText(applicationContext,"record saved",Toast.LENGTH_LONG).show()
                binding?.etName?.text?.clear()
                binding?.etMailId?.text?.clear()
            } }else{
            Toast.makeText(applicationContext,"Name or Email cannot be blank",Toast.LENGTH_LONG).show()
        }
    }

    private fun setupListOfDataIntoRecyclerView(
        employeesList: ArrayList<EmployeeEntity>,
        employeeDao: EmployeeDao){
        if(employeesList.isNotEmpty()){
            val itemAdapter = ItemAdapter(employeesList,
                {    updateId ->
                    updateRecordDialog(updateId,employeeDao)

                    lifecycleScope.launch {
                        employeeDao.insert(EmployeeWorkCrossRef(employeeId= updateId,workId= 2))
                    }

                }, { deleteId ->
                    deleteRecordAlertDialog(deleteId,employeeDao)
                }
            )
            binding?.itemList?.layoutManager = LinearLayoutManager(this)
            binding?.itemList?.adapter = itemAdapter
            binding?.itemList?.visibility = View.VISIBLE
            binding?.noRecordsAvailable?.visibility = View.GONE
        }else{
            binding?.itemList?.visibility = View.GONE
            binding?.noRecordsAvailable?.visibility = View.VISIBLE
        }
    }

    fun updateRecordDialog(id:Int,employeeDao: EmployeeDao){
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        val binding = DialogUpdateBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch{
            employeeDao.fetchEmployeeById(id).collect{
                binding.etUpdateName.setText(it.name)
                binding.etUpdateEmailId.setText(it.email)
            }
        }

        binding.tvUpdate.setOnClickListener {
            val name = binding.etUpdateName.text.toString()
            val email = binding.etUpdateEmailId.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty()){
                lifecycleScope.launch{
                    employeeDao.update(EmployeeEntity(id,name,email))
                    Toast.makeText(applicationContext,"record updated",Toast.LENGTH_LONG).show()
                    updateDialog.dismiss()
                }
            }else{
                Toast.makeText(applicationContext,"Name or Email cannot be blank",Toast.LENGTH_LONG).show()
            }
        }

        binding.tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }

        updateDialog.show()

    }

    fun deleteRecordAlertDialog(id:Int,employeeDao: EmployeeDao){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){dialogInterface,_ ->
            lifecycleScope.launch{
                employeeDao.delete(EmployeeEntity(id))
                Toast.makeText(applicationContext,"Record deleted successfully",Toast.LENGTH_LONG).show()
            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("No"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}
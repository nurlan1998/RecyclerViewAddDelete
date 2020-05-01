package com.example.recyclerviewreplay

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add.*
import kotlinx.android.synthetic.main.dialog_add.view.*

class MainActivity : AppCompatActivity() {

    var adapter: ListAdapter = ListAdapter(this)
    private val models:MutableList<User> = mutableListOf()

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        setData(1)

        val itemTouchHelperCallBack = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeUser(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun setData(count:Int){
        for(i in 0 until count){
            val user = User()
            user.title = "Title${i+1}"
            user.description = "Description${i+1}"
            models.add(user)
        }
        adapter.setData(models)
    }

    fun onOptionButtonClick(view: View,position:Int){
        val optionMenu = PopupMenu(this,view)
        val menuInflater = optionMenu.menuInflater
        menuInflater.inflate(R.menu.main_menu,optionMenu.menu)
        optionMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_add -> {
                    val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add,null)
                    val mBuilder = AlertDialog.Builder(this)
                        .setView(mDialogView)
                    val mAlertDialog = mBuilder.show()
                    mDialogView.btnAdd.setOnClickListener {
                        mAlertDialog.dismiss()
                        val name = mDialogView.userName.text.toString()
                        val lastName = mDialogView.lastName.text.toString()
                        if(name.isNotEmpty() && lastName.isNotEmpty()) {
                            adapter.addUser(position, name, lastName)
                        }else{
                            Toast.makeText(this,"Заполните поля",Toast.LENGTH_SHORT).show()
                        }
                    }
                    mDialogView.btnCancel.setOnClickListener {
                        mAlertDialog.dismiss()
                    }
//                    adapter.addUser(position)
                }
                R.id.action_delete -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Delete")
                    builder.setMessage("You want to delete?")
                    builder.setPositiveButton(R.string.yes){ dialog, which ->
                        adapter.removeUser(position)
                    }
                    builder.setNegativeButton(R.string.no){dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        optionMenu.show()
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu,menu)
//        val searchItem: MenuItem = menu.findItem(R.id.action_search)
//        if(searchItem != null){
//            searchView?.setOnCloseListener (SearchView.OnCloseListener { true })
//            val searchPlate = searchView?.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
//            searchPlate.hint = "Search"
//            val searchPlateView: View =
//                searchView!!.findViewById(androidx.appcompat.R.id.search_plate)
//            searchPlateView.setBackgroundColor(ContextCompat.getColor(this,
//                android.R.color.transparent
//                )
//            )
//            searchView!!.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return false
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    adapter.filter.filter(newText)
//                    return true
//                }
//            })
//            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        }
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item!!.itemId == R.id.action_search){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
////
////    override fun onBackPressed() {
////        if(!searchView?.isIconified!!){
////            searchView?.isIconified ?:  = true
////            return
////        }
////        super.onBackPressed()
////    }
}

package com.example.recyclerviewreplay

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var adapter: ListAdapter = ListAdapter(this)
    private val models:MutableList<User> = mutableListOf()

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        setData(0,1)

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
                deleteData(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun setData(size:Int,count:Int){
        for(i in size until count+size){
            val user = User()
            user.title = "Title${i+1}"
            user.description = "Description${i+1}"
            models.add(user)
        }
        adapter.setData(models)
    }
    fun addData(size:Int){
        val user = User()
        user.title = "Title${size+1}"
        user.description = "Description${size+1}"
        models.add(user)
        adapter.setData(models)
    }
    fun deleteData(position: Int){
        val animation = AnimationUtils.loadAnimation(this,R.anim.fade_text)
        models.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    fun onItemClicked(size:Int,position:Int){
        Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show()
        setData(size,position+1)
    }
    fun onOptionButtonClick(view: View,size: Int,position:Int){
        val optionMenu = PopupMenu(this,view)
        val menuInflater = optionMenu.menuInflater
        menuInflater.inflate(R.menu.main_menu,optionMenu.menu)
        optionMenu.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_add -> {
                    addData(size)
                }
                R.id.action_delete -> {
                    deleteData(position)
                }
            }
            return@setOnMenuItemClickListener true
        }
        optionMenu.show()
    }
//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.main_menu,menu)
//        val searchItem: MenuItem = menu.findItem(R.id.action_search)
//        searchView?.queryHint = "Search"
//        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
//                return true
//            }
//        })
//            val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(item.itemId == R.id.action_search){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onBackPressed() {
//        if(!searchView?.isIconified!!){
//            searchView?.isIconified ?:  = true
//            return
//        }
//        super.onBackPressed()
//    }
}

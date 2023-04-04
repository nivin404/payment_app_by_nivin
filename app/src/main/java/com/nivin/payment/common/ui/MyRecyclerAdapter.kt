package com.nivin.payment.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

/**
 * The custom recycler adapter removes most of the boiler plate
 * Works using DataBinding,
 *
 * @param DATA_MODEL data model type of each item
 * @param BINDING view binding type of each item
 * @property itemLayoutResId resource id of the provided view binding
 * @property recyclerView traget recycler view so the adapter to be attached
 * @constructor Create empty My recycler adapter
 */
open class MyRecyclerAdapter<DATA_MODEL>(private val recyclerView: RecyclerView) {
    private var lastCalledPosition: Int = 0
    var selectedItemPosition: Int? = null
    private var dataList: MutableList<DATA_MODEL>? = null
    private var onItemPressed: ((DATA_MODEL?, DATA_MODEL) -> Unit)? = null
    private var onBindViewHolder: ((
        binding: ViewDataBinding, dataModel: DATA_MODEL,
        position: Int) -> Unit)? = null
    private var _onDeleteListener: ((position: Int, dataModel: DATA_MODEL) -> Unit)? = null
    private var onCreateViewHolder: ((data: DATA_MODEL) -> Int)? = null
    val onDeleteListener get() = _onDeleteListener

    private val adapter = object : RecyclerView.Adapter<PpViewHolder<ViewDataBinding>>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                layoutRedId: Int): PpViewHolder<ViewDataBinding> {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(
                    LayoutInflater.from(parent.context), layoutRedId, parent, false)
                return PpViewHolder(binding)
            }

            override fun getItemViewType(position: Int): Int {
                if(onCreateViewHolder == null)
                    throw NullPointerException("You didn't set the onCreateViewHolder() set it before updating the adapter")
                return onCreateViewHolder!!.invoke(dataList!![position])
            }

            override fun onBindViewHolder(
                holder: PpViewHolder<ViewDataBinding>,
                position: Int,
            ) {
                onBindViewHolder?.let {
                    val currentData = getDataAt(position)
                    onItemPressed?.let {

                        holder.binding.root.setOnClickListener {
                            if ((selectedItemPosition == null) || (dataList!!.lastIndex < selectedItemPosition!!)) {
                                onItemPressed!!.invoke(null, currentData)
                            } else {
                                val previousData = getDataAt(selectedItemPosition!!)
                                onItemPressed!!.invoke(previousData, currentData)
                                updateItem(previousData, selectedItemPosition!!)
                            }
                            selectedItemPosition = position
                            updateItem(currentData, position)
                        }
                    }
                    it.invoke(holder.binding, currentData, position)
                }
            }

            override fun getItemCount(): Int {
                return if (dataList != null) dataList!!.size else 0
            }
        }

    init {
        if (recyclerView.layoutManager == null) recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter
    }

    /**
     * This will be triggered when then user scrolls to the last item for the first time,
     * remember: if the list length is same this will only trigger for the first time,
     * call the loadmore APIs here
     *
     * @param onScrolledToLast
     * @receiver
     */
    fun setScrolledToLastListener(onScrolledToLast: () -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: LinearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition()
                val lastItemPosition = dataList!!.lastIndex
                if (lastVisiblePosition == lastItemPosition) {
                    //bottom of list!
                    // checking if it is calling for the first time
                    if (lastCalledPosition != lastItemPosition) onScrolledToLast.invoke()
                    lastCalledPosition = lastItemPosition
                }
            }
        })
    }


    /**
     * Updates the data and view
     * @param data
     */
    @Throws(java.lang.NullPointerException::class)
    fun notifyDataSetChanged(data: MutableList<DATA_MODEL>) {
        if (onBindViewHolder == null)
            throw NullPointerException("onBindViewHolder is null , set it on setOnBindViewHolder()")
        dataList = data
        // don't mind of directly using Notify data set changed,
        // because here we already changing the whole data
        adapter.notifyDataSetChanged()
    }

    /**
     * Add only one item , the view will be updated automatically
     * @param data
     */
    fun addItem(data: DATA_MODEL) {
        dataList!!.add(data)
        adapter.notifyItemInserted(dataList!!.lastIndex)
    }

    /**
     * Removes only one item , the view will be updated automatically
     * @param position
     */
    fun removeItemAt(position: Int) {
        if (position < 0) return
        if (position > dataList!!.lastIndex) return

        dataList!!.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    /**
     * returns the data model at the specified position
     * @param position index of the data model
     * @return data model at the specified position
     */
    fun getDataAt(position: Int): DATA_MODEL = dataList!![position]

    /**
     * Update data model at the specified position,
     * view will be automatically updated
     *
     * @param data dataModel to be replaced with
     * @param position index where the update will be performed
     */
    fun updateItem(data: DATA_MODEL?, position: Int) {
        if (data == null) return
        dataList!![position] = data
        adapter.notifyItemChanged(position)
    }

    /**
     * Insert item and update the view
     * @param position
     * @param data
     */
    fun insertItem(position: Int, data: DATA_MODEL) {
        dataList!!.add(position, data)
        adapter.notifyItemInserted(position)
    }

    /**
     * Clears the list and updates the view
     */
    fun clearList() {
        dataList!!.clear()
        adapter.notifyDataSetChanged()
    }

    /**
     * Sets on bind view holder,
     * this will be called for every item in the list while the view is being created
     *
     * @param onBindViewHolder
     * @receiver
     */
    fun setOnBindViewHolder(onBindViewHolder: ((binding: ViewDataBinding,
                            dataModel: DATA_MODEL, position: Int) -> Unit)) {
        this.onBindViewHolder = onBindViewHolder
    }

    /**
     * Sets one item click enabled listener
     * make the data model change when user presses a particular data model
     * remember: then logic for selected and non selected view should be written in the
     * setOnBindViewHolder(), this method only updates the data model and notifies to update the
     * view at the mentioned position,
     * @param onItemPressed
     * @receiver
     */
    fun setOnItemClickChange(
        onItemPressed: (previousItem: DATA_MODEL?, selectedItem: DATA_MODEL) -> Unit,
    ) {
        this.onItemPressed = onItemPressed
    }

    /**
     * Adds the data model list to the end of the existing data model list
     * and upsdate the view
     * @param episodes
     */
    fun addAllAndNotify(episodes: ArrayList<DATA_MODEL>) {
        if (dataList == null) {
            notifyDataSetChanged(episodes)
        } else {
            val startPosition = episodes.lastIndex
            dataList!!.addAll(episodes)
            val endPosition = episodes.lastIndex
            adapter.notifyItemRangeChanged(startPosition, endPosition)
        }
    }

    /**
     * Sets on delete listener
     * this method will be called when an item is premanently removed from the datalist
     * This method will be called even when you remove one item from the datalist using
     * [removeItemAt],
     * MyAdapterSwipeController will call this method when user removes item
     * You can trigger any API calls if you want to update that change to the server
     * @see MyAdapterSwipeController
     * @param onDeleteListener
     * @receiver
     */
    fun setOnDeleteListener(onDeleteListener: (position: Int, dataModel: DATA_MODEL) -> Unit) {
        _onDeleteListener = onDeleteListener
    }

    fun requestFocusForFirstItem() {
        try {
            recyclerView[0].requestFocus()
        } catch (_: Exception) {
        }
    }

    fun notifyDataSetChanged() {
        adapter.notifyDataSetChanged()
    }


    fun findDataAndIndex(
        onCondition: (currentData: DATA_MODEL, currenIndex: Int) -> Boolean,
        onDataFound: (DATA_MODEL?, index: Int?) -> Unit,
    ) {
        val lifecycleCoroutineScope = recyclerView.findViewTreeLifecycleOwner()?.lifecycleScope
        lifecycleCoroutineScope ?: return
        val dataList: MutableList<DATA_MODEL>? = dataList
        dataList ?: return // data list can be null if not initialized yet
        var dataFound: Boolean = false
        lifecycleCoroutineScope.launch {
            for (index in 0..dataList.lastIndex) {
                val data: DATA_MODEL = dataList[index]
                // check if the condition is meeting, set your condition on the callback
                if (onCondition.invoke(data, index)) {
                    dataFound = true
                    onDataFound.invoke(data, index)
                }
            }
        }

        // calling with null values to update the listener that data not found
        if (!dataFound) onDataFound.invoke(null, null)

    }

    fun setOnCreateViewHolder(onCreateViewHolder:(DATA_MODEL)->Int) {
        this. onCreateViewHolder = onCreateViewHolder
    }


    class PpViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root)
}
package com.pasotti.matteo.wikiheroes.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {

    var MARVEL_PUBLIC_KEY = "8da2e7269fff32817c0f81f419db00ce"

    var MARVEL_PRIVATE_KEY = "e06e48a05a4c410c56c8d10bd360c4c0aa8f9e7b"

    var IMAGE_NOT_AVAILABLE = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"

    fun md5(stringToHash: String): String {
        val MD5 = "MD5"

        try {
            val digest = MessageDigest.getInstance(MD5)
            digest.update(stringToHash.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2)
                    h = "0" + h
                hexString.append(h)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

    fun addFragmentToActivity(manager: FragmentManager, fragment: Fragment, frameId: Int) {

        val transaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()

    }

    fun checkDetailsImages(items: List<Detail>): List<Detail> {

        var goodItems: MutableList<Detail> = mutableListOf<Detail>()

        goodItems.addAll(items.filter { it.thumbnail != null && it.thumbnail.path != null && !it.thumbnail.path.equals(IMAGE_NOT_AVAILABLE) })

        return goodItems
    }

    fun removeItemById(id : String , items: List<Detail>) : List<Detail> {
        var goodItems: MutableList<Detail> = mutableListOf<Detail>()

        goodItems.addAll(items.filter { !(it.id.toString().equals(id)) })

        return goodItems
    }

    fun getIdByResourceURI(resourceURI: String): String {
        var result = ""
        if (resourceURI != null && !resourceURI.equals("")) {
            var i : Int = resourceURI.length - 1
            var str : String = resourceURI.get(i).toString()
            result = result + str


            while (!str.equals("/")) {
                i--
                str = resourceURI.get(i).toString()
                if(!str.equals("/")) {
                    result = str + result
                }

            }

        }

        return result
    }

    fun checkCharactersImages(items: List<Character>): List<Character> {

        var goodItems: MutableList<Character> = mutableListOf<Character>()

        goodItems.addAll(items.filter { it.thumbnail != null && it.thumbnail.path != null && !it.thumbnail.path.equals(IMAGE_NOT_AVAILABLE) })

        return goodItems
    }

    class InfiniteScrollListener(val func: () -> Unit, val layoutManager: androidx.recyclerview.widget.LinearLayoutManager) : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        private var previousTotal = 0
        private var loading = true
        private var visibleThreshold = 2
        private var firstVisibleItem = 0
        private var visibleItemCount = 0
        private var totalItemCount = 0

        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                visibleItemCount = recyclerView.childCount
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    Log.i("InfiniteScrollListener", "End reached")
                    func()
                    loading = true
                }
            }
        }

    }

    class InfiniteScrollListenerGrid(val func: () -> Unit, val layoutManager: androidx.recyclerview.widget.GridLayoutManager) : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
        private var previousTotal = 0
        private var loading = true
        private var visibleThreshold = 2
        private var firstVisibleItem = 0
        private var visibleItemCount = 0
        private var totalItemCount = 0

        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (dy > 0) {
                visibleItemCount = recyclerView.childCount
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    Log.i("InfiniteScrollListener", "End reached")
                    func()
                    loading = true
                }
            }
        }

    }


    class DetailsDiffCallback(private val oldItems : List<Detail>, private val newItems : List<Detail>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems.get(oldItemPosition).id == newItems.get(newItemPosition).id

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }


        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition))

    }

}
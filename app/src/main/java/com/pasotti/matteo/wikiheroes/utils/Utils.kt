package com.pasotti.matteo.wikiheroes.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.pasotti.matteo.wikiheroes.models.Character
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {

    var MARVEL_PUBLIC_KEY = "8da2e7269fff32817c0f81f419db00ce"

    var MARVEL_PRIVATE_KEY = "e06e48a05a4c410c56c8d10bd360c4c0aa8f9e7b"

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

    class InfiniteScrollListener(val func:() -> Unit, val layoutManager: androidx.recyclerview.widget.LinearLayoutManager) : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
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

    class CharacterDiffCallback(private val oldItems : List<Character>, private val newItems : List<Character>) : DiffUtil.Callback() {


        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems.get(oldItemPosition).id == newItems.get(newItemPosition).id

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition))

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            return super.getChangePayload(oldItemPosition, newItemPosition)
        }

    }
}
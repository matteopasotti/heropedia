package com.pasotti.matteo.wikiheroes.utils

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    var MARVEL_PUBLIC_KEY = "8da2e7269fff32817c0f81f419db00ce"

    var MARVEL_PRIVATE_KEY = "e06e48a05a4c410c56c8d10bd360c4c0aa8f9e7b"

    var IMAGE_NOT_AVAILABLE = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available"

    const val BASE_URL = "http://gateway.marvel.com"


    enum class WEEK {
        lastWeek,
        thisWeek,
        nextWeek,
        none
    }


    fun md5(stringToHash: String): String {
        val md5 = "MD5"

        try {
            val digest = MessageDigest.getInstance(md5)
            digest.update(stringToHash.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) {
                    h = "0$h"
                }
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

        val goodItems: MutableList<Detail> = mutableListOf()

        goodItems.addAll(items.filter { it.thumbnail?.path != null && it.thumbnail.path != IMAGE_NOT_AVAILABLE })

        return goodItems
    }

    fun removeItemById(id : String , items: List<Detail>) : List<Detail> {
        val goodItems: MutableList<Detail> = mutableListOf()

        goodItems.addAll(items.filter { it.id.toString() != id })

        return goodItems
    }

    fun getIdByResourceURI(resourceURI: String?): String {
        var result = ""
        if (resourceURI != null && resourceURI != "") {
            var i : Int = resourceURI.length - 1
            var str : String = resourceURI.get(i).toString()
            result += str


            while (str != "/") {
                i--
                str = resourceURI[i].toString()
                if(str != "/") {
                    result = str + result
                }

            }

        }

        return result
    }

    fun checkCharactersImages(items: List<Character>): List<Character> {

        val goodItems: MutableList<Character> = mutableListOf()

        goodItems.addAll(items.filter { it.thumbnail.path != IMAGE_NOT_AVAILABLE })

        return goodItems
    }


    class NestedInfiniteScrollListener(val func: () -> Unit) : NestedScrollView.OnScrollChangeListener {
        override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
            if (v?.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight && scrollY > oldScrollY) {
                    //code to fetch more data for endless scrolling
                    Log.i("NestedInfiniteScrollLis", "End reached")
                    func()
                }
            }
        }

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


    fun getCurrentDate() : String {
        val c : Date = Calendar.getInstance().time

        val df = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate = df.format(c)
        Log.d("" , "")

        return formattedDate
    }

    /**
     * d1 could be lastDateSynch
     * d2 could be the current date
     */
    fun getDifferenceBetweenDates( d1 : String , d2 : String) : Long {
        val df = SimpleDateFormat("dd/MM/yyyy")

        try {
            val date1 = df.parse(d1)
            val date2 = df.parse(d2)

            //calculate difference

            val difference = date2.time - date1.time

            val secondsInMilli = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            return difference / daysInMilli

        } catch ( e : ParseException) {
            e.printStackTrace()
        }

        return 0L

    }


    class DetailsDiffCallback(private val oldItems : List<Detail>, private val newItems : List<Detail>) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems[oldItemPosition].id == newItems.get(newItemPosition).id

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }


        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItems.get(oldItemPosition).equals(newItems.get(newItemPosition))

    }


    fun showAlert(context: Context?, message : String) {
        if(context != null) {
            AlertDialog.Builder(context).apply {
                setMessage(message)
                setPositiveButton("OK") { _, _ ->
                    //pass
                }
                show()
            }
        }

    }

}
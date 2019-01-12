package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import javax.inject.Inject

class MoreInfoViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {


    private var goodItems: MutableList<Pair<String?, MutableList<Item>>> = mutableListOf()

    lateinit var creators: List<Item>

    lateinit var section : String


    fun getCreatorsMap(creators: List<Item>): MutableList<Pair<String?, MutableList<Item>>> {

        if (creators != null && creators.isNotEmpty()) {
            for (creator in creators) {
                if (creator.role != null && creator.name != null) {
                    //check if we already have this role and this creator in out list in our list
                    if(!isRoleAlreadyAdded(creator.role)) {
                        addCreatorAndRole(creator)
                    } else if (!isCreatorAlreadyAdded(creator)) {
                        //if false, we add it to the list
                        addCreator(creator)
                    }
                }
            }
        }

        return goodItems
    }

    private fun isRoleAlreadyAdded(role: String): Boolean {
        return goodItems.count { it.first.equals(role) } > 0
    }

    private fun isCreatorAlreadyAdded(creator: Item): Boolean {
        if (goodItems.isNotEmpty()) {
            return goodItems.count { it.first.equals(creator.role) && it.second.contains(creator) } > 0
        } else {
            return false
        }

        return false
    }

    private fun addCreatorAndRole(creator : Item) {
        goodItems.add(Pair(creator.role , mutableListOf(creator)))
    }

    private fun addCreator(creator : Item) {
        var creators : MutableList<Item> = mutableListOf()

        creators = goodItems.filter { it.first.equals(creator.role) }.get(0).second

        val index = goodItems.indexOfFirst { it.first.equals(creator.role) }

        creators.add(creator)

        goodItems[index] = Pair(creator.role , creators)
    }
}
package ru.mirea.zotovml.loadermanager

import android.content.Context
import android.os.Bundle
import androidx.loader.content.AsyncTaskLoader

class MyLoader(context: Context, args: Bundle?) : AsyncTaskLoader<String?>(context) {
    private var word: String? = null
    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    fun shuffle(inp:String):String{
        val characters: MutableList<Char> = ArrayList()
        for (c in inp.toCharArray()) {
            characters.add(c)
        }
        val output: StringBuilder = StringBuilder(inp.length)
        while (characters.size != 0) {
            val randPicker = (Math.random() * characters.size).toInt()
            output.append(characters.removeAt(randPicker))
        }
        return output.toString()
    }
    override fun loadInBackground(): String? {
        return word?.let { shuffle(it) }
    }

    companion object {
        const val ARG_WORD = "word"
    }

    init {
        if (args != null) word = args.getString(ARG_WORD)
    }
}
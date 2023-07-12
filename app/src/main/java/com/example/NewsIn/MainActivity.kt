package com.example.NewsIn

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter:NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.apply {
            elevation = 0f  // Remove elevation shadow if needed
            // Set background color
            val color = ContextCompat.getColor(this@MainActivity, R.color.black)
            setBackgroundDrawable(ColorDrawable(color))
        }

        findViewById<RecyclerView>(R.id.recyclerView).layoutManager=LinearLayoutManager(this )
        mAdapter=NewsListAdapter(this)
        fetchData()
        findViewById<RecyclerView>(R.id.recyclerView).adapter=mAdapter

    }
    private fun fetchData()  {
    val url= "https://saurav.tech/NewsAPI/everything/cnn.json"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            {
                val newsJsonArray=it.getJSONArray("articles")
                val newsArray=ArrayList<News>()
                for (i in 0 until newsJsonArray.length()){
                    val newJsonObject=newsJsonArray.getJSONObject(i)
                    val news=News(newJsonObject.getString("title"),newJsonObject.getString("author"),newJsonObject.getString("url"),newJsonObject.getString("urlToImage"))
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    override fun onItemClicked(item: News) {
        Toast.makeText(this,"Here you go...",Toast.LENGTH_SHORT).show()
        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        val colorScheme = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(Color.BLACK)
            .setNavigationBarColor(Color.BLACK)
            .build()

        val builder = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(colorScheme)

        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }




}
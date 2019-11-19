package com.vbanjan.newsnext

import android.content.SharedPreferences
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sources.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), SourcesFragment.OnFragmentInteractionListener,
    TopHeadlinesFragment.OnFragmentInteractionListener,
    SourceRecyclerViewAdapter.OnFragmentAdapterInteractionListener {

    override fun getSources() {
        val sources = GetNewsSources().execute()
    }

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
    }


    override fun onFragmentInteraction(uri: Uri) {
    }

    fun setUpSourcesRecyclerView(sourcesArrayList: ArrayList<Source>) {
        val sourcesRV = findViewById<RecyclerView>(R.id.sourcesRecyclerView)
        sourcesRV.layoutManager = LinearLayoutManager(this@MainActivity)
        var adapter = SourceRecyclerViewAdapter(
            sourcesArrayList,
            this@MainActivity as SourceRecyclerViewAdapter.OnFragmentAdapterInteractionListener
        )
        sourcesRV.adapter = adapter
    }

    inner class GetNewsSources : AsyncTask<Void, ArrayList<Source>, ArrayList<Source>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<Source>? {
            val sourcesArrayList: ArrayList<Source> = ArrayList()

            val client = OkHttpClient()
            val url =
                "https://newsapi.org/v2/sources?language=en&country=us&apiKey=712796de4cf6476fa1c8d86fab042601" //TODO Use getString

            val request: Request = Request.Builder().url(url).build()
            try {
                val response: Response = client.newCall(request).execute()
                if (!response.isSuccessful) throw IOException("Unexpected Code: $response")

                val json: String = response.body!!.string()
                val jsonRoot = JSONObject(json)
                val sourcesJsonArray = JSONArray(jsonRoot.getString("sources"))

                for (i in 0 until sourcesJsonArray.length()) {
                    val sourceJson = JSONObject(sourcesJsonArray[i].toString())
                    val sourceObj = Source(
                        sourceJson.getString("id"),
                        sourceJson.getString("name"),
                        sourceJson.getString("description")
                    )
                    sourcesArrayList.add(sourceObj)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return sourcesArrayList
        }

        override fun onPostExecute(result: ArrayList<Source>?) {
            if (result != null)
                setUpSourcesRecyclerView(result!!)
        }
    }

    override fun onSourceClick(source: Source) {
        val bundle = bundleOf("selectedSource" to source)
        navController?.navigate(R.id.action_sourcesFragment_to_topHeadlinesFragment, bundle)
    }
}

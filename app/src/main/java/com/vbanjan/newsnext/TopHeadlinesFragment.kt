package com.vbanjan.newsnext

import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_top_headlines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class TopHeadlinesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    lateinit var selectedSource: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedSource = arguments!!.getSerializable("selectedSource").toString()
        var sourceObj=arguments!!.getSerializable("selectedSource") as Source
        GetTopHeadlines().execute(sourceObj.getID());
    }
    inner class GetTopHeadlines: AsyncTask<String, Void, ArrayList<HeadLine>>() {
        override fun doInBackground(vararg p0: String): ArrayList<HeadLine> {
            var source = p0[0]
            var headlines = ArrayList<HeadLine>();
            var result = ""
            val client = OkHttpClient()
            val url ="https://newsapi.org/v2/top-headlines?sources="+source+"&apiKey=2c5d920262c643b5889cd05d58a1ae53"
            val request: Request = Request.Builder().url(url).build()
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException("Unexpected Code: $response")
            val json: String = response.body!!.string()
            Log.d("demo",json);
            if(!json.isNullOrEmpty()) {
                val jsonObject = JSONObject(json);
                val jsonArray = jsonObject.getJSONArray("articles");
                for (i in 0 until jsonArray.length()) {
                    val article = jsonArray.getJSONObject(i);
                    val title = article.get("title").toString();
                    val description = article.get("description").toString();
                    val urlHL = article.get("url").toString();
                    val urlTL = article.get("urlToImage").toString();
                    val publishedAt = article.get("publishedAt").toString();
                    if (title.isEmpty() || description.isEmpty() || urlHL.isEmpty() || urlTL.isEmpty()) {
                        break
                    } else {
                        headlines.add(HeadLine(title, description, urlHL, urlTL,publishedAt))
                    }
                }
            }
            return headlines;
        }

        override fun onPostExecute(result: ArrayList<HeadLine>) {
            super.onPostExecute(result)
            topHeadlinesRecyclerView.layoutManager=LinearLayoutManager(context)
            topHeadlinesRecyclerView.adapter= HeadLineAdapter(result,context as Context)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        Log.d("demo", selectedSource)
        return inflater.inflate(R.layout.fragment_top_headlines, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

}

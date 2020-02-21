package com.rifafauzi.moviecoroutines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rifafauzi.moviecoroutines.databinding.ItemMovieBinding
import com.rifafauzi.moviecoroutines.model.MovieModel

/**
 * Created by rrifafauzikomara on 2020-02-20.
 */
 
class MovieAdapter(private val listener: OnMoviesPressedListener) : ListAdapter<MovieModel, MovieAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), listener, holder.adapterPosition)

    class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MovieModel, listener: OnMoviesPressedListener, position: Int) {
            binding.data = model
            binding.root.setOnClickListener {
                listener.onMoviesPressed(model, position)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<MovieModel>(){
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnMoviesPressedListener {
        fun onMoviesPressed(movies: MovieModel, position: Int)
    }

}
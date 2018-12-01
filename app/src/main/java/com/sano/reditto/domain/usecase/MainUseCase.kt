package com.sano.reditto.domain.usecase

import com.sano.reditto.domain.entity.TopTimeEntity
import com.sano.reditto.domain.IRepository
import com.sano.reditto.presentation.model.LinkModel
import com.sano.reditto.presentation.model.mapper.toLinkModel
import io.reactivex.Single

class MainUseCase(private val repository: IRepository) {
    private var after: String? = null
    private val time = TopTimeEntity.DAY

    fun getTop(count: Int, limit: Int): Single<List<LinkModel>> =
        repository.getTop(time.value, after, count, false, limit)
            .doOnSuccess { after = it.after }
            .map { list -> list.links.map { it.toLinkModel() } }
}